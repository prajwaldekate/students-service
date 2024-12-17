package com.student.student_service.cache;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.student.student_service.collection.Student;
import com.student.student_service.constant.StudentConstant;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class CacheInitializer {

	private final CacheManager cacheManager;

	private final MongoTemplate mongoTemplate;

	@PostConstruct
	public void preloadCache() {

		// Fetch all students from MongoDB
		List<Student> studentList = mongoTemplate.findAll(Student.class);
		if (studentList == null || studentList.isEmpty()) {
			log.info("Student list is empty");
			return;
		}

		// Retrieve the cache
		Cache cache = cacheManager.getCache(StudentConstant.STUDENT_DATA_VALUE);
		if (cache == null) {
			log.warn("Cache not found: studentapp");
			return;
		}

		log.info("Initializing cache with student data...");

		// Populate the cache with student data
		for (Student student : studentList) {
			cache.put(student.getStudentId(), student);
			log.debug("Added student with ID {} to cache", student.getStudentId());
		}

		log.info("Cache initialization complete. Total students cached: {}", studentList.size());
	}

	@Cacheable(value = StudentConstant.STUDENT_DATA_VALUE, key = StudentConstant.STUDENT_DATA_KEY)
	public Student getStudentDetails(Long studentId) {

		try {
			// Fetch the student from the cache
			Student student = (Student) cacheManager.getCache(StudentConstant.STUDENT_DATA_VALUE).get(studentId).get();

			if (student == null) {
				log.info("Student details not found in cache.");
			} else {
				log.info("Student details retrieved from cache.");
			}

			return student;
		} catch (Exception e) {
			log.error("Error retrieving student details from cache", e);
			return null;
		}
	}
}
