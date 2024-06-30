package com.student.student_service.serviceImpl;

import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.student.student_service.collection.UserOTPs;
import com.student.student_service.constant.ExceptionConstant;
import com.student.student_service.enums.OtpTypeEmun;
import com.student.student_service.helper.CounterDAO;
import com.student.student_service.repository.StudentRepository;
import com.student.student_service.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final StudentRepository studentRepository;
	private final CounterDAO sequence;

	@Override
	public Boolean generateOTP(Long sId) {

		boolean isStudentExists = studentRepository.existsById(sId);

		if (!isStudentExists) {
			throw new ResourceAccessException(ExceptionConstant.EXCEPTION_SRVC02_DESC + sId);
		}

		int otp = new Random().nextInt(999999);

		while (countDigit(otp) != 6) {
			otp = new Random().nextInt(999999);
		}

		UserOTPs userOTPs = new UserOTPs();

		userOTPs.setOtpId(sequence.getNextSequenceOfField(UserOTPs.SEQUENCE_NAME));
		userOTPs.setOtp(otp);
		userOTPs.setSId(sId);
		userOTPs.setCreatedTime(new Date());
		userOTPs.setCreatedById(sId);
		userOTPs.setOtpType(OtpTypeEmun.SHARE);

		return true;
	}

	private int countDigit(int otp) {

		int count = 0;
		while (otp != 0) {
			otp = otp / 10;
			++count;
		}
		return count;
	}

}
