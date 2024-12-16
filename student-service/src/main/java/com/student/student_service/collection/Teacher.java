package com.student.student_service.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.student.student_service.enums.Gender;

import lombok.Data;

@Data
@Document("TEACHERS")
public class Teacher {
	
	@Transient
	public static final String SEQUENCE_NAME = "teacherId";

	@Id
	private Long teacherId;
	private String firstName;
	private String lastName;
	private String subject;
	private String experience;
	private String mobileNo;
	private String city;
	private Gender gender;
}
