package com.student.student_service.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("STANDARD")
public class Standard {

	@Id
	private Long stdId;
	private String std;
	
}
