package com.student.student_service.collection;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.student.student_service.enums.OtpTypeEmun;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Document(collation = "USER_OTP")
public class UserOTPs {

	@Transient
	public static final String SEQUENCE_NAME = "otpId";
	
	@Id
	private Long otpId;
	private Integer otp;
	private Long createdById;
	private Date createdTime;
	private Long sId;
	private OtpTypeEmun otpType;
}
