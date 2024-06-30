package com.student.student_service.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Document("DOCUMENTS")
public class FileUpload {

	@Transient
	public static final String SEQUENCE_NAME = "fileUploadId";
	
	@Id
	private Long fileUploadId;
	private String fileName;
	private String filePath;
}
