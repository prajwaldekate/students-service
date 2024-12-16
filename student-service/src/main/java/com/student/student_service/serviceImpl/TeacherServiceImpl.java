package com.student.student_service.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.student.student_service.collection.Teacher;
import com.student.student_service.helper.CounterDAO;
import com.student.student_service.model.TeacherRequest;
import com.student.student_service.repository.TeacherRepository;
import com.student.student_service.service.TeacherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

	private final CounterDAO sequence;

	private final TeacherRepository teacherRepository;

	@Override
	public Teacher createTecherData(TeacherRequest teacherRequest) {

		Teacher teacher = new Teacher();

		teacher.setTeacherId(sequence.getNextSequenceOfField(Teacher.SEQUENCE_NAME));
		BeanUtils.copyProperties(teacherRequest, teacher);

		try {
			Teacher saveTeacher = teacherRepository.save(teacher);
			return saveTeacher;
		} catch (DataAccessException ex) {
			throw new RuntimeException("Error occurred while saving the teacher information", ex);
		}
		
	}

}
