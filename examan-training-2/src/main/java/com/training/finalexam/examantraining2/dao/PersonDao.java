package com.training.finalexam.examantraining2.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.criteria.CriteriaBuilder;

@Component
@Slf4j
public class PersonDao implements IPersonDao{

        @PersistenceContext
        private EntityManager em;
        @Autowired
        private OnlyAcademicPurposes oap;

        @Override
        public Optional<Pet> get(long id) {
            Pet student = em.createQuery("select st from Pet st where id = " + id, Pet.class).getSingleResult();

            Pet hibernatePet = em.find(Pet.class, id);
            log.info("---------------Print student from hibernate find implementation: " + hibernatePet.getEmail() + " ---------------");
            //return Optional.ofNullable(student1);
            return Optional.ofNullable(student);
        }

        @Override
        public List<Student> getAll() {
            log.info("---------------Printing studentList from JPQL query---------------");
            List<Student> studentList = em.createQuery("from Student order by email DESC", Student.class).getResultList();
            for (Student s : studentList) {
                log.info(s.getEmail());
                log.info("Print Student's courses if any");
                for (Course c : s.getCourses()) {
                    log.info(c.getBriefDescription());
                }
            }
            //printAllStudentsWithCriteriaQuery();
            return studentList;
        }

        private void printAllStudentsWithCriteriaQuery() {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Student> cq = cb.createQuery(Student.class);
            Root<Student> rootEntry = cq.from(Student.class);
            log.info("---------------Printing all students from Criteria query---------------");
            CriteriaQuery<Student> all = cq.select(rootEntry);
            TypedQuery<Student> allQuery = em.createQuery(all);
            for (Student s : allQuery.getResultList()) {
                log.info(s.getEmail());
            }
        }

        @Override
        @Transactional
        public int save(Object o) {
            Student s = (Student) o;
            Address address = em.find(Address.class, s.getAddress().getId());
            s.setAddress(address);
            ((Session) em.getDelegate()).saveOrUpdate(o);
            log.info("persisted object" + o);
            return 1;
        }

        @Override
        public void delete(Object o) {
            em.remove(o);
        }

        public List<Student> getAllStudentsFetchJoinCourses() {
            log.info("---------------Printing studentList from JPQL query---------------");
            List<Student> studentList = em.createQuery("select distinct s from Student s left join fetch s.courses", Student.class).getResultList();
            for (Student s : studentList) {
                log.info(s.getEmail());
                log.info("Print Student's courses");
                for (Course c : s.getCourses()) {
                    log.info("Book Description:" + c.getBriefDescription());
                }
            }
            return studentList;
        }

    }
