package com.student.student_service.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.student.student_service.cache.CacheInitializer;
import com.student.student_service.collection.FileUpload;
import com.student.student_service.collection.Standard;
import com.student.student_service.collection.Student;
import com.student.student_service.helper.CounterDAO;
import com.student.student_service.model.StudentRequest;
import com.student.student_service.model.StudentResponse;
import com.student.student_service.repository.FileUploadRepository;
import com.student.student_service.repository.StandardRepository;
import com.student.student_service.repository.StudentRepository;
import com.student.student_service.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	
	private final StandardRepository standardRepository;
	
	private final CounterDAO sequence;
	
	private final FileUploadRepository fileUploadRepository;
	
	private static final String FILE_DIRECTORY = "C:/uploads/prajwal/";
	
	private final CacheInitializer cacheInitializer;


	@Override
	public Student createStudentInfo(StudentRequest studentRequest, MultipartFile file) {

		Student student = new Student();

		student.setStudentId(sequence.getNextSequenceOfField(Student.SEQUENCE_NAME));
		student.setAttachment(uploadFiles(file));
		mapStudentRequestToEntity(studentRequest, student);

		try {
			Student savedStudent = studentRepository.save(student);
			return savedStudent;
		} catch (DataAccessException ex) {
			throw new RuntimeException("Error occurred while saving the student information", ex);
		}
	}

	private String uploadFiles(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename();
			String filePath = FILE_DIRECTORY + fileName;
			File file = new File(filePath);

			file.getParentFile().mkdirs();
			multipartFile.transferTo(file);

			return filePath;
		} catch (IOException e) {
			throw new RuntimeException("Error occurred while saving the file", e);
		}
	}

	private void mapStudentRequestToEntity(StudentRequest studentRequest, Student student) {
		student.setFirstName(studentRequest.getFirstName());
		student.setLastName(studentRequest.getLastName());
		student.setMiddleName(studentRequest.getMiddleName());
		student.setMotherName(studentRequest.getMotherName());
		student.setEmail(studentRequest.getEmail());
		student.setMobileNo(studentRequest.getMobileNo());
		student.setAddress(studentRequest.getAddress());
		student.setCity(studentRequest.getCity());
		student.setState(studentRequest.getState());
		student.setCountry(studentRequest.getCountry());
		student.setStandard(studentRequest.getStandard());
	}

	@Override
	public Page<StudentResponse> getAllStudentInfo(int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("studentId").descending());

		Page<Student> studentRes = studentRepository.findAll(pageable);

		if (!studentRes.isEmpty()) {

			List<StudentResponse> list = studentRes.stream().map(student -> {

				StudentResponse studentResponse = new StudentResponse();
				studentResponse.setStudentId(student.getStudentId());
				studentResponse.setFirstName(student.getFirstName());
				studentResponse.setLastName(student.getLastName());
				studentResponse.setMiddleName(student.getMiddleName());
				studentResponse.setMotherName(student.getMotherName());
				studentResponse.setEmail(student.getEmail());
				studentResponse.setMobileNo(student.getMobileNo());
				studentResponse.setAddress(student.getAddress());
				studentResponse.setCity(student.getCity());
				studentResponse.setState(student.getState());
				studentResponse.setCountry(student.getCountry());

				return studentResponse;
			}).collect(Collectors.toList());

			return new PageImpl<>(list, pageable, studentRes.getTotalElements());
		} else {
			return Page.empty();
		}
	}

	@Override
	public StudentResponse findStudentById(Long studentId) {
		
//		Student student = studentRepository.findById(studentId)
//				.orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.EXCEPTION_SRV02, ExceptionConstant.STUDENT_NOT_FOUND + studentId));
		Student student = cacheInitializer.getStudentDetails(studentId);
		StudentResponse studentResponse = new StudentResponse();
		studentResponse.setStudentId(student.getStudentId());
		studentResponse.setFirstName(student.getFirstName());
		studentResponse.setLastName(student.getLastName());
		studentResponse.setMiddleName(student.getMiddleName());
		studentResponse.setMotherName(student.getMotherName());
		studentResponse.setEmail(student.getEmail());
		studentResponse.setMobileNo(student.getMobileNo());
		studentResponse.setAddress(student.getAddress());
		studentResponse.setCity(student.getCity());
		studentResponse.setState(student.getState());
		studentResponse.setCountry(student.getCountry());

		long std = student.getStandard();
		Optional<Standard> optionalStandard = standardRepository.findById(std);
		optionalStandard.ifPresent(standard -> studentResponse.setStandard(standard.getStandard()));

		return studentResponse;
	}

	@Override
	public String uploadFile(MultipartFile file) {
		try {
			FileUpload fileUpload = new FileUpload();
			fileUpload.setFileUploadId(sequence.getNextSequenceOfField(FileUpload.SEQUENCE_NAME));

			String fileName = file.getOriginalFilename();
			String filePath = FILE_DIRECTORY + fileName;
			File dest = new File(filePath);

			dest.getParentFile().mkdirs();
			file.transferTo(dest);

			fileUpload.setFileName(fileName);
			fileUpload.setFilePath(filePath);
			fileUploadRepository.save(fileUpload);

			return fileName;
		} catch (IOException e) {
			throw new RuntimeException("Error occurred while saving the file", e);
		}
	}


	@Override
	public ResponseEntity<byte[]> downloadFile(String fileName) throws IOException {
		// Construct file path
		String filePath = FILE_DIRECTORY + fileName;
		File file = new File(filePath);

		// Check if the file exists
		if (!file.exists()) {
			throw new FileNotFoundException("File not found: " + fileName);
		}

		try {
			// Load file content
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] fileContent = new byte[(int) file.length()];
			fileInputStream.read(fileContent);
			fileInputStream.close();

			// Set Content-Disposition header to prompt download
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			// Return the file content as a ResponseEntity
			return ResponseEntity.ok().headers(headers).body(fileContent);
		} catch (IOException e) {
			// Handle file reading errors
			throw new IOException("Error reading file: " + fileName, e);
		}
	}
}
