package com.student.student_service.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.student.student_service.collection.Student;
import com.student.student_service.model.StudentRequest;
import com.student.student_service.model.StudentResponse;

public interface StudentService {

	public Student createStudentInfo(StudentRequest studentRequest, MultipartFile file);
	
	public Page<StudentResponse> getAllStudentInfo(int page, int size);
	
	public StudentResponse findBySId(Long sId);
	
	public String uploadFile(MultipartFile file);
	
	public ResponseEntity<byte[]> downloadFile(String fileName) throws IOException;
	
}
