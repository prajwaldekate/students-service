package com.student.student_service.repository;

import org.springframework.stereotype.Repository;

import com.student.student_service.collection.Teacher;

@Repository
public interface TeacherRepository {

	Teacher save(Teacher teacher);

}
