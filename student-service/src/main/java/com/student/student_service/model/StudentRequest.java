package com.student.student_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentRequest {

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
