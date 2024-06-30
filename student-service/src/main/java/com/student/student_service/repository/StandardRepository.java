package com.student.student_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.student.student_service.collection.Standard;

@Repository
public interface StandardRepository extends MongoRepository<Standard, Long> {

}
