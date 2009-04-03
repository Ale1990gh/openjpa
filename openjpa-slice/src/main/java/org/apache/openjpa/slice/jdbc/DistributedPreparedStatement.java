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
package org.apache.openjpa.slice.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.openjpa.lib.util.ConcreteClassGenerator;

/**
 * A virtual PreparedStaement that delegates to a set of actual
 * PreparedStatements.
 * 
 * @author Pinaki Poddar 
 *
 */
public abstract class DistributedPreparedStatement 
    extends DistributedTemplate<PreparedStatement> 
    implements PreparedStatement {

    static final Class<DistributedPreparedStatement> concreteImpl;
    static {
        try {
            concreteImpl = ConcreteClassGenerator.
                makeConcrete(DistributedPreparedStatement.class);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
	public DistributedPreparedStatement(DistributedConnection c) {
		super(c);
	}
	
    public static DistributedPreparedStatement newInstance(
        DistributedConnection conn) {
    return ConcreteClassGenerator.newInstance(concreteImpl, 
        DistributedConnection.class, conn);
}

	public void clearParameters() throws SQLException {
		for (PreparedStatement s : this)
			s.clearParameters();
	}

	public boolean execute() throws SQLException {
		boolean ret = true;
		for (PreparedStatement s : this)
			ret = s.execute() & ret;
		return ret;
	}

	public ResultSet executeQuery() throws SQLException {
		DistributedResultSet mrs = DistributedResultSet.newInstance();
		for (PreparedStatement t : this)
			mrs.add(t.executeQuery());
		return mrs;
	}

	public int executeUpdate() throws SQLException {
		int ret = 0;
		for (PreparedStatement t : this)
			ret += t.executeUpdate();
		return ret;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return master.getMetaData();
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void setArray(int i, Array x) throws SQLException {
		for (PreparedStatement t : this)
			t.setArray(i, x);
	}

	public void setAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		for (PreparedStatement t : this)
			t.setAsciiStream(arg0, arg1, arg2);
	}

    public void setBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setBigDecimal(arg0, arg1);
	}

	public void setBinaryStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		for (PreparedStatement t : this)
			t.setBinaryStream(arg0, arg1, arg2);
	}

	public void setBlob(int arg0, Blob arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setBlob(arg0, arg1);
	}

	public void setBoolean(int arg0, boolean arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setBoolean(arg0, arg1);
	}

	public void setByte(int arg0, byte arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setByte(arg0, arg1);
	}

	public void setBytes(int arg0, byte[] arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setBytes(arg0, arg1);
	}

	public void setCharacterStream(int arg0, Reader arg1, int arg2)
			throws SQLException {
		for (PreparedStatement t : this)
			t.setCharacterStream(arg0, arg1, arg2);
	}

	public void setClob(int arg0, Clob arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setClob(arg0, arg1);
	}

	public void setDate(int arg0, Date arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setDate(arg0, arg1);
	}

    public void setDate(int arg0, Date arg1, Calendar arg2) throws SQLException
    {
		for (PreparedStatement t : this)
			t.setDate(arg0, arg1, arg2);
	}

	public void setDouble(int arg0, double arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setDouble(arg0, arg1);
	}

	public void setFloat(int arg0, float arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setFloat(arg0, arg1);
	}

	public void setInt(int arg0, int arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setInt(arg0, arg1);
	}

	public void setLong(int arg0, long arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setLong(arg0, arg1);
	}

	public void setNull(int arg0, int arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setNull(arg0, arg1);
	}

    public void setNull(int arg0, int arg1, String arg2) throws SQLException {
		for (PreparedStatement t : this)
			t.setNull(arg0, arg1, arg2);
	}

	public void setObject(int arg0, Object arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setObject(arg0, arg1);
	}

    public void setObject(int arg0, Object arg1, int arg2) throws SQLException {
		for (PreparedStatement t : this)
			t.setObject(arg0, arg1, arg2);
	}

	public void setObject(int arg0, Object arg1, int arg2, int arg3)
			throws SQLException {
		for (PreparedStatement t : this)
			t.setObject(arg0, arg1, arg2, arg3);
	}

	public void setRef(int arg0, Ref arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setRef(arg0, arg1);
	}

	public void setShort(int arg0, short arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setShort(arg0, arg1);
	}

	public void setString(int arg0, String arg1) throws SQLException {
		for (PreparedStatement t : this)
			t.setString(arg0, arg1);
	}

	 public void setTime(int arg0, Time arg1) throws SQLException {
			for (PreparedStatement t : this)
				t.setTime(arg0, arg1);
	 }
	
	 public void setTime(int arg0, Time arg1, Calendar arg2) throws
	 SQLException {
			for (PreparedStatement t : this)
				t.setTime(arg0, arg1, arg2);
	 }
	
     public void setTimestamp(int arg0, Timestamp arg1) throws SQLException {
			for (PreparedStatement t : this)
				t.setTimestamp(arg0, arg1);
	 }
	
	 public void setTimestamp(int arg0, Timestamp arg1, Calendar arg2)
	 throws SQLException {
			for (PreparedStatement t : this)
				t.setTimestamp(arg0, arg1, arg2);
	 }
	
	 public void setURL(int arg0, URL arg1) throws SQLException {
			for (PreparedStatement t : this)
				t.setURL(arg0, arg1);
	 }
	
	 public void setUnicodeStream(int arg0, InputStream arg1, int arg2)
	     throws SQLException {
			for (PreparedStatement t : this)
				t.setUnicodeStream(arg0, arg1, arg2);
	 }
	
	 public void addBatch() throws SQLException {
		for (PreparedStatement t:this)
		    t.addBatch();
	 }
}
