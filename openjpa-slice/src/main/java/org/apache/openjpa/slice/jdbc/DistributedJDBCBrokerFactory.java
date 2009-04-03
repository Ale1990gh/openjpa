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

import java.security.AccessController;
import java.util.Map;

import org.apache.openjpa.conf.OpenJPAVersion;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCBrokerFactory;
import org.apache.openjpa.kernel.Bootstrap;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.slice.DistributedBrokerFactory;
import org.apache.openjpa.slice.Slice;

/**
 * A factory for distributed JDBC datastores.
 * 
 * @author Pinaki Poddar
 * 
 */
@SuppressWarnings("serial")
public class DistributedJDBCBrokerFactory extends JDBCBrokerFactory 
    implements DistributedBrokerFactory {
	private static final Localizer _loc = 
	    Localizer.forPackage(DistributedJDBCBrokerFactory.class);
	/**
     * Factory method for constructing a factory from properties. Invoked from
	 * {@link Bootstrap#newBrokerFactory}.
	 */
	public static DistributedJDBCBrokerFactory newInstance(
			ConfigurationProvider cp) {
		DistributedJDBCConfigurationImpl conf =
				new DistributedJDBCConfigurationImpl(cp);
		cp.setInto(conf);
		return new DistributedJDBCBrokerFactory(conf);
	}

	/**
     * Factory method for obtaining a possibly-pooled factory from properties.
	 * Invoked from {@link Bootstrap#getBrokerFactory}.
	 */
	public static JDBCBrokerFactory getInstance(ConfigurationProvider cp) {
	    Map properties = cp.getProperties();
	    Object key = toPoolKey(properties);
		DistributedJDBCBrokerFactory factory =
                (DistributedJDBCBrokerFactory) getPooledFactoryForKey(key);
		if (factory != null)
			return factory;

		factory = newInstance(cp);
		pool(key, factory);
		return factory;
	}

	/**
	 * Factory method for constructing a factory from a configuration.
	 */
	public static synchronized JDBCBrokerFactory getInstance(
			JDBCConfiguration conf) {
	    Map properties = conf.toProperties(false);
	    Object key = toPoolKey(properties);
		DistributedJDBCBrokerFactory factory =
                (DistributedJDBCBrokerFactory) getPooledFactoryForKey(key);
		if (factory != null)
			return factory;

		factory = new DistributedJDBCBrokerFactory(
		        (DistributedJDBCConfiguration) conf);
		pool(key, factory);
		return factory;
	}

	public DistributedJDBCBrokerFactory(DistributedJDBCConfiguration conf) {
		super(conf);
	}
	
	@Override
	public DistributedJDBCConfiguration getConfiguration() {
	    return (DistributedJDBCConfiguration)super.getConfiguration();
	}
	
	public Slice addSlice(String name, Map properties) {
	    Slice slice = getConfiguration().addSlice(name, properties);
        ClassLoader loader = AccessController.doPrivileged(
            J2DoPrivHelper.getContextClassLoaderAction());
        synchronizeMappings(loader, (JDBCConfiguration)slice.
                getConfiguration());
	    return slice;
	}

	@Override
	protected DistributedJDBCStoreManager newStoreManager() {
		return new DistributedJDBCStoreManager(getConfiguration());
	}
	
    @Override
    protected Object getFactoryInitializationBanner() {
        return _loc.get("factory-init", OpenJPAVersion.VERSION_NUMBER);
    }
}
