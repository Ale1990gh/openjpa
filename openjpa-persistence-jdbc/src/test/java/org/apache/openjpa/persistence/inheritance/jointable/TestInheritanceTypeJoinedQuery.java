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
package org.apache.openjpa.persistence.inheritance.jointable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.openjpa.persistence.test.SQLListenerTestCase;


public class TestInheritanceTypeJoinedQuery  extends SQLListenerTestCase {

    public void setUp() {
        setUp(Contractor.class, Employee.class, Department.class, Person.class,
            CLEAR_TABLES);
        populate();
    }

    public void populate() {
        EntityManager em = emf.createEntityManager();
        
        Department d = new Department("IT");
        for (int i = 0; i < 3; i++) {
            Contractor c = new Contractor("ctr" + i);
            c.setDept(d);
            em.persist(c);
        }
        em.persist(d);
      
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
    }

    public void testInheritanceTypeJoinedQuery() {
        EntityManager em = emf.createEntityManager();
        Query q = null;
        String qS = null;
        Department dept = null;
        
        qS = "SELECT c.OID, c.dept FROM Department d, Contractor c where d.OID = c.dept.OID and d.description = 'IT'";
        q = em.createQuery(qS); 
        List<Object[]> lResult = q.getResultList();
        for (Object[] resultElement : lResult) {
            Long oid = (Long)resultElement[0];
            dept = (Department)resultElement[1];
        }
        
        qS = "SELECT c.OID FROM Department d, Contractor c where d.OID = c.dept.OID and d.description = 'IT'";
        q = em.createQuery(qS); 
        for (Object resultElement : q.getResultList()) {
            Long oid = (Long)resultElement;
        }
       
        qS = "SELECT d FROM Department d, Contractor c where d.OID = c.dept.OID and d.description = 'IT'";
        q = em.createQuery(qS); 
        for (Department aResult: (List <Department>) q.getResultList()) {
            assertEquals(dept.getOID(), aResult.getOID());
        }

        qS = "SELECT c FROM Department d, Contractor c  where d.OID = c.dept.OID and d.description = 'IT'";
        q = em.createQuery(qS);             
        for (Contractor aResult: (List <Contractor>) q.getResultList()) {
            //System.out.println(aResult.getDescription() + ", " + aResult.getOID());
            assertEquals(dept.getOID(), aResult.getDept().getOID());
        }
        qS = "SELECT c FROM Contractor c, Department d  where d.OID = c.dept.OID and d.description = 'IT'";
        q = em.createQuery(qS);             
        for (Contractor aResult: (List <Contractor>) q.getResultList()) {
            assertEquals(dept.getOID(), aResult.getDept().getOID());
        }
        
        qS = "SELECT c, c.OID FROM Department d, Contractor c where d.OID = c.dept.OID and d.description = 'IT'";
        q = em.createQuery(qS);             
        List<Object[]> cResult = q.getResultList();
        Contractor contractor = null;
        for (Object[] resultElement : cResult) {
            contractor = (Contractor)resultElement[0];
            Long oid = (Long)resultElement[1];
            assertTrue(contractor.getOID() == oid);
            assertEquals(dept.getOID(), contractor.getDept().getOID());
        }
        
        qS = "SELECT c.OID, c FROM Contractor c, Department d where d.OID = c.dept.OID and d.description = 'IT'";
        q = em.createQuery(qS);             
        List<Object[]> dResult = q.getResultList();
        for (Object[] resultElement : dResult) {
            Long oid = (Long)resultElement[0];
            contractor = (Contractor)resultElement[1];
            assertTrue(contractor.getOID() == oid);
            assertEquals(dept.getOID(), contractor.getDept().getOID());
        }
        
        qS = "SELECT c, c.OID FROM Department d, Contractor c where d.OID = c.dept.OID and d.description = 'IT'";
        q = em.createQuery(qS);             
        List<Object[]> eResult = q.getResultList();
        for (Object[] resultElement : eResult) {
            Long oid = (Long)resultElement[1];
            contractor = (Contractor)resultElement[0];
            assertTrue(contractor.getOID() == oid);
            assertEquals(dept.getOID(), contractor.getDept().getOID());
        }

        qS = "SELECT c.OID, c FROM Department d, Contractor c where d.OID = c.dept.OID and d.description = 'IT'";
        q = em.createQuery(qS);             
        List<Object[]> fResult = q.getResultList();
        for (Object[] resultElement : fResult) {
            Long oid = (Long)resultElement[0];
            Contractor c = (Contractor)resultElement[1];
            assertTrue(oid.longValue() == c.getOID());
            assertEquals(dept.getOID(), c.getDept().getOID());
        }
        
        qS = "SELECT d,c FROM Department d, Contractor c where d.OID = c.dept.OID and d.description = 'IT' " +
                " and c = ?1";
        q = em.createQuery(qS);
        q.setParameter(1, contractor);
        for (Object[] aResult: (List <Object[]>) q.getResultList()) {
            System.out.println(((Department)aResult[0]).getOID() + ", " + ((Contractor)aResult[1]).getOID());
            assertTrue(contractor.equals(aResult[1]));
        }
                
        qS = "SELECT c,d FROM Contractor c, Department d where d.OID = c.dept.OID and d.description = 'IT' " +
                " and c = ?1";
        q = em.createQuery(qS);
        q.setParameter(1, contractor);
        for (Object[] aResult: (List <Object[]>) q.getResultList()) {
            System.out.println(((Contractor)aResult[0]).getOID() + ", " + ((Department)aResult[1]).getOID());
            assertTrue(contractor.equals(aResult[0]));
        }

        qS = "SELECT p FROM Person p ";
        q = em.createQuery(qS);
        for (Object aResult: (List<Object>) q.getResultList()) {
            assertTrue(aResult instanceof Contractor);
        }

        em.close();
    }
}

