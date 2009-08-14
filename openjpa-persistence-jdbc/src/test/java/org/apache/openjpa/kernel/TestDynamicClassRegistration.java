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
package org.apache.openjpa.kernel;

import java.util.List;
import java.util.Collections;
import javax.persistence.EntityManager;

import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.UnenhancedFieldAccess;
import org.apache.openjpa.enhance.ManagedClassSubclasser;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.persistence.JPAFacadeHelper;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactorySPI;
import org.apache.openjpa.persistence.query.SimpleEntity;
import org.apache.openjpa.persistence.test.AbstractCachedEMFTestCase;
import org.apache.openjpa.persistence.test.PersistenceTestCase;

public class TestDynamicClassRegistration
    extends AbstractCachedEMFTestCase {

    private OpenJPAEntityManagerFactorySPI emf;

    public void setUp() throws Exception {
        super.setUp();

        emf = createNamedEMF("empty-pu");
    }

    public void testEnhancedDynamicClassRegistration()
        throws ClassNotFoundException {
        assertTrue(
            PersistenceCapable.class.isAssignableFrom(SimpleEntity.class));

        // trigger class initialization. We could just do 'new SimpleEntity()'.
        Class.forName(SimpleEntity.class.getName(), true,
            getClass().getClassLoader());

        ClassMetaData meta =
            JPAFacadeHelper.getMetaData(emf, SimpleEntity.class);
        assertNotNull(meta);
        EntityManager em = emf.createEntityManager();
        javax.persistence.Query q = em.createQuery("select o from simple o");
        em.close();
    }

    public void testUnenhancedDynamicClassRegistration() {
        assertFalse(PersistenceCapable.class.isAssignableFrom(
            UnenhancedFieldAccess.class));

        // trigger class initialization
        ManagedClassSubclasser.prepareUnenhancedClasses(
            emf.getConfiguration(),
            Collections.singleton(UnenhancedFieldAccess.class),
            null);

        ClassMetaData meta =
            JPAFacadeHelper.getMetaData(emf, UnenhancedFieldAccess.class);
        assertNotNull(meta);
        EntityManager em = emf.createEntityManager();
        javax.persistence.Query q = em.createQuery(
            "select o from UnenhancedFieldAccess o");
        em.close();
    }
}
