package com.student.student_service.collection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "COUNTERS")
public class Counters {

	private String name;
	private long sequence;
}
