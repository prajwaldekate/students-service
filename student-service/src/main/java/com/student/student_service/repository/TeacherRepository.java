package com.student.student_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.student.student_service.collection.Teacher;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, Long> {

}
