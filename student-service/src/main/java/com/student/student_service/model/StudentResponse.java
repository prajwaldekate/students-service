package com.student.student_service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StudentResponse {

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
	private String standard;
	private String attachment;

}
