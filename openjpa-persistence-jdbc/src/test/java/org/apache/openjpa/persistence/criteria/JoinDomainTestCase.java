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
 package org.apache.openjpa.persistence.criteria;

import javax.persistence.EntityManager;

import org.apache.openjpa.persistence.OpenJPAEntityManagerFactorySPI;

public abstract class JoinDomainTestCase extends AbstractCriteriaTestCase {
    protected static OpenJPAEntityManagerFactorySPI emf;
    protected static SQLAuditor auditor;

    protected CriteriaBuilder cb;
    protected EntityManager em;

    protected Class<?>[] getDomainClasses() {
        return new Class[]{A.class,B.class,C.class,D.class};
    }

    public void setUp() {
        if (getEntityManagerFactory() == null) {
            auditor = new SQLAuditor();
            setEntityManagerFactory(createNamedEMF(getDomainClasses()));
            assertNotNull(getEntityManagerFactory());
            setDictionary();
        }
        em = getEntityManagerFactory().createEntityManager();
        cb = getEntityManagerFactory().getQueryBuilder();
    }

    protected OpenJPAEntityManagerFactorySPI getEntityManagerFactory() {
        return emf;
    }

    protected void setEntityManagerFactory(OpenJPAEntityManagerFactorySPI emf) {
        JoinDomainTestCase.emf = emf;
    }

    protected SQLAuditor getAuditor() {
        return auditor;
    }

    protected void setAuditor(SQLAuditor auditor) {
        JoinDomainTestCase.auditor = auditor;
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return cb;
    }

    protected void setCriteriaBuilder(CriteriaBuilder cb) {
        this.cb = cb;
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    protected void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
