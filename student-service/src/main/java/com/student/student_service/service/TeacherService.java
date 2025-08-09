package com.student.student_service.service;

import com.student.student_service.collection.Teacher;
import com.student.student_service.model.TeacherRequest;

public interface TeacherService {

	public Teacher createTecherData(TeacherRequest teacherRequest);
}
