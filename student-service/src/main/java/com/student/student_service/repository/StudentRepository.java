package com.student.student_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.student.student_service.collection.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, Long> {

	public Page<Student> findAll(Pageable pageable);

}
