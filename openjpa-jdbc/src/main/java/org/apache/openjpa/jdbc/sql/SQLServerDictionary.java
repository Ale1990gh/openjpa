/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package org.apache.openjpa.jdbc.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.openjpa.jdbc.kernel.exps.FilterValue;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.JavaTypes;

/**
 * Dictionary for MS SQLServer.
 */
public class SQLServerDictionary
    extends AbstractSQLServerDictionary {

    public static final String VENDOR_MICROSOFT = "microsoft";
    public static final String VENDOR_NETDIRECT = "netdirect";
    public static final String VENDOR_JTDS = "jtds";

    private static final Localizer _loc = Localizer.forPackage
        (SQLServerDictionary.class);

    private String schemaCase = SCHEMA_CASE_PRESERVE;
    /**
     * Flag whether to treat UNIQUEIDENTIFIER as VARBINARY or VARCHAR
     */
    public boolean uniqueIdentifierAsVarbinary = true;

    public SQLServerDictionary() {
        platform = "Microsoft SQL Server";

        // SQLServer locks on a table-by-table basis
        forUpdateClause = null;
        tableForUpdateClause = "WITH (UPDLOCK)";

        supportsNullTableForGetColumns = false;
        requiresAliasForSubselect = true;

        stringLengthFunction = "LEN({0})";
        supportsTimestampNanos = false;
    }

    public void connectedConfiguration(Connection conn)
        throws SQLException {
        super.connectedConfiguration(conn);

        DatabaseMetaData meta = conn.getMetaData();
        String driverName = meta.getDriverName();
        String url = meta.getURL();
        if (driverVendor == null) {
            if ("NetDirect JSQLConnect".equals(driverName))
                driverVendor = VENDOR_NETDIRECT;
            else if (driverName != null && driverName.startsWith("jTDS"))
                driverVendor = VENDOR_JTDS;
            else if ("SQLServer".equals(driverName)) {
                if (url != null && url.startsWith("jdbc:microsoft:sqlserver:"))
                    driverVendor = VENDOR_MICROSOFT;
                else if (url != null
                    && url.startsWith("jdbc:datadirect:sqlserver:"))
                    driverVendor = VENDOR_DATADIRECT;
                else
                    driverVendor = VENDOR_OTHER;
            } else
                driverVendor = VENDOR_OTHER;
            if (driverName.indexOf(platform) != -1) {
                String versionString = driverName.
                    substring(platform.length() + 1);
                if (versionString.indexOf(" ") != -1)
                    versionString = versionString.substring(0,
                        versionString.indexOf(" "));
                int version = Integer.parseInt(versionString);
                if (version >= 2005)
                    supportsXMLColumn = true;
            }
        }

        // warn about using cursors
        if ((VENDOR_MICROSOFT.equalsIgnoreCase(driverVendor)
            || VENDOR_DATADIRECT.equalsIgnoreCase(driverVendor))
            && url.toLowerCase().indexOf("selectmethod=cursor") == -1)
            log.warn(_loc.get("sqlserver-cursor", url));

        // warn about prepared statement caching if using ms driver
        String props = conf.getConnectionFactoryProperties();
        if (props == null)
            props = "";
        if (VENDOR_MICROSOFT.equalsIgnoreCase(driverVendor)
            && props.toLowerCase().indexOf("maxcachedstatements=0") == -1)
            log.warn(_loc.get("sqlserver-cachedstmnts"));
    }

    public Column[] getColumns(DatabaseMetaData meta, String catalog,
        String schemaName, String tableName, String columnName, Connection conn)
        throws SQLException {
        Column[] cols = super.getColumns(meta, catalog, schemaName, tableName,
            columnName, conn);

        // for opta driver, which reports nvarchar as unknown type
        for (int i = 0; cols != null && i < cols.length; i++) {
            String typeName = cols[i].getTypeName();
            if (typeName == null)
                continue;

            typeName = typeName.toUpperCase();

            if ("NVARCHAR".equals(typeName))
                cols[i].setType(Types.VARCHAR);
            else if ("UNIQUEIDENTIFIER".equals(typeName)) {
                if (uniqueIdentifierAsVarbinary)
                    cols[i].setType(Types.VARBINARY);
                else
                    cols[i].setType(Types.VARCHAR);
            } else if ("NCHAR".equals(typeName))
                cols[i].setType(Types.CHAR);
            else if ("NTEXT".equals(typeName))
                cols[i].setType(Types.CLOB);
        }
        return cols;
    }
    
    protected void appendLength(SQLBuffer buf, int type) {
        if (type == Types.VARCHAR)
            buf.append("(").append(Integer.toString(characterColumnSize)).append(")");
    }

    /**
     * If this dictionary supports XML type,
     * use this method to append xml predicate.
     * 
     * @param buf the SQL buffer to write the comparison
     * @param op the comparison operation to perform
     * @param lhs the left hand side of the comparison
     * @param rhs the right hand side of the comparison
     * @param lhsxml indicates whether the left operand maps to xml
     * @param rhsxml indicates whether the right operand maps to xml
     */
    public void appendXmlComparison(SQLBuffer buf, String op, FilterValue lhs,
        FilterValue rhs, boolean lhsxml, boolean rhsxml) {
        super.appendXmlComparison(buf, op, lhs, rhs, lhsxml, rhsxml);
        if (lhsxml && rhsxml)
            appendXmlComparison2(buf, op, lhs, rhs);
        else if (lhsxml)
            appendXmlComparison1(buf, op, lhs, rhs);
        else 
            appendXmlComparison1(buf, op, rhs, lhs);
    }
    /**
     * Append an xml comparison predicate
     *
     * @param buf the SQL buffer to write the comparison
     * @param op the comparison operation to perform
     * @param lhs the left hand side of the comparison (maps to xml column)
     * @param rhs the right hand side of the comparison
     */
    private void appendXmlComparison1(SQLBuffer buf, String op,
        FilterValue lhs, FilterValue rhs) {
        boolean castrhs = rhs.isConstant();
        if (castrhs)
            appendXmlValue(buf, lhs);
        else
            appendXmlExist(buf, lhs);
        buf.append(" ").append(op).append(" ");
        if (castrhs)
            rhs.appendTo(buf);
        else {
            buf.append("sql:column(\"");
            rhs.appendTo(buf);
            buf.append("\")").
                append("]') = 1");
        }
    }
    
    private void appendXmlExist(SQLBuffer buf, FilterValue lhs) {
        buf.append(lhs.getColumnAlias(
            lhs.getFieldMapping().getColumns()[0])).
            append(".exist('").
            append("/*[");
        lhs.appendTo(buf);    
    }
    
    /**
     * Append an xml comparison predicate (both operands map to xml column)
     *
     * @param buf the SQL buffer to write the comparison
     * @param op the comparison operation to perform
     * @param lhs the left hand side of the comparison (maps to xml column)
     * @param rhs the right hand side of the comparison (maps to xml column)
     */
    private void appendXmlComparison2(SQLBuffer buf, String op, 
        FilterValue lhs, FilterValue rhs) {
        appendXmlValue(buf, lhs);
        buf.append(" ").append(op).append(" ");
        appendXmlValue(buf, rhs);
    }
    
    private void appendXmlValue(SQLBuffer buf, FilterValue val) {
        Class rc = Filters.wrap(val.getType());
        int type = getJDBCType(JavaTypes.getTypeCode(rc), false);
        boolean isXmlAttribute = (val.getXmlMapping() == null) ? false
                : val.getXmlMapping().isXmlAttribute();
        buf.append(val.getColumnAlias(
            val.getFieldMapping().getColumns()[0])).
            append(".value(").
            append("'(/*/");
        val.appendTo(buf);
        if (!isXmlAttribute)
            buf.append("/text()");
        buf.append(")[1]','").
            append(getTypeName(type));
        appendLength(buf, type);
        buf.append("')");
    }
    
    /**
     * Return DB specific schemaCase
     */
    public String getSchemaCase() {
        return schemaCase;
    }
}
