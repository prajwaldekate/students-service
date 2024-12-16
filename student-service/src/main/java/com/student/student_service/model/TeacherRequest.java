package com.student.student_service.model;

import com.student.student_service.enums.Gender;

import lombok.Data;

@Data
public class TeacherRequest {

	private String firstName;
	private String lastName;
	private String subject;
	private String experience;
	private String mobileNo;
	private String city;
	private Gender gender;
}
