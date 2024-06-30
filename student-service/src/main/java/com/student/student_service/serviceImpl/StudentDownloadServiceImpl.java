package com.student.student_service.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.student.student_service.collection.Student;
import com.student.student_service.helper.FileProcessingHeaderList;
import com.student.student_service.repository.StudentRepository;
import com.student.student_service.service.StudentDownloadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentDownloadServiceImpl implements StudentDownloadService{

	private final StudentRepository studentRepository;
	
	private final FileProcessingHeaderList fileProcessingHeaderList;
	
	@Override
	public ByteArrayInputStream getActualData() throws IOException {

		List<Student> list = studentRepository.findAll();
		System.out.println(list);

		ByteArrayInputStream byteArrayInputStream = fileProcessingHeaderList.dataToExcel(list);
		
		return byteArrayInputStream;
	}
}
