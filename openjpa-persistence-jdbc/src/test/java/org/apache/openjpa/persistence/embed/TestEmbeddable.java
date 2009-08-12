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
package org.apache.openjpa.persistence.embed;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;


import org.apache.openjpa.persistence.ArgumentException;
import org.apache.openjpa.persistence.test.SingleEMFTestCase;

public class TestEmbeddable extends SingleEMFTestCase {
   
    public int numEmbeddables = 1;
    public int numBasicTypes = 1;
    public int numProgramManagers = 2;
    public int numNickNames = 3;
    public int numEmployeesPerPhoneNumber = 1;
    public int numPhoneNumbersPerEmployee = 2;
    public int numEmployeesPerProgramManager = 2;
    public int numEmployees = numProgramManagers
        * numEmployeesPerProgramManager;
    public int numPhoneNumbers = numEmployees * numPhoneNumbersPerEmployee;
    public int numDepartments = 2;
    public int numEmployeesPerDept = 2;
    public int numItems = 2;
    public int numImagesPerItem = 3;
    public int numCompany = 2;
    public int numDivisionsPerCo = 2;
    public int ID = 1;
    public int itemId = 1;
    public int compId = 1;
    public int divId = 1;
    public int vpId = 1;
    public int deptId = 1;
    public int empId = 1;
    public int phoneId = 1;
    public int pmId = 1;
    public int parkingSpotId = 1;
    public Map<Integer, PhoneNumber> phones =
        new HashMap<Integer, PhoneNumber>();
    public Map<Integer, Employee> employees = new HashMap<Integer, Employee>();

    public void setUp() {
        setUp(Embed.class, Embed_Coll_Embed.class, Embed_Coll_Integer.class, 
            Embed_Embed.class, Embed_Embed_ToMany.class, Embed_ToMany.class, 
            Embed_ToOne.class, EntityA_Coll_Embed_ToOne.class, 
            EntityA_Coll_String.class, EntityA_Embed_Coll_Embed.class, 
            EntityA_Embed_Coll_Integer.class, EntityA_Embed_Embed.class, 
            EntityA_Embed_Embed_ToMany.class, EntityA_Embed_ToMany.class, 
            EntityA_Embed_ToOne.class, EntityB1.class, 
            EntityA_Coll_Embed_Embed.class, ContactInfo.class,
            Employee.class, JobInfo.class, LocationDetails.class,
            ParkingSpot.class, PhoneNumber.class, ProgramManager.class,
            Department1.class, Employee1.class, Department2.class,
            Employee2.class, EmployeePK2.class, Department3.class,
            Employee3.class, EmployeeName3.class, Item1.class, Item2.class,
            Item3.class, Item4.class, Item5.class, FileName4.class, 
            Company1.class, Company2.class, Division.class,   
            VicePresident.class, EntityA_Embed_MappedToOne.class,
            Embed_MappedToOne.class, Embed_MappedToOneCascadeDelete.class, 
            EntityA_Embed_MappedToOneCascadeDelete.class, EntityB2.class, 
            Book.class, Listing.class, Seller.class, DROP_TABLES);
    }
    
    public void testEntityA_Coll_String() {
        createEntityA_Coll_String();
        queryEntityA_Coll_String();
        findEntityA_Coll_String();
    }

    public void testEntityA_Embed_ToOne() {
        createEntityA_Embed_ToOne();
        queryEntityA_Embed_ToOne();
        findEntityA_Embed_ToOne();
    }

    public void testEntityA_Embed_MappedToOne() {
        createEntityA_Embed_MappedToOne();
        queryEntityA_Embed_MappedToOne();
        findEntityA_Embed_MappedToOne();
    }

    public void testEntityA_Coll_Embed_ToOne() {
        createEntityA_Coll_Embed_ToOne();
        queryEntityA_Coll_Embed_ToOne();
        findEntityA_Coll_Embed_ToOne();
    }

    public void testEntityA_Embed_ToMany() {
        createEntityA_Embed_ToMany();
        queryEntityA_Embed_ToMany();
        findEntityA_Embed_ToMany();
    }

    public void testEntityA_Embed_Embed_ToMany() {
        createEntityA_Embed_Embed_ToMany();
        queryEntityA_Embed_Embed_ToMany();
        findEntityA_Embed_Embed_ToMany();
    }

    public void testEntityA_Embed_Coll_Integer() {
        createEntityA_Embed_Coll_Integer();
        queryEntityA_Embed_Coll_Integer();
        findEntityA_Embed_Coll_Integer();
    }

    public void testEntityA_Embed_Embed() {
        createEntityA_Embed_Embed();
        queryEntityA_Embed_Embed();
        findEntityA_Embed_Embed();
    }

    public void testEntityA_Coll_Embed_Embed() {
        createEntityA_Coll_Embed_Embed();
        queryEntityA_Coll_Embed_Embed();
        findEntityA_Coll_Embed_Embed();
    }

    public void testEntityA_Embed_Coll_Embed() {
        createEntityA_Embed_Coll_Embed();
        queryEntityA_Embed_Coll_Embed();
        findEntityA_Embed_Coll_Embed();
    }
    
    public void testEmployee() {
        createEmployeeObj();
        queryEmployeeObj();
        findEmployeeObj();
    }

    public void testMapKey() {
        createObjMapKey();
        queryObjMapKey();
        findObjMapKey();
    }
    
    public void testMapKeyClass() {
        createObjMapKeyClass();
        queryObjMapKeyClass();
        findObjMapKeyClass();
    }

    public void testMapKeyEnumerated() {
        createObjMapKeyEnumerated();
        findObjMapKeyEnumerated();
    }

    public void testMapKeyTemporal() {
        createObjMapKeyTemporal();
        findObjMapKeyTemporal();
    }

    public void testEntityA_Embed_MappedToOneCascadeDelete() {
        createEntityA_Embed_MappedToOneCascadeDelete();
        updateEntityA_Embed_MappedToOneCascadeDelete();
    }
    
    public void testEmbeddableContainingRelationWithGeneratedKey() {
        createEmbeddableContainingRelationWithGeneratedKey();
    }
    /*
     * Create EntityA_Coll_String
     */
    public void createEntityA_Coll_String() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Coll_String(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Coll_String(EntityManager em, int id) {
        EntityA_Coll_String a = new EntityA_Coll_String();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        for (int i = 0; i < numBasicTypes; i++)
            a.addNickName("nickName_" + id + i);
        a.addCreditRating(EntityA_Coll_String.CreditRating.POOR);
        a.addTimestamp(new Timestamp(System.currentTimeMillis()));
        a.addLob("lob");
        em.persist(a);
    }

    /*
     * Create EntityA_Embed_ToOne
     */
    public void createEntityA_Embed_ToOne() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Embed_ToOne(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Embed_ToOne(EntityManager em, int id) {
        EntityA_Embed_ToOne a = new EntityA_Embed_ToOne();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        Embed_ToOne embed = createEmbed_ToOne(em, id);
        a.setEmbed(embed);
        em.persist(a);
    }

    public Embed_ToOne createEmbed_ToOne(EntityManager em, int id) {
        Embed_ToOne embed = new Embed_ToOne();
        embed.setName1("name1");
        embed.setName2("name2");
        embed.setName3("name3");
        EntityB1 b = new EntityB1();
        b.setId(id);
        b.setName("b" + id);
        embed.setEntityB(b);
        em.persist(b);
        return embed;
    }

    /*
     * Create EntityA_Embed_MappedToOne
     */
    public void createEntityA_Embed_MappedToOne() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Embed_MappedToOne(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Embed_MappedToOne(EntityManager em, int id) {
        EntityA_Embed_MappedToOne a = new EntityA_Embed_MappedToOne();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        Embed_MappedToOne embed = createEmbed_MappedToOne(em, id, a);
        a.setEmbed(embed);
        em.persist(a);
    }

    public Embed_MappedToOne createEmbed_MappedToOne(EntityManager em, int id, 
        EntityA_Embed_MappedToOne a) {
        Embed_MappedToOne embed = new Embed_MappedToOne();
        embed.setName1("name1");
        embed.setName2("name2");
        embed.setName3("name3");
        EntityB1 b = new EntityB1();
        b.setId(id);
        b.setName("bm" + id);
        b.setEntityA(a);
        embed.setMappedEntityB(b);
        em.persist(b);
        return embed;
    }

    /*
     * Create EntityA_Embed_MappedToOneCascadeDelete
     */
    public void createEntityA_Embed_MappedToOneCascadeDelete() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Embed_MappedToOneCascadeDelete(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Embed_MappedToOneCascadeDelete(EntityManager em, 
        int id) {
        EntityA_Embed_MappedToOneCascadeDelete a = 
            new EntityA_Embed_MappedToOneCascadeDelete();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        Embed_MappedToOneCascadeDelete embed = 
            createEmbed_MappedToOneDeleteCascade(em, id, a);
        a.setEmbed(embed);
        em.persist(a);
    }

    public Embed_MappedToOneCascadeDelete createEmbed_MappedToOneDeleteCascade(
        EntityManager em, int id, EntityA_Embed_MappedToOneCascadeDelete a) {
        Embed_MappedToOneCascadeDelete embed =
            new Embed_MappedToOneCascadeDelete();
        embed.setName1("name1");
        embed.setName2("name2");
        embed.setName3("name3");
        EntityB2 b = new EntityB2();
        b.setId(id);
        b.setName("bm" + id);
        b.setEntityA(a);
        embed.setMappedEntityB(b);
        em.persist(b);
        return embed;
    }
    
    /*
     * Update EntityA_Embed_MappedToOneCascadeDelete
     */
    public void updateEntityA_Embed_MappedToOneCascadeDelete() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        updateEntityA_Embed_MappedToOneCascadeDelete(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.clear();
        
        EntityA_Embed_MappedToOneCascadeDelete a = 
            em.find(EntityA_Embed_MappedToOneCascadeDelete.class, ID);
        assertNotNull(a);
        
        EntityB2 b2 = em.find(EntityB2.class, ID);
        assertNotNull(b2);
        
        em.close();
    }

    public void updateEntityA_Embed_MappedToOneCascadeDelete(EntityManager em, 
        int id) {
        EntityA_Embed_MappedToOneCascadeDelete a = 
            em.find(EntityA_Embed_MappedToOneCascadeDelete.class, id);
        a.setName("newa" + id);
        a.setAge(id + 1);
        Embed_MappedToOneCascadeDelete embed = 
            createEmbed_MappedToOneDeleteCascade(em, id+1, a);
        a.setEmbed(embed);
    }
    
    /*
     * Create EntityA_Coll_Embed_ToOne
     */
    public void createEntityA_Coll_Embed_ToOne() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Coll_Embed_ToOne(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Coll_Embed_ToOne(EntityManager em, int id) {
        EntityA_Coll_Embed_ToOne a = new EntityA_Coll_Embed_ToOne();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        for (int i = 0; i < numEmbeddables; i++) {
            Embed_ToOne embed = createEmbed_ToOne(em, i+id);
            a.addEmbed1ToOne(embed);
        }
        em.persist(a);
    }

    /*
     * Create EntityA_Embed_ToMany
     */
    public void createEntityA_Embed_ToMany() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Embed_ToMany(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Embed_ToMany(EntityManager em, int id) {
        EntityA_Embed_ToMany a = new EntityA_Embed_ToMany();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        Embed_ToMany embed = createEmbed_ToMany(em, id);
        a.setEmbed(embed);
        em.persist(a);
    }

    public Embed_ToMany createEmbed_ToMany(EntityManager em, int id) {
        Embed_ToMany embed = new Embed_ToMany();
        embed.setName1("name1");
        embed.setName2("name2");
        embed.setName3("name3");
        for (int i = 0; i < numEmbeddables; i++) {
            EntityB1 b = new EntityB1();
            b.setId(id + i);
            b.setName("b" + id + i);
            embed.addEntityB(b);
            em.persist(b);
        }
        return embed;
    }

   /*
     * Create EntityA_Embed_Embed_ToMany
     */
    public void createEntityA_Embed_Embed_ToMany() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Embed_Embed_ToMany(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Embed_Embed_ToMany(EntityManager em, int id) {
        EntityA_Embed_Embed_ToMany a = new EntityA_Embed_Embed_ToMany();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        Embed_Embed_ToMany embed = createEmbed_Embed_ToMany(em, id);
        a.setEmbed(embed);
        em.persist(a);
    }

    public Embed_Embed_ToMany createEmbed_Embed_ToMany(EntityManager em,
            int id) {
        Embed_Embed_ToMany embed = new Embed_Embed_ToMany();
        embed.setIntVal1(1);
        embed.setIntVal2(2);
        embed.setIntVal3(3);
        Embed_ToMany embed_ToMany = createEmbed_ToMany(em, id);
        embed.setEmbed(embed_ToMany);
        return embed;
    }
    
    /*
     * Create EntityA_Embed_Coll_Integer
     */
    public void createEntityA_Embed_Coll_Integer() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Embed_Coll_Integer(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Embed_Coll_Integer(EntityManager em, int id) {
        EntityA_Embed_Coll_Integer a = new EntityA_Embed_Coll_Integer();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        Embed_Coll_Integer embed = createEmbed_Coll_Integer(em, id);
        a.setEmbed(embed);
        em.persist(a);
    }

    public Embed_Coll_Integer createEmbed_Coll_Integer(EntityManager em,
            int id) {
        Embed_Coll_Integer embed = new Embed_Coll_Integer();
        embed.setIntVal1(id*10 + 1);
        embed.setIntVal2(id*10 + 2);
        embed.setIntVal3(id*10 + 3);
        for (int i = 0; i < numBasicTypes; i++) {
            embed.addOtherIntVal(id * 100 + i);
        }
        return embed;
    }

    /*
     * Create EntityA_Embed_Embed
     */
    public void createEntityA_Embed_Embed() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Embed_Embed(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Embed_Embed(EntityManager em, int id) {
        EntityA_Embed_Embed a = new EntityA_Embed_Embed();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        Embed_Embed embed = createEmbed_Embed(em, id, 0);
        a.setEmbed(embed);
        em.persist(a);
    }

    public Embed_Embed createEmbed_Embed(EntityManager em, int id, int idx) {
        Embed_Embed embed = new Embed_Embed();
        embed.setIntVal1(id * 100 + idx * 10 + 1);
        embed.setIntVal2(id * 100 + idx * 10 + 2);
        embed.setIntVal3(id * 100 + idx * 10 + 3);
        Embed embed1 = createEmbed(id, idx);
        embed.setEmbed(embed1);
        return embed;
    }

    public Embed createEmbed(int id, int idx) {
        Embed embed = new Embed();
        embed.setIntVal1(id * 100 + idx * 10 + 4);
        embed.setIntVal2(id * 100 + idx * 10 + 5);
        embed.setIntVal3(id * 100 + idx * 10 + 6);
        return embed;
    }

    /*
     * Create EntityA_Coll_Embed_Embed
     */
    public void createEntityA_Coll_Embed_Embed() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Coll_Embed_Embed(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Coll_Embed_Embed(EntityManager em, int id) {
        EntityA_Coll_Embed_Embed a = new EntityA_Coll_Embed_Embed();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        for (int i = 0; i < numEmbeddables; i++) {
            Embed_Embed embed = createEmbed_Embed(em, id, i);
            a.addEmbed(embed);
        }
        em.persist(a);
    }

    /*
     * Create EntityA_Embed_Coll_Embed
     */
    public void createEntityA_Embed_Coll_Embed() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createEntityA_Embed_Coll_Embed(em, ID);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createEntityA_Embed_Coll_Embed(EntityManager em, int id) {
        EntityA_Embed_Coll_Embed a = new EntityA_Embed_Coll_Embed();
        a.setId(id);
        a.setName("a" + id);
        a.setAge(id);
        Embed_Coll_Embed embed = createEmbed_Coll_Embed(em, id);
        a.setEmbed(embed);
        em.persist(a);
    }

    public Embed_Coll_Embed createEmbed_Coll_Embed(EntityManager em, int id) {
        Embed_Coll_Embed embed = new Embed_Coll_Embed();
        embed.setIntVal1(id * 10 + 1);
        embed.setIntVal2(id * 10 + 2);
        embed.setIntVal3(id * 10 + 3);
        for (int i = 0; i < numEmbeddables; i++) {
            Embed embed1 = createEmbed(id, i);
            embed.addEmbed(embed1);
        }
        return embed;
    }

    /*
     * Create Employee
     */
    public void createEmployeeObj() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        createPhoneNumbers(em);
        createEmployees(em);
        createProgramManagers(em);
        
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }
    
    public void createProgramManagers(EntityManager em) {
        empId = 1;
        for (int i = 0; i < numProgramManagers; i++)
            createProgramManager(em, pmId++);
    }
    
    public void createProgramManager(EntityManager em, int id) {
        ProgramManager pm = new ProgramManager();
        pm.setId(id);
        for (int i = 0; i < numEmployeesPerProgramManager; i++) {
            Employee e = employees.get(empId++);
            pm.addManage(e);
            JobInfo jobInfo = new JobInfo();
            jobInfo.setJobDescription("jobDescription" + e.getEmpId());
            jobInfo.setProgramManager(pm);
            e.setJobInfo(jobInfo);
        }
        em.persist(pm);
    }
    
    public void createEmployees(EntityManager em) {
        phoneId = 1;
        for (int i = 0; i < numEmployees; i++) {
            Employee e = createEmployee(em, empId++);
            employees.put(e.getEmpId(), e);
        }
    }

    public Employee createEmployee(EntityManager em, int id) {
        Employee e = new Employee();
        e.setEmpId(id);
        ContactInfo contactInfo = new ContactInfo();
        for (int i = 0; i < numPhoneNumbersPerEmployee; i++) { 
            PhoneNumber phoneNumber = phones.get(phoneId++);
            contactInfo.addPhoneNumber(phoneNumber);
            e.setContactInfo(contactInfo);
            phoneNumber.addEmployees(e);
            em.persist(phoneNumber);
        }
        ParkingSpot parkingSpot = createParkingSpot(em, parkingSpotId++);
        LocationDetails location = new LocationDetails();
        location.setOfficeNumber(id);
        location.setParkingSpot(parkingSpot);
        e.setLocationDetails(location);
        parkingSpot.setAssignedTo(e);
        for (int i = 0; i < numNickNames; i++)
            e.addNickName("nickName" + id + i);
        em.persist(parkingSpot);
        em.persist(e);
        return e;
    }
    
    public void createPhoneNumbers(EntityManager em) {
        for (int i = 0; i < numPhoneNumbers; i++) {
            PhoneNumber p = new PhoneNumber();
            p.setNumber(phoneId++);
            phones.put(p.getNumber(), p);
            em.persist(p);
        }
    }    
    
    public ParkingSpot createParkingSpot(EntityManager em, int id) {
        ParkingSpot p = new ParkingSpot();
        p.setId(id);
        p.setGarage("garage" + id);
        em.persist(p);
        return p;
    }    

    public void findEmployeeObj() {
        EntityManager em = emf.createEntityManager();
        ProgramManager pm = em.find(ProgramManager.class, 1);
        assertProgramManager(pm);

        pm = em.find(ProgramManager.class, 2);
        assertProgramManager(pm);

        Employee e = em.find(Employee.class, 1);
        assertEmployee(e);
        
        PhoneNumber p = em.find(PhoneNumber.class, 1);
        assertPhoneNumber(p);
        
        ParkingSpot ps = em.find(ParkingSpot.class, 1);
        assertParkingSpot(ps);
       
        em.close();
    }
    
    public void queryEmployeeObj() {
        queryProgramManager(emf);
        queryEmployeeObj(emf);
        queryPhoneNumber(emf);
        queryParkingSpot(emf);
    }
    
    public void queryParkingSpot(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select p from ParkingSpot p");
        List<ParkingSpot> ps = q.getResultList();
        for (ParkingSpot p : ps){
            assertParkingSpot(p);
        }
        tran.commit();
        em.close();
    }
    
    public void queryProgramManager(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select pm from ProgramManager pm");
        List<ProgramManager> pms = q.getResultList();
        for (ProgramManager pm : pms){
            assertProgramManager(pm);
        }
        tran.commit();
        em.close();
    }

    public void queryPhoneNumber(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select p from PhoneNumber p");
        List<PhoneNumber> ps = q.getResultList();
        for (PhoneNumber p : ps){
            assertPhoneNumber(p);
        }
        tran.commit();
        em.close();
    }

    public void queryEmployeeObj(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select e from Employee e");
        List<Employee> es = q.getResultList();
        for (Employee e : es){
            assertEmployee(e);
        }
        tran.commit();
        em.clear();
        // test range variable over element collection
        String[] query = {
            "select e from Employee e, in (e.nickNames) n " +
                " where n like '%1'",  
        };
        for (int i = 0; i < query.length; i++) {
            es = em.createQuery(query[i]).getResultList();
            for (Employee e : es){
                assertEmployee(e);
            }
        }
        em.close();
    }

    public void assertProgramManager(ProgramManager pm) {
        int id = pm.getId();
        Collection<Employee> es = pm.getManages();
        assertEquals(numEmployeesPerProgramManager, es.size());
        for (Employee e : es) {
            assertEmployee(e);
        }
    }
    
    public void assertEmployee(Employee e) {
        int id = e.getEmpId();
        ContactInfo c = e.getContactInfo();
        List<PhoneNumber> phones = c.getPhoneNumbers();
        assertEquals(numPhoneNumbersPerEmployee, phones.size());
        for (PhoneNumber p : phones) {
            assertPhoneNumber(p);
        }
        
        LocationDetails loc = e.getLocationDetails();
        int officeNumber = loc.getOfficeNumber();
        ParkingSpot p = loc.getParkingSpot();
        assertParkingSpot(p);
        ProgramManager pm = e.getJobInfo().getProgramManager();
        Set<String> nickNames = e.getNickNames();
        assertEquals(numNickNames, nickNames.size());
        
    }
    
    public void assertPhoneNumber(PhoneNumber p) {
        int number = p.getNumber();
        Collection<Employee> es = p.getEmployees();
        assertEquals(numEmployeesPerPhoneNumber, es.size());
    }
    
    public void assertParkingSpot(ParkingSpot p) {
        String garage = p.getGarage();
        Employee e = p.getAssignedTo();
    }
    
    /*
     * Find EntityA_Coll_String
     */
    public void findEntityA_Coll_String() {
        EntityManager em = emf.createEntityManager();
        EntityA_Coll_String a = em.find(EntityA_Coll_String.class, ID);
        checkEntityA_Coll_String(a);
        em.close();
    }

    /*
     * Find EntityA_Embed_ToOne
     */
    public void findEntityA_Embed_ToOne() {
        EntityManager em = emf.createEntityManager();
        EntityA_Embed_ToOne a = em.find(EntityA_Embed_ToOne.class, ID);
        checkEntityA_Embed_ToOne(a);
        em.close();
    }

    /*
     * Find EntityA_Embed_MappedToOne
     */
    public void findEntityA_Embed_MappedToOne() {
        EntityManager em = emf.createEntityManager();
        EntityA_Embed_MappedToOne a =
            em.find(EntityA_Embed_MappedToOne.class, ID);
        checkEntityA_Embed_MappedToOne(a);
        em.close();
    }

    /*
     * Find EntityA_Coll_Embed_ToOne
     */
    public void findEntityA_Coll_Embed_ToOne() {
        EntityManager em = emf.createEntityManager();
        EntityA_Coll_Embed_ToOne a =
            em.find(EntityA_Coll_Embed_ToOne.class, ID);
        checkEntityA_Coll_Embed_ToOne(a);
        em.close();
    }

    /*
     * Find EntityA_Embed_ToMany
     */
    public void findEntityA_Embed_ToMany() {
        EntityManager em = emf.createEntityManager();
        EntityA_Embed_ToMany a = em.find(EntityA_Embed_ToMany.class, ID);
        checkEntityA_Embed_ToMany(a);
        em.close();
    }

    /*
     * Find EntityA_Embed_Embed_ToMany
     */
    public void findEntityA_Embed_Embed_ToMany() {
        EntityManager em = emf.createEntityManager();
        EntityA_Embed_Embed_ToMany a =
            em.find(EntityA_Embed_Embed_ToMany.class, ID);
        checkEntityA_Embed_Embed_ToMany(a);
        em.close();
    }

    /*
     * Find EntityA_Embed_Coll_Integer
     */
    public void findEntityA_Embed_Coll_Integer() {
        EntityManager em = emf.createEntityManager();
        EntityA_Embed_Coll_Integer a =
            em.find(EntityA_Embed_Coll_Integer.class, ID);
        checkEntityA_Embed_Coll_Integer(a);
        em.close();
    }

    /*
     * Find EntityA_Embed_Embed
     */
    public void findEntityA_Embed_Embed() {
        EntityManager em = emf.createEntityManager();
        EntityA_Embed_Embed a = em.find(EntityA_Embed_Embed.class, ID);
        checkEntityA_Embed_Embed(a);
        em.close();
    }

    /*
     * Find EntityA_Coll_Embed_Embed
     */
    public void findEntityA_Coll_Embed_Embed() {
        EntityManager em = emf.createEntityManager();
        EntityA_Coll_Embed_Embed a =
            em.find(EntityA_Coll_Embed_Embed.class, ID);
        checkEntityA_Coll_Embed_Embed(a);
        
        em.clear();
        em.getTransaction().begin();
        Embed_Embed embed = createEmbed_Embed(em, ID, 100);
        a.addEmbed(embed);
        em.merge(a);
        em.getTransaction().commit();
        em.close();
    }

    /*
     * Find EntityA_Embed_Coll_Embed
     */
    public void findEntityA_Embed_Coll_Embed() {
        EntityManager em = emf.createEntityManager();
        EntityA_Embed_Coll_Embed a =
            em.find(EntityA_Embed_Coll_Embed.class, ID);
        checkEntityA_Embed_Coll_Embed(a);
        em.close();
    }

    /*
     * check EntityA_Coll_String
     */
    public void checkEntityA_Coll_String(EntityA_Coll_String a) {
        int id = a.getId();
        String name = a.getName();
        int age = a.getAge();
        assertEquals(1, id);
        assertEquals("a" + id ,name);
        assertEquals(1, age);
        Set<String> nickNames = a.getNickNames();
        for (String nickName : nickNames)
            assertEquals("nickName_" + id + "0", nickName);
        List<EntityA_Coll_String.CreditRating> cr = a.getCreditRating();
        for (EntityA_Coll_String.CreditRating c : cr)
            assertEquals("POOR", c.toString());
    }

    /*
     * check EntityA_Embed_ToOne
     */
    public void checkEntityA_Embed_ToOne(EntityA_Embed_ToOne a) {
        int id = a.getId();
        String name = a.getName();
        int age = a.getAge();
        assertEquals(1, id);
        assertEquals("a" + id ,name);
        assertEquals(1, age);
        Embed_ToOne embed = a.getEmbed();
        checkEmbed_ToOne(embed);
    }

    /*
     * check EntityA_Embed_MappedToOne
     */
    public void checkEntityA_Embed_MappedToOne(EntityA_Embed_MappedToOne a) {
        int id = a.getId();
        String name = a.getName();
        int age = a.getAge();
        assertEquals(1, id);
        assertEquals("a" + id ,name);
        assertEquals(1, age);
        Embed_MappedToOne embed = a.getEmbed();
        checkEmbed_MappedToOne(embed);
    }

    /*
     * check EntityA_Coll_Embed_ToOne
     */
    public void checkEntityA_Coll_Embed_ToOne(EntityA_Coll_Embed_ToOne a) {
        int id = a.getId();
        String name = a.getName();
        int age = a.getAge();
        assertEquals(1, id);
        assertEquals("a" + id ,name);
        assertEquals(1, age);
        Set<Embed_ToOne> embeds = a.getEmbed1ToOnes();
        for (Embed_ToOne embed : embeds)
            checkEmbed_ToOne(embed);
    }

    public void checkEmbed_ToOne(Embed_ToOne embed) {
        String name1 = embed.getName1();
        String name2 = embed.getName2();
        String name3 = embed.getName3();
        assertEquals("name1", name1);
        assertEquals("name2", name2);
        assertEquals("name3", name3);
        EntityB1 b = embed.getEntityB();
        assertEquals(1, b.getId());
        assertEquals("b" + b.getId(), b.getName());
    }

    public void checkEmbed_MappedToOne(Embed_MappedToOne embed) {
        String name1 = embed.getName1();
        String name2 = embed.getName2();
        String name3 = embed.getName3();
        assertEquals("name1", name1);
        assertEquals("name2", name2);
        assertEquals("name3", name3);
        EntityB1 b = embed.getMappedEntityB();
        assertEquals(1, b.getId());
        assertEquals("bm" + b.getId(), b.getName());
    }

    /*
     * check EntityA_Embed_ToMany
     */
    public void checkEntityA_Embed_ToMany(EntityA_Embed_ToMany a) {
        int id = a.getId();
        String name = a.getName();
        int age = a.getAge();
        assertEquals(1, id);
        assertEquals("a" + id ,name);
        assertEquals(1, age);
        Embed_ToMany embed = a.getEmbed();
        checkEmbed_ToMany(embed);
    }

    public void checkEmbed_ToMany(Embed_ToMany embed) {
        String name1 = embed.getName1();
        String name2 = embed.getName2();
        String name3 = embed.getName3();
        assertEquals("name1", name1);
        assertEquals("name2", name2);
        assertEquals("name3", name3);
        List<EntityB1> bs = embed.getEntityBs();
        for (EntityB1 b : bs) {
            assertEquals(1, b.getId());
            assertEquals("b" + b.getId() + "0", b.getName());
        }
    }

    /*
     * check EntityA_Embed_Embed_ToMany
     */
    public void checkEntityA_Embed_Embed_ToMany(EntityA_Embed_Embed_ToMany a) {
        int id = a.getId();
        String name = a.getName();
        int age = a.getAge();
        assertEquals(1, id);
        assertEquals("a" + id ,name);
        assertEquals(1, age);
        Embed_Embed_ToMany embed = a.getEmbed();
        checkEmbed_Embed_ToMany(embed);
    }
    
    public void checkEmbed_Embed_ToMany(Embed_Embed_ToMany embed) {
        int intVal1 = embed.getIntVal1();
        int intVal2 = embed.getIntVal2();
        int intVal3 = embed.getIntVal3();
        assertEquals(1, intVal1);
        assertEquals(2, intVal2);
        assertEquals(3, intVal3);
        Embed_ToMany embed1 = embed.getEmbed();
        checkEmbed_ToMany(embed1);
    }
    
    /*
     * check EntityA_Embed_Coll_Integer
     */
    public void checkEntityA_Embed_Coll_Integer(EntityA_Embed_Coll_Integer a) {
        int id = a.getId();
        String name = a.getName();
        int age = a.getAge();
        assertEquals(1, id);
        assertEquals("a" + id ,name);
        assertEquals(1, age);
        Embed_Coll_Integer embed = a.getEmbed();
        checkEmbed_Integers(embed);
    }

    public void checkEmbed_Integers(Embed_Coll_Integer embed) {
        int intVal1 = embed.getIntVal1();
        int intVal2 = embed.getIntVal2();
        int intVal3 = embed.getIntVal3();
        assertEquals(11, intVal1);
        assertEquals(12, intVal2);
        assertEquals(13, intVal3);
        Set<Integer> intVals = embed.getOtherIntVals();
        for (Integer intVal : intVals) {
            assertEquals(100, intVal.intValue());
        }
    }

    /*
     * check EntityA_Embed_Embed
     */
    public void checkEntityA_Embed_Embed(EntityA_Embed_Embed a) {
        int id = a.getId();
        String name = a.getName();
        int age = a.getAge();
        assertEquals(1, id);
        assertEquals("a" + id ,name);
        assertEquals(1, age);
        Embed_Embed embed = a.getEmbed();
        checkEmbed_Embed(embed);
    }

    public void checkEmbed_Embed(Embed_Embed embed) {
        int intVal1 = embed.getIntVal1();
        int intVal2 = embed.getIntVal2();
        int intVal3 = embed.getIntVal3();
        assertEquals(101, intVal1);
        assertEquals(102, intVal2);
        assertEquals(103, intVal3);
        Embed embed1 = embed.getEmbed();
        checkEmbed(embed1);
    }

    public void checkEmbed(Embed embed) {
        int intVal1 = embed.getIntVal1();
        int intVal2 = embed.getIntVal2();
        int intVal3 = embed.getIntVal3();
        assertEquals(104, intVal1);
        assertEquals(105, intVal2);
        assertEquals(106, intVal3);
    }

    /*
     * check EntityA_Coll_Embed_Embed
     */
    public void checkEntityA_Coll_Embed_Embed(EntityA_Coll_Embed_Embed a) {
        int id = a.getId();
        String name = a.getName();
        int age = a.getAge();
        assertEquals(1, id);
        assertEquals("a" + id ,name);
        assertEquals(1, age);
        List<Embed_Embed> embeds = a.getEmbeds();
        for (Embed_Embed embed : embeds)
            checkEmbed_Embed(embed);
    }

    /*
     * check EntityA_Embed_Coll_Embed
     */
    public void checkEntityA_Embed_Coll_Embed(EntityA_Embed_Coll_Embed a) {
        int id = a.getId();
        String name = a.getName();
        int age = a.getAge();
        assertEquals(1, id);
        assertEquals("a" + id ,name);
        assertEquals(1, age);
        Embed_Coll_Embed embed = a.getEmbed();
        checkEmbed_Coll_Embed(embed);
    }

    public void checkEmbed_Coll_Embed(Embed_Coll_Embed embed) {
        int intVal1 = embed.getIntVal1();
        int intVal2 = embed.getIntVal2();
        int intVal3 = embed.getIntVal3();
        assertEquals(11, intVal1);
        assertEquals(12, intVal2);
        assertEquals(13, intVal3);
        List<Embed> embeds = embed.getEmbeds();
        for (Embed embed1 : embeds)
            checkEmbed(embed1);
    }

    /*
     * Query EntityA_Coll_String
     */
    public void queryEntityA_Coll_String() {
        EntityManager em = emf.createEntityManager();
        String[] query = {
            "select e from " +
                " EntityA_Coll_String a " +
                " , in (a.nickNames) e order by a.id",
            "select e from " +
                " EntityA_Coll_String a " +
                " , in (a.nickNames) e order by a.id",
            "select e from " +
                " EntityA_Coll_String a " +
                " , in (a.nickNames) e order by e",
            "select a from " +
                " EntityA_Coll_String a " +
                " WHERE a.nickNames IS EMPTY order by a",
            "select a from " +
                " EntityA_Coll_String a " +
                " WHERE exists (select n from EntityA_Coll_String a, " +
                " in (a.nickNames) n where n like '%1') " +
                " order by a",
        };
        List rs = null;
        for (int i = 0; i < query.length; i++) {
            rs = em.createQuery(query[i]).getResultList();
            switch (i) {
            case 0:
            case 1:
            case 2:
                assertTrue(rs.size() > 0);
                Object obj = rs.get(0);
                assertTrue(obj instanceof String);
                break;
            case 3:
            case 4:
                assertTrue(rs.size() == 0);
            }
            em.clear();
        }
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select a from EntityA_Coll_String a");
        List<EntityA_Coll_String> as = q.getResultList();
        for (EntityA_Coll_String a : as) {
            checkEntityA_Coll_String(a);
        }
        tran.commit();
        em.close();
    }

    /*
     * Query EntityA_Embed_ToOne
     */
    public void queryEntityA_Embed_ToOne() {
        EntityManager em = emf.createEntityManager();
        // test select embed object
        String[] query = {
            "select a.embed from " +
                " EntityA_Embed_ToOne a ",
            "select a.embed from " +
                " EntityA_Embed_ToOne a ",
            "select e from EntityA_Embed_ToOne a " +
                " join a.embed e join e.b b where e.b.id > 0 order by a.id",
            "select e from EntityA_Embed_ToOne a " +
                " join a.embed e join e.b b where e.b.id > 0 order by a.id",
            "select a.embed from " +
                " EntityA_Embed_ToOne a ORDER BY a.embed",
            "select a.embed from " +
                " EntityA_Embed_ToOne a WHERE a.embed.b IS NOT NULL " +
                " ORDER BY a.embed",
            "select a.embed from " +
                " EntityA_Embed_ToOne a WHERE exists " +
                " (select a from EntityA_Embed_ToOne a " +
                " where a.embed.b IS NOT NULL) " +
                " ORDER BY a.embed",
        };
        for (int i = 0; i < query.length; i++) {
            List<Object[]> rs = null;
            rs = em.createQuery(query[i]).getResultList();
            assertTrue(rs.size() > 0);
            Object obj = rs.get(0);
            assertTrue(obj instanceof Embed_ToOne);
            assertTrue(((Embed_ToOne) obj).getEntityB() != null);
            em.clear();
        }
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select a from EntityA_Embed_ToOne a");
        List<EntityA_Embed_ToOne> as = q.getResultList();
        for (EntityA_Embed_ToOne a : as) {
            checkEntityA_Embed_ToOne(a);
        }
        tran.commit();
        em.close();
    }

    /*
     * Query EntityA_Embed_MappedToOne
     */
    public void queryEntityA_Embed_MappedToOne() {
        EntityManager em = emf.createEntityManager();
        // test select embed object
        String[] query = {
            "select a.embed from " +
                " EntityA_Embed_MappedToOne a ",
            "select e from EntityA_Embed_MappedToOne a " +
                " join a.embed e join e.bm bm where e.bm.id > 0 order by a.id",
            "select a.embed as e from " +
                " EntityA_Embed_MappedToOne a ORDER BY e",
            "select a.embed from " +
                " EntityA_Embed_MappedToOne a WHERE a.embed.bm IS NOT NULL",
            "select a.embed from " +
                " EntityA_Embed_MappedToOne a " +
                " WHERE exists " +
                " (select a from EntityA_Embed_MappedToOne a " +
                " where a.embed.bm IS NOT NULL)",
        };
        for (int i = 0; i < query.length; i++) {
            List<Object[]> rs = null;
            rs = em.createQuery(query[i]).getResultList();
            assertTrue(rs.size() > 0);
            Object obj = rs.get(0);
            assertTrue(obj instanceof Embed_MappedToOne);
            assertTrue(((Embed_MappedToOne) obj).getMappedEntityB() != null);
            em.clear();
        }
        String[] query2 = {
            // embeddable in conditional expression should fail
            "select a.embed from " +
                " EntityA_Embed_MappedToOne a " +
                " WHERE exists " +
                " (select e from EntityA_Embed_MappedToOne a" +
                " join a.embed e " +
                " where e.bm IS NOT NULL)",
            "select a.embed from " +
                " EntityA_Embed_MappedToOne a " +
                " WHERE exists " +
                " (select a.embed from EntityA_Embed_MappedToOne a " +
                " where a.embed.bm IS NOT NULL)",
        };
        for (int i = 0; i < query2.length; i++) {
            List<Object[]> rs = null;
            try {
                rs = em.createQuery(query2[i]).getResultList();
            } catch (ArgumentException e) {
                // as expected
            }
        }

        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select a from EntityA_Embed_MappedToOne a");
        List<EntityA_Embed_MappedToOne> as = q.getResultList();
        for (EntityA_Embed_MappedToOne a : as) {
            checkEntityA_Embed_MappedToOne(a);
        }
        tran.commit();
        em.close();
    }

    /*
     * Query EntityA_Coll_Embed_ToOne
     */
    public void queryEntityA_Coll_Embed_ToOne() {
        EntityManager em = emf.createEntityManager();
        // test select embed object from element collection
        String[] query = {
            "select e, e.b from " +
                " EntityA_Coll_Embed_ToOne a " +
                " , in (a.embed1s) e order by e.name1",
            "select e, a.id from EntityA_Coll_Embed_ToOne a " +
                " , in (a.embed1s) e where e.b.id > 0 order by a.id",
            "select e, e.b.id  from " +
                " EntityA_Coll_Embed_ToOne a " +
                " , in (a.embed1s) e where e.name1 like '%1'" +
                " order by e.name3",
            "select e, e.b.id  from " +
                " EntityA_Coll_Embed_ToOne a " +
                " , in (a.embed1s) e where e.name1 like '%1'" +
                " order by e",
            "select e, e.b.id  from " +
                " EntityA_Coll_Embed_ToOne a " +
                " , in (a.embed1s) e where e.name1 like '%1' and" +
                " a.embed1s IS NOT EMPTY and " +
                " e.b IS NOT NULL " +
                " order by e",
            // the following query works in DB2 but not in Derby:
            // because Derby subquery is only allowed to 
            // return a single column. 
            /*
            "select e, e.b.id  from " +
                " EntityA_Coll_Embed_ToOne a " +
                " , in (a.embed1s) e where " +
                " exists (select a.embed1s from " +
                " EntityA_Coll_Embed_ToOne a) and " +
                " exists (select e.b from a.embed1s e) " +
                " order by e",
            */
        };
        List<Object[]> rs = null;
        for (int i = 0; i < query.length; i++) {
            rs = em.createQuery(query[i]).getResultList();
            assertTrue(rs.size() > 0);
            Object obj = ((Object[]) rs.get(0))[0];
            assertTrue(obj instanceof Embed_ToOne);
            assertTrue(((Embed_ToOne) obj).getEntityB() != null);
            switch (i) {
            case 0:
                Object b = ((Object[]) rs.get(0))[1];
                assertTrue(b instanceof EntityB1);
                assertEquals(((EntityB1) b).getId(),
                    ((Embed_ToOne) obj).getEntityB().getId());
                break;
            }
            em.clear();
        }
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select a from EntityA_Coll_Embed_ToOne a");
        List<EntityA_Coll_Embed_ToOne> as = q.getResultList();
        for (EntityA_Coll_Embed_ToOne a : as) {
            checkEntityA_Coll_Embed_ToOne(a);
        }
        tran.commit();
        em.close();
    }

    /*
     * Query EntityA_Embed_ToMany
     */
    public void queryEntityA_Embed_ToMany() {
        EntityManager em = emf.createEntityManager();
        // test select embeddable
        String query[] = {
            "select a.embed from EntityA_Embed_ToMany a",
            "select e from EntityA_Embed_ToMany a join a.embed e",
            "select b from EntityA_Embed_ToMany a join a.embed.bs" +
                " b",
            "select e from EntityA_Embed_ToMany a join a.embed e " +
                " where e.name1 like '%1'",
            "select a.embed from EntityA_Embed_ToMany a ORDER BY a.embed",
            "select e from EntityA_Embed_ToMany a join a.embed e ORDER BY e",
            "select b from EntityA_Embed_ToMany a join a.embed.bs" +
                " b ORDER BY b",
            "select e from EntityA_Embed_ToMany a join a.embed e " +
                " WHERE e.bs IS NOT EMPTY ORDER BY e",
            "select a from EntityA_Embed_ToMany a " +
                " WHERE exists (select a from EntityA_Embed_ToMany a" +
                " where a.embed.bs IS NOT EMPTY) ORDER BY a",
            "select a from EntityA_Embed_ToMany a " +
                " WHERE exists (select a from EntityA_Embed_ToMany a" +
                " where a.embed.bs IS NOT EMPTY) ORDER BY a",
            };
        List rs = null;
        for (int i = 0; i < query.length; i++) {
            rs = em.createQuery(query[i]).getResultList();
            assertTrue(rs.size() > 0);
            Object obj = rs.get(0);
            switch (i) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 7:
                assertTrue(obj instanceof Embed_ToMany);
                assertTrue(((Embed_ToMany) obj).getEntityBs().size() > 0);
                break;
            case 2:
            case 6:
                assertTrue(obj instanceof EntityB1);
                break;
            case 8:
            case 9:
                assertTrue(obj instanceof EntityA_Embed_ToMany);
                break;
            }
            em.clear();
        }
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select a from EntityA_Embed_ToMany a");
        List<EntityA_Embed_ToMany> as = q.getResultList();
        for (EntityA_Embed_ToMany a : as) {
            checkEntityA_Embed_ToMany(a);
        }
        tran.commit();
        em.close();
    }

    /*
     * Query EntityA_Embed_Embed_ToMany
     */
    public void queryEntityA_Embed_Embed_ToMany() {
        EntityManager em = emf.createEntityManager();
        // test select embeddable
        String query[] = {
            "select a.embed from EntityA_Embed_Embed_ToMany a",
            "select a.embed from EntityA_Embed_Embed_ToMany a" +
                " where a.embed.embed.name1 like '%1' ",
            "select a.embed.embed from EntityA_Embed_Embed_ToMany a",
            "select b from EntityA_Embed_Embed_ToMany a join a.embed.embed.bs" +
                " b",
            "select a.embed.embed from EntityA_Embed_Embed_ToMany a " +
                " where a.embed.embed.name1 like '%1'",
            "select e2 from EntityA_Embed_Embed_ToMany a " +
                " left join a.embed e1 left join e1.embed e2",
            "select e2 from EntityA_Embed_Embed_ToMany a " +
                " join a.embed e1 join e1.embed e2",
            "select a.embed as e from EntityA_Embed_Embed_ToMany a ORDER BY e",
            "select a.embed.embed as e from EntityA_Embed_Embed_ToMany a " +
                " where a.embed.embed.name1 like '%1' ORDER BY e",
            "select a.embed from EntityA_Embed_Embed_ToMany a " +
                " where a.embed.embed.bs IS NOT EMPTY",
            "select a.embed from EntityA_Embed_Embed_ToMany a " +
                " where exists (select a.embed.embed.bs from " +
                " EntityA_Embed_Embed_ToMany a)",
            "select b from EntityA_Embed_Embed_ToMany a join a.embed.embed.bs" +
                " b",
        };
        List rs = null;
        for (int i = 0; i < query.length; i++) {
            rs = em.createQuery(query[i]).getResultList();
            assertTrue(rs.size() > 0);
            Object obj = rs.get(0);
            switch (i) {
            case 0:
            case 1:
            case 7:
            case 9:
            case 10:
                assertTrue(obj instanceof Embed_Embed_ToMany);
                assertTrue(((Embed_Embed_ToMany) obj).getEmbed().getEntityBs().
                    size() > 0);
                break;
            case 2:
            case 4:
            case 5:
            case 6:
            case 8:
                assertTrue(obj instanceof Embed_ToMany);
                assertTrue(((Embed_ToMany) obj).getEntityBs().size() > 0);
                break;
            case 3:
            case 11:
                assertTrue(obj instanceof EntityB1);
                break;
            }        
            em.clear();
        }
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select a from EntityA_Embed_Embed_ToMany a");
        List<EntityA_Embed_Embed_ToMany> as = q.getResultList();
        for (EntityA_Embed_Embed_ToMany a : as) {
            checkEntityA_Embed_Embed_ToMany(a);
        }
        tran.commit();
        em.close();
    }

    /*
     * Query EntityA_Embed_Coll_Integer
     */
    public void queryEntityA_Embed_Coll_Integer() {
        EntityManager em = emf.createEntityManager();
        // test select embed object from element collection in embeddable object
        String[] query = {
            "select e, a.id from " +
                " EntityA_Embed_Coll_Integer a " +
                " , in (a.embed.otherIntVals) e order by e",
            "select e, a.id from " +
                " EntityA_Embed_Coll_Integer a " +
                " , in (a.embed.otherIntVals) e order by e",
            "select e, a.id from " +
                " EntityA_Embed_Coll_Integer a " +
                " , in (a.embed.otherIntVals) e order by a.id",
            "select e, a.embed.intVal1 from EntityA_Embed_Coll_Integer a " +
                " , in (a.embed.otherIntVals) e order by a.id",
            "select e, a.embed.intVal2 from " +
                " EntityA_Embed_Coll_Integer a " +
                " , in (a.embed.otherIntVals) e order by e",
            "select e, a.embed.intVal2 from " +
                " EntityA_Embed_Coll_Integer a " +
                " , in (a.embed.otherIntVals) e " +
                " WHERE a.embed.otherIntVals IS NOT EMPTY order by e",
            "select e, a.embed.intVal2 from " +
                " EntityA_Embed_Coll_Integer a " +
                " , in (a.embed.otherIntVals) e " +
                " WHERE exists (select a from " +
                " EntityA_Embed_Coll_Integer a " +
                " , in (a.embed.otherIntVals) e " +
                " where e > 0) order by e",
            "select e, a0.intVal2 from EntityA_Embed_Coll_Integer a " +
                "JOIN a.embed a0 JOIN a0.otherIntVals e WHERE exists (select a from " +
                " EntityA_Embed_Coll_Integer a JOIN a.embed a0 JOIN a0.otherIntVals e " +
                " where e > 0) order by e",
        };
        List<Object[]> rs = null;
        for (int i = 0; i < query.length; i++) {
            rs = em.createQuery(query[i]).getResultList();
            assertTrue(rs.size() > 0);
            Object obj = ((Object[]) rs.get(0))[0];
            assertTrue(obj instanceof Integer);
            em.clear();
        }

        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select a from EntityA_Embed_Coll_Integer a");
        List<EntityA_Embed_Coll_Integer> as = q.getResultList();
        for (EntityA_Embed_Coll_Integer a : as) {
            checkEntityA_Embed_Coll_Integer(a);
        }
        tran.commit();
        em.close();
    }

    /*
     * Query EntityA_Embed_Embed
     */
    public void queryEntityA_Embed_Embed() {
        EntityManager em = emf.createEntityManager();
        // test select embeddable
        String query[] = {
            "select a.embed from EntityA_Embed_Embed a",
            "select a.embed.embed from EntityA_Embed_Embed a",
            "select a.embed as e from EntityA_Embed_Embed a ORDER BY e",
        };
        String query2[] = {
            "select a.embed from EntityA_Embed_Embed a WHERE a.embed.embed " +
                " IS NOT NULL",
            "select a.embed from EntityA_Embed_Embed a " + " WHERE exists " +
                " (select a.embed.embed from EntityA_Embed_Embed a" +
                " where a.embed IS NOT NULL) ",
        };
        List rs = null;
        for (int i = 0; i < query.length; i++) {
            rs = em.createQuery(query[i]).getResultList();
            assertTrue(rs.size() > 0);
            switch (i) {
            case 0:
            case 2:
                assertTrue(rs.get(0) instanceof Embed_Embed);
                break;
            case 1:
                assertTrue(rs.get(0) instanceof Embed);
                break;
            }
            em.clear();
        }
        for (int i = 0; i < query2.length; i++) {
            try {
            rs = em.createQuery(query2[i]).getResultList();
            } catch(ArgumentException e) {
                // as expected
            }
        }
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select a from EntityA_Embed_Embed a");
        List<EntityA_Embed_Embed> as = q.getResultList();
        for (EntityA_Embed_Embed a : as) {
            checkEntityA_Embed_Embed(a);
        }
        tran.commit();
        em.close();
    }

    /*
     * Query EntityA_Coll_Embed_Embed
     */
    public void queryEntityA_Coll_Embed_Embed() {
        EntityManager em = emf.createEntityManager();
        // test select embed object from element collection
        String[] query = {
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e order by e.intVal3",
            "select e, a.id from EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e order by a.id",
            "select e, a.id from EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e order by e desc",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE a.embeds IS NOT EMPTY " +
                " order by e.intVal3",
            // the following query works in DB2 but not in Derby,
            // because Derby subquery is only allowed to 
            // return a single column. 
            /*
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE exists (select a.embeds " +
                " from EntityA_Coll_Embed_Embed a) " +
                " order by e.intVal3",
            */
        };
        String[] query2 = {
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 < ANY (select e2.intVal2 " +
                " from EntityA_Coll_Embed_Embed a1, in (a1.embeds) e2) " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 < ALL (select e2.intVal2 " +
                " from EntityA_Coll_Embed_Embed a1, in (a1.embeds) e2) " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 <= SOME " +
                " (select e2.intVal2 " +
                " from EntityA_Coll_Embed_Embed a1, in (a1.embeds) e2) " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 > ALL (select e2.intVal2 " +
                " from EntityA_Coll_Embed_Embed a1, in (a1.embeds) e2) " +
                " order by e.intVal3",
            // non-corelated subquery:
            // TODO: known problem in table alias resolution for subquery
            //       the genarated SQL subquery should be non-corelated,
            //       but generated corelated subquery.
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 < ANY (select e.intVal2 " +
                " from EntityA_Coll_Embed_Embed a, in (a.embeds) e) " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 < ALL (select e.intVal2 " +
                " from EntityA_Coll_Embed_Embed a, in (a.embeds) e) " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 <= SOME " +
                " (select e.intVal2 " +
                " from EntityA_Coll_Embed_Embed a, in (a.embeds) e) " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 > ALL (select e.intVal2 " +
                " from EntityA_Coll_Embed_Embed a, in (a.embeds) e) " +
                " order by e.intVal3",
            // corelated subquery:
            // TODO: known problem in table alias resolution for subquery
            //       the genarated SQL subquery should be corelated,
            //       but generated non-corelated subquery.
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 < ANY (select e2.intVal2 " +
                " from in(a.embeds) e2) " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 < ALL (select e2.intVal2 " +
                " from a.embeds e2) " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 <= SOME " +
                " (select e2.intVal2 " +
                " from in(a.embeds) e2) " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE e.intVal1 > ALL (select e2.intVal2 " +
                " from a.embeds e2) " +
                " order by e.intVal3",
        };
        String[] query3 = {
            // query with parameter of embeddable object
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE ?1 MEMBER OF a.embeds " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " left join a.embeds e WHERE ?1 MEMBER OF a.embeds " +
                " order by e.intVal3",
            "select e, e.intVal1, e.embed.intVal2 from " +
                " EntityA_Coll_Embed_Embed a " +
                " , in (a.embeds) e WHERE ?1 = e " +
                " order by e.intVal3",
        };

        List rs = null;
        Object obj = null;
        for (int i = 0; i < query.length; i++) {
            rs = em.createQuery(query[i]).getResultList();
            assertTrue(rs.size() > 0);
            obj = ((Object[]) rs.get(0))[0];
            assertTrue(obj instanceof Embed_Embed);
        }
        for (int i = 0; i < query2.length; i++) {
            rs = em.createQuery(query2[i]).getResultList();
            if (rs.size() > 0) {
                obj = ((Object[]) rs.get(0))[0];
                assertTrue(obj instanceof Embed_Embed);
            }
        }
        for (int i = 0; i < query3.length; i++) {
            try {
            rs = em.createQuery(query3[i]).setParameter(1, obj).getResultList();
            } catch(ArgumentException e) {
                // as expected
            }
        }

        em.clear();

        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select a from EntityA_Coll_Embed_Embed a");
        List<EntityA_Coll_Embed_Embed> as = q.getResultList();
        for (EntityA_Coll_Embed_Embed a : as) {
            checkEntityA_Coll_Embed_Embed(a);
        }
        tran.commit();
        em.close();
    }

    /*
     * Query EntityA_Embed_Coll_Embed
     */
    public void queryEntityA_Embed_Coll_Embed() {
        EntityManager em = emf.createEntityManager();
        // test select embed object from element collection in embeddable object
        String[] query = {
            "select e, e.intVal1, e.intVal2 from " +
                " EntityA_Embed_Coll_Embed a " +
                " , in (a.embed.embeds) e order by e",
            "select e, e.intVal1 from " +
                " EntityA_Embed_Coll_Embed a " +
                " , in (a.embed.embeds) e order by e.intVal3",
            "select e, a.id from EntityA_Embed_Coll_Embed a " +
                " , in (a.embed.embeds) e order by a.id",
            "select e, e.intVal1, e.intVal2 from " +
                " EntityA_Embed_Coll_Embed a " +
                " , in (a.embed.embeds) e order by e.intVal3",
            "select e, e.intVal1, e.intVal2 from " +
                " EntityA_Embed_Coll_Embed a " +
                " , in (a.embed.embeds) e where a.embed.embeds IS NOT EMPTY" +
                " order by e",
            "select e, e.intVal1, e.intVal2 from " +
                " EntityA_Embed_Coll_Embed a " +
                " , in (a.embed.embeds) e where exists (select e.intVal1 " +
                " from EntityA_Embed_Coll_Embed a, in (a.embed.embeds) e " +
                " where e.intVal2 = 105) " +
                " order by e",
            "select e, a from " +
                " EntityA_Embed_Coll_Embed a " +
                " , in (a.embed.embeds) e " +
                " where e.intVal1 = SOME " +
                " (select e2.intVal1 from " +
                " EntityA_Embed_Coll_Embed a2 " +
                " , in (a2.embed.embeds) e2) " +
                " order by e",
        };
        String[] query2 = {
            "select e, a from " +
                " EntityA_Embed_Coll_Embed a " +
                " , in (a.embed.embeds) e " +
                " where e = ?1 order by e",
        };
        List<Object[]> rs = null;
        EntityA_Embed_Coll_Embed ea = null;
        Object obj = null;
        for (int i = 0; i < query.length; i++) {
            rs = em.createQuery(query[i]).getResultList();
            assertTrue(rs.size() > 0);
            obj = ((Object[]) rs.get(0))[0];
            assertTrue(obj instanceof Embed);
            if (i == query.length-1)
                ea = (EntityA_Embed_Coll_Embed) ((Object[]) rs.get(0))[1];
        }

        for (int i = 0; i < query2.length; i++) {
            try {
            rs = em.createQuery(query2[i]).
                setParameter(1, (Embed) obj).
                getResultList();
            } catch (ArgumentException e) {
                // expected exception
            }
        }
        try {
            rs = em.createQuery("select a from " +
                " EntityA_Embed_Coll_Embed a " +
                " where a.embed = ?1 order by e").
                setParameter(1, ea.getEmbed()).
                getResultList();
        } catch (ArgumentException e) {
            // expected exception
        }
        em.clear();

        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select a from EntityA_Embed_Coll_Embed a");
        List<EntityA_Embed_Coll_Embed> as = q.getResultList();
        for (EntityA_Embed_Coll_Embed a : as) {
            checkEntityA_Embed_Coll_Embed(a);
        }
        tran.commit();
        em.close();
    }
    
    public void createObjMapKey() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        for (int i = 0; i < numDepartments; i++)
            createDepartment1(em, deptId++);
        for (int i = 0; i < numDepartments; i++)
            createDepartment2(em, deptId++);
        for (int i = 0; i < numDepartments; i++)
            createDepartment3(em, deptId++);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createObjMapKeyEnumerated() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        Item4 item = new Item4();
        item.setId(1);
        FileName4 fileName1 = new FileName4("file" + 1, "file" + 1);
        item.addImage(Item4.Catagory.A1, fileName1);

        FileName4 fileName2 = new FileName4("file" + 2, "file" + 2);
        item.addImage(Item4.Catagory.A2, fileName2);

        FileName4 fileName3 = new FileName4("file" + 3, "file" + 3);
        item.addImage(Item4.Catagory.A3, fileName3);

        em.persist(item);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }
    
    public void createObjMapKeyTemporal() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        Item5 item = new Item5();
        item.setId(1);
        long ts = System.currentTimeMillis();
        Timestamp ts1 = new Timestamp(ts);
        Timestamp ts2 = new Timestamp(ts+1000000);
        Timestamp ts3 = new Timestamp(ts+2000000);
        
        FileName4 fileName1 = new FileName4("file" + 1, "file" + 1);
        item.addImage(ts1, fileName1);

        FileName4 fileName2 = new FileName4("file" + 2, "file" + 2);
        item.addImage(ts2, fileName2);

        FileName4 fileName3 = new FileName4("file" + 3, "file" + 3);
        item.addImage(ts3, fileName3);

        em.persist(item);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createObjMapKeyClass() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        for (int i = 0; i < numItems; i++)
            createItem1(em, itemId++);
        for (int i = 0; i < numItems; i++)
            createItem2(em, itemId++);
        for (int i = 0; i < numItems; i++)
            createItem3(em, itemId++);
        for (int i = 0; i < numCompany; i++)
            createCompany1(em, compId++);
        for (int i = 0; i < numCompany; i++)
            createCompany2(em, compId++);
        tran.begin();
        em.flush();
        tran.commit();
        em.close();
    }

    public void createItem1(EntityManager em, int id) {
        Item1 item = new Item1();
        item.setId(id);
        for (int i = 0; i < numImagesPerItem; i++) {
            item.addImage("image" + id + i, "file" + id + i);
        }
        em.persist(item);
    }
    
    public void createItem2(EntityManager em, int id) {
        Item2 item = new Item2();
        item.setId(id);
        for (int i = 0; i < numImagesPerItem; i++) {
            item.addImage("image" + id + i, "file" + id + i);
        }
        em.persist(item);
    }

    public void createItem3(EntityManager em, int id) {
        Item3 item = new Item3();
        item.setId(id);
        for (int i = 0; i < numImagesPerItem; i++) {
            item.addImage("image" + id + i, "file" + id + i);
        }
        em.persist(item);
    }

    public void createCompany1(EntityManager em, int id) {
        Company1 c = new Company1();
        c.setId(id);
        for (int i = 0; i < numDivisionsPerCo; i++) {
            Division d = createDivision(em, divId++);
            VicePresident vp = createVicePresident(em, vpId++);
            c.addToOrganization(d, vp);
            em.persist(d);
            em.persist(vp);
        }
        em.persist(c);
    }
    
    public void createCompany2(EntityManager em, int id) {
        Company2 c = new Company2();
        c.setId(id);
        for (int i = 0; i < numDivisionsPerCo; i++) {
            Division d = createDivision(em, divId++);
            VicePresident vp = createVicePresident(em, vpId++);
            c.addToOrganization(d, vp);
            em.persist(d);
            em.persist(vp);
        }
        em.persist(c);
    }

    public Division createDivision(EntityManager em, int id) {
        Division d = new Division();
        d.setId(id);
        d.setName("d" + id);
        return d;
    }
    
    public VicePresident createVicePresident(EntityManager em, int id) {
        VicePresident vp = new VicePresident();
        vp.setId(id);
        vp.setName("vp" + id);
        return vp;
    }    

    public void createDepartment1(EntityManager em, int id) {
        Department1 d = new Department1();
        d.setDeptId(id);
        Map emps = new HashMap();
        for (int i = 0; i < numEmployeesPerDept; i++) {
            Employee1 e = createEmployee1(em, empId++);
            //d.addEmployee1(e);
            emps.put(e.getEmpId(), e);
            e.setDepartment(d);
            em.persist(e);
        }
        d.setEmpMap(emps);
        em.persist(d);
    }
    
    public Employee1 createEmployee1(EntityManager em, int id) {
        Employee1 e = new Employee1();
        e.setEmpId(id);
        return e;
    }
    
    public void createDepartment2(EntityManager em, int id) {
        Department2 d = new Department2();
        d.setDeptId(id);
        for (int i = 0; i < numEmployeesPerDept; i++) {
            Employee2 e = createEmployee2(em, empId++);
            d.addEmployee(e);
            e.setDepartment(d);
            em.persist(e);
        }
        em.persist(d);
    }
    
    public Employee2 createEmployee2(EntityManager em, int id) {
        Employee2 e = new Employee2("e" + id, new Date());
        return e;
    }
    
    public void createDepartment3(EntityManager em, int id) {
        Department3 d = new Department3();
        d.setDeptId(id);
        for (int i = 0; i < numEmployeesPerDept; i++) {
            Employee3 e = createEmployee3(em, empId++);
            d.addEmployee(e);
            e.setDepartment(d);
            em.persist(e);
        }
        em.persist(d);
    }
    
    public Employee3 createEmployee3(EntityManager em, int id) {
        Employee3 e = new Employee3();
        EmployeeName3 name = new EmployeeName3("f" + id, "l" + id);
        e.setEmpId(id);
        e.setName(name);
        return e;
    }

    public void findObjMapKey() {
        EntityManager em = emf.createEntityManager();
        Department1 d1 = em.find(Department1.class, 1);
        assertDepartment1(d1);
        
        Employee1 e1 = em.find(Employee1.class, 1);
        assertEmployee1(e1);
        
        Department2 d2 = em.find(Department2.class, 3);
        assertDepartment2(d2);
        
        Map emps = d2.getEmpMap();
        Set<EmployeePK2> keys = emps.keySet();
        for (EmployeePK2 key : keys) {
            Employee2 e2 = em.find(Employee2.class, key);
            assertEmployee2(e2);
        }
        
        Department3 d3 = em.find(Department3.class, 5);
        assertDepartment3(d3);
        
        Employee3 e3 = em.find(Employee3.class, 9);
        assertEmployee3(e3);
        
        em.close();
    }
    
    public void assertDepartment1(Department1 d) {
        int id = d.getDeptId();
        Map<Integer, Employee1> es = d.getEmpMap();
        assertEquals(2,es.size());
        Set keys = es.keySet();
        for (Object obj : keys) {
            Integer empId = (Integer) obj;
            Employee1 e = es.get(empId);
            assertEquals(empId.intValue(), e.getEmpId());
        }
    }
    
    public void assertDepartment2(Department2 d) {
        int id = d.getDeptId();
        Map<EmployeePK2, Employee2> es = d.getEmpMap();
        assertEquals(2,es.size());
        Set<EmployeePK2> keys = es.keySet();
        for (EmployeePK2 pk : keys) {
            Employee2 e = es.get(pk);
            assertEquals(pk, e.getEmpPK());
        }
    }   

    public void assertDepartment3(Department3 d) {
        int id = d.getDeptId();
        Map<EmployeeName3, Employee3> es = d.getEmployees();
        assertEquals(2,es.size());
        Set<EmployeeName3> keys = es.keySet();
        for (EmployeeName3 key : keys) {
            Employee3 e = es.get(key);
            assertEquals(key, e.getName());
        }
    }
    
    public void assertEmployee1(Employee1 e) {
        int id = e.getEmpId();
        Department1 d = e.getDepartment();
        assertDepartment1(d);
    }
    
    public void assertEmployee2(Employee2 e) {
        EmployeePK2 pk = e.getEmpPK();
        Department2 d = e.getDepartment();
        assertDepartment2(d);
    }
    
    public void assertEmployee3(Employee3 e) {
        int id = e.getEmpId();
        Department3 d = e.getDepartment();
        assertDepartment3(d);
    }

    public void queryObjMapKey() {
        queryDepartment(emf);
        queryEmployee(emf);
    }
    
    public void queryDepartment(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q1 = em.createQuery("select d from Department1 d");
        List<Department1> ds1 = q1.getResultList();
        for (Department1 d : ds1){
            assertDepartment1(d);
        }
        
        Query q2 = em.createQuery("select d from Department2 d");
        List<Department2> ds2 = q2.getResultList();
        for (Department2 d : ds2){
            assertDepartment2(d);
        }

        Query q3 = em.createQuery("select d from Department3 d");
        List<Department3> ds3 = q3.getResultList();
        for (Department3 d : ds3){
            assertDepartment3(d);
        }
        
        tran.commit();

        String query[] = {
            "select d from Department1 d join d.empMap e " +
                " where KEY(e) > 1 order by d",
            "select d from Department1 d join d.empMap e" +
                " where d.deptId = KEY(e) order by d",
            "select d from Department1 d " +
                " where d.deptId < ANY " +
                " (select KEY(e) from in(d.empMap) e) " +
                " order by d",
            "select d from Department1 d " +
                " where d.deptId < SOME " +
                " (select KEY(e) from Department1 d1, in(d1.empMap) e) " +
                " order by d",
        };
        for (int i = 0; i < query.length; i++) {
            ds1 = em.createQuery(query[i]).getResultList();
            assertDepartment1(ds1.get(0));
        }
        em.close();
    }

    public void queryEmployee(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q1 = em.createQuery("select e from Employee1 e");
        List<Employee1> es1 = q1.getResultList();
        for (Employee1 e : es1){
            assertEmployee1(e);
        }

        Query q2 = em.createQuery("select e from Employee2 e");
        List<Employee2> es2 = q2.getResultList();
        for (Employee2 e : es2){
            assertEmployee2(e);
        }

        Query q3 = em.createQuery("select e from Employee3 e");
        List<Employee3> es3 = q3.getResultList();
        for (Employee3 e : es3){
            assertEmployee3(e);
        }
        tran.commit();
        em.close();
    }
    
    public void findObjMapKeyClass() {
        EntityManager em = emf.createEntityManager();
        
        Item1 item1 = em.find(Item1.class, 1);
        assertItem1(item1);
        
        Item2 item2 = em.find(Item2.class, 3);
        assertItem2(item2);
        
        Item3 item3 = em.find(Item3.class, 5);
        assertItem3(item3);

        Company1 c1 = em.find(Company1.class, 1);
        assertCompany1(c1);
        
        Company2 c2 = em.find(Company2.class, 3);
        assertCompany2(c2);

        Division d = em.find(Division.class, 1);
        assertDivision(d);
        
        VicePresident vp = em.find(VicePresident.class, 1);
        assertVicePresident(vp);
    }
    
    public void assertItem1(Item1 item) {
        int id = item.getId();
        Map images = item.getImages();
        assertEquals(numImagesPerItem, images.size());
    }
    
    public void assertItem2(Item2 item) {
        int id = item.getId();
        Map images = item.getImages();
        assertEquals(numImagesPerItem, images.size());
    }

    public void assertItem3(Item3 item) {
        int id = item.getId();
        Map images = item.getImages();
        assertEquals(numImagesPerItem, images.size());
    }

    public void assertCompany1(Company1 c) {
        int id = c.getId();
        Map organization = c.getOrganization();
        assertEquals(2,organization.size());
    }
    
    public void assertCompany2(Company2 c) {
        int id = c.getId();
        Map organization = c.getOrganization();
        assertEquals(2,organization.size());
    }    
    
    public void assertDivision(Division d) {
        int id = d.getId();
        String name = d.getName();
    }

    public void assertVicePresident(VicePresident vp) {
        int id = vp.getId();
        String name = vp.getName();
    }

    public void queryObjMapKeyClass() {
        queryItem(emf);
        queryCompany(emf);
        queryDivision(emf);
        queryVicePresident(emf);
    }
    
    public void findObjMapKeyEnumerated() {
        EntityManager em = emf.createEntityManager();
        Item4 item = em.find(Item4.class, 1);
        FileName4 fileName1 = item.getImage(Item4.Catagory.A1);
        assertEquals("file1", fileName1.getFName());       
        assertEquals("file1", fileName1.getLName());       
        
        FileName4 fileName2 = item.getImage(Item4.Catagory.A2);
        assertEquals("file2", fileName2.getFName());       
        assertEquals("file2", fileName2.getLName());       
        
        FileName4 fileName3 = item.getImage(Item4.Catagory.A3);
        assertEquals("file3", fileName3.getFName());       
        assertEquals("file3", fileName3.getLName());       
    }

    public void findObjMapKeyTemporal() {
        EntityManager em = emf.createEntityManager();
        Item5 item = em.find(Item5.class, 1);
        assertEquals(3, item.getImages().size());
    }
    
    public void queryItem(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q1 = em.createQuery("select i from Item1 i");
        List<Item1> is1 = q1.getResultList();
        for (Item1 item : is1){
            assertItem1(item);
        }
        
        Query q2 = em.createQuery("select i from Item2 i");
        List<Item2> is2 = q2.getResultList();
        for (Item2 item : is2){
            assertItem2(item);
        }

        Query q3 = em.createQuery("select i from Item3 i");
        List<Item3> is3 = q3.getResultList();
        for (Item3 item : is3){
            assertItem3(item);
        }

        tran.commit();

        String imageKey1 = (String) is1.get(0).getImages().
            keySet().toArray()[0];
        String imageKey2 = (String) is2.get(0).getImages().
            keySet().toArray()[0];
        String imageKey3 = (String) is3.get(0).getImages().
            keySet().toArray()[0];
        String[] query = {
            "select i from Item1 i" +
                " where ?1 = any " +
                " (select KEY(e) from Item1 i, in(i.images) e) " +
                " order by i",
            "select i from Item1 i" +
                " where exists " +
                " (select e from Item1 i, in(i.images) e" +
                "   where ?1 = KEY(e)) " +
                " order by i",
        };
        String[] query2 = {
            "select i from Item2 i" +
                " where ?1 = any " +
                " (select KEY(e) from Item2 i, in(i.images) e) " +
                " order by i",
            "select i from Item2 i" +
                " where exists " +
                " (select e from Item2 i, in(i.images) e" +
                "   where ?1 = KEY(e)) " +
                " order by i",
        };
        String[] query3 = {
            "select i from Item3 i" +
                " where ?1 = any " +
                " (select KEY(e) from Item3 i, in(i.images) e) " +
                " order by i",
            "select i from Item3 i" +
                " where exists " +
                " (select e from Item3 i, in(i.images) e" +
                "   where ?1 = KEY(e)) " +
                " order by i",
        };

        for (int i = 0; i < query.length; i++) {
            Query q = em.createQuery(query[i]);
            q.setParameter(1, imageKey1);
            is1 = q.getResultList();
            for (Item1 item : is1){
                assertItem1(item);
            }
        }
        for (int i = 0; i < query2.length; i++) {
            Query q = em.createQuery(query2[i]);
            q.setParameter(1, imageKey2);
            is2 = q.getResultList();
            for (Item2 item : is2){
                assertItem2(item);
            }
        }
        for (int i = 0; i < query3.length; i++) {
            Query q = em.createQuery(query3[i]);
            q.setParameter(1, imageKey3);
            is3 = q.getResultList();
            for (Item3 item : is3){
                assertItem3(item);
            }
        }
        em.close();
    }

    public void queryCompany(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q1 = em.createQuery("select c from Company1 c");
        List<Company1> cs1 = q1.getResultList();
        for (Company1 c : cs1){
            assertCompany1(c);
        }
        Query q2 = em.createQuery("select c from Company2 c");
        List<Company2> cs2 = q2.getResultList();
        for (Company2 c : cs2){
            assertCompany2(c);
        }
        tran.commit();

        em.clear();
        // test KEY(e) in subquery
        Division d1 = (Division) ((Company1) cs1.get(0)).getOrganization().
            keySet().toArray()[0];
        Division d2 = (Division) ((Company2) cs2.get(0)).getOrganization().
            keySet().toArray()[0];
        String[] query = {
            "select c from Company1 c, in(c.organization) d " +
                " where KEY(d) = ?1",
            "select c from Company1 c " +
                " where ?1 = " +
                " (select KEY(d) from Company1 c, in(c.organization) d" +
                "   where d.id = 1)" +
                " order by c ",  
            "select c from Company1 c where exists" +
                " (select d from in(c.organization) d" +
                "  where KEY(d) = ?1)" +
                " order by c ",  
            "select c from Company1 c where exists" +
                " (select d from c.organization d" +
                "  where KEY(d) = ?1)" +
                " order by c ",  
        };
        for (int i = 0; i < query.length; i++) {
            Query q = em.createQuery(query[i]);
            q.setParameter(1, d1);
            cs1 = q.getResultList();
            for (Company1 c : cs1){
                assertCompany1(c);
            }
        }

        String[] query2 = {
            "select c from Company2 c" +
                " where ?1 = " +
                " (select KEY(d) from Company2 c, in(c.organization) d" +
                "   where d.id = 3)" +
                " order by c ",  
            "select c from Company2 c where exists" +
                " (select d from in(c.organization) d" +
                "  where KEY(d) = ?1)" +
                " order by c ",  
            "select c from Company2 c where exists" +
                " (select d from c.organization d" +
                "  where KEY(d) = ?1)" +
                " order by c ",  
        };

        for (int i = 0; i < query2.length; i++) {
            Query q = em.createQuery(query2[i]);
            q.setParameter(1, d2);
            cs2 = q.getResultList();
            for (Company2 c : cs2){
                assertCompany2(c);
            }
        }
        em.close();
    }

    public void queryDivision(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select d from Division d");
        List<Division> ds = q.getResultList();
        for (Division d : ds){
            assertDivision(d);
        }
        tran.commit();
        em.close();
    }

    public void queryVicePresident(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        tran.begin();
        Query q = em.createQuery("select vp from VicePresident vp");
        List<VicePresident> vps = q.getResultList();
        for (VicePresident vp : vps){
            assertVicePresident(vp);
        }
        tran.commit();
        em.close();
    }        
    public void createEmbeddableContainingRelationWithGeneratedKey() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tran = em.getTransaction();
        
        Book b = new Book(1590596455);
        Seller bob = new Seller("Bob's books!");
        Seller jim = new Seller("Jim's books!");
        Seller mike = new Seller("Mikes's books!");
        b.addListing(new Listing(bob , 44.15));
        b.addListing(new Listing(jim , 34.15));
        b.addListing(new Listing(mike , 14.15));
        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();
        int id = b.getId();
        em.clear();
        Book b2 = em.find(Book.class, id);
        Set<Listing> listings = b2.getListings();
        for (Listing listing : listings) {
            Seller seller = listing.getSeller();
            assertNotNull(seller);
            assertTrue(seller.getId() != 0);
        }
        
    }
}
