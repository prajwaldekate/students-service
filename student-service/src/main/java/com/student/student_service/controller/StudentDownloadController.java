package com.student.student_service.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.student_service.service.StudentDownloadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${base-uri.context}")
public class StudentDownloadController {

	private final StudentDownloadService studentDownloadService;
	
	 @GetMapping("/downloadData")
	    public ResponseEntity<Resource> downloadData() {
	        try {
	            String fileName = "student.xlsx";
	            
	            ByteArrayInputStream actualData = studentDownloadService.getActualData();
	            InputStreamResource file = new InputStreamResource(actualData);
	            
	            return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
	                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	                    .body(file);
	        } catch (IOException e) {
	            // Handle IOException appropriately
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }
}
