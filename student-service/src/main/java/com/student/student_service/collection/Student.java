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
@Document("STUDENT")
public class Student{

	@Transient
	public static final String SEQUENCE_NAME = "sId";
	
	@Id
	private Long studentId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String motherName;
	private String email;
	private String mobileNo;
	private String address;
	private String city;
	private String state;
	private String country;
	private long standard;
	private String attachment;
	
}
