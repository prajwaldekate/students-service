package com.student.student_service.controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.student.student_service.collection.Student;
import com.student.student_service.constant.ExceptionConstant;
import com.student.student_service.model.ExceptionModel;
import com.student.student_service.model.StudentRequest;
import com.student.student_service.model.StudentResponse;
import com.student.student_service.service.StudentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("${base-uri.context}")
public class StudentController {
	
	private final StudentService studentService;

	@PostMapping(value = "/createStudentInfo", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> createStudentInfo(@RequestPart StudentRequest studentRequest, @RequestPart MultipartFile file){
		
		log.info("@StudentController @createStudentInfo start ::", studentRequest);

		ResponseEntity<?> responseEntity = null;
		Student responseModel = null;
		
		responseModel = studentService.createStudentInfo(studentRequest, file);
		
		if(responseModel != null) {
			responseEntity = new ResponseEntity<>(responseModel.getSId(), HttpStatus.CREATED);
		}else {
			responseEntity = new ResponseEntity<>(buildExceptionPayload(ExceptionConstant.EXCEPTION_SRVC01, ExceptionConstant.EXCEPTION_SRVC01_DESC), HttpStatus.BAD_REQUEST);
		}
		
		log.info("@StudentController @createStudentInfo end ::", studentRequest);
		return responseEntity;
	}

	
	@GetMapping(value = "/getAllStudentInfo")
	public ResponseEntity<?> getAllStudentInfo(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
	    log.info("@StudentController @getAllStudentInfo start ::");

	    ResponseEntity<?> responseEntity;
	    Page<StudentResponse> responseModel = studentService.getAllStudentInfo(page, size);

	    if (responseModel != null && !responseModel.isEmpty()) {
	        responseEntity = new ResponseEntity<>(responseModel, HttpStatus.OK);
	    } else {
	        responseEntity = new ResponseEntity<>(buildExceptionPayload(ExceptionConstant.EXCEPTION_SRVC01, ExceptionConstant.EXCEPTION_SRVC01_DESC), HttpStatus.NOT_FOUND);
	    }

	    log.info("@StudentController @getAllStudentInfo end ::");
	    return responseEntity;
	}
	
	
	@GetMapping(value = "/findBySId")
	public ResponseEntity<?> findById(@RequestParam Long sId) {
	    log.info("@StudentController @findBySId start :: {}", sId);

	    ResponseEntity<?> responseEntity;
	    StudentResponse responseModel = studentService.findBySId(sId);

	    if (responseModel != null) {
	        responseEntity = new ResponseEntity<>(responseModel, HttpStatus.OK);
	    } else {
	        responseEntity = new ResponseEntity<>(buildExceptionPayload(ExceptionConstant.EXCEPTION_SRVC01, ExceptionConstant.EXCEPTION_SRVC01_DESC), HttpStatus.NOT_FOUND);
	    }

	    log.info("@StudentController @findBySId end :: {}", sId);
	    return responseEntity;
	}

	
	@PostMapping(value = "/uploadFile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> uploadFile(@RequestPart MultipartFile file){
		
		log.info("@StudentController @uploadFile start ::", file);

		ResponseEntity<?> responseEntity = null;
		String responseModel = null;
		
		responseModel = studentService.uploadFile(file);
		
		if(responseModel != null) {
			responseEntity = new ResponseEntity<>(responseModel, HttpStatus.CREATED);
		}else {
			responseEntity = new ResponseEntity<>(buildExceptionPayload(ExceptionConstant.EXCEPTION_SRVC01, ExceptionConstant.EXCEPTION_SRVC01_DESC), HttpStatus.BAD_REQUEST);
		}
		
		log.info("@StudentController @uploadFile end ::", file);
		return responseEntity;
	}
	
	@GetMapping(value = "/downloadFile")
	public ResponseEntity<?> downloadFile(@RequestPart String fileName) throws IOException {
	    log.info("@StudentController @downloadFile start :: {}", fileName);

	    ResponseEntity<?> responseEntity;
	   ResponseEntity<byte[]> responseModel = studentService.downloadFile(fileName);

	    if (responseModel != null) {
	        responseEntity = new ResponseEntity<>(responseModel, HttpStatus.OK);
	    } else {
	        responseEntity = new ResponseEntity<>(buildExceptionPayload(ExceptionConstant.EXCEPTION_SRVC01, ExceptionConstant.EXCEPTION_SRVC01_DESC), HttpStatus.NOT_FOUND);
	    }

	    log.info("@StudentController @downloadFile end :: {}", fileName);
	    return responseEntity;
	}
	
	private ExceptionModel buildExceptionPayload(String errorCode, String errorDesc) {
		return new ExceptionModel(errorCode, errorDesc);
	}
	
}
