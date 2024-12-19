package com.student.student_service.cache;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.student.student_service.collection.Student;
import com.student.student_service.constant.ExceptionConstant;
import com.student.student_service.constant.StudentConstant;
import com.student.student_service.exception.ResourceNotFoundException;

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
	    
		//This method use for print the cache in cansole.(not other used)
		printCacheContents(cache);
		log.info("Cache initialization complete. Total students cached: {}", studentList.size());
	}

	// Method to print the cache contents
	private void printCacheContents(Cache cache) {
	    if (cache instanceof org.springframework.cache.concurrent.ConcurrentMapCache) {
	        org.springframework.cache.concurrent.ConcurrentMapCache mapCache = 
	            (org.springframework.cache.concurrent.ConcurrentMapCache) cache;
	        mapCache.getNativeCache().forEach((key, value) -> {
	            System.out.println("Cache Entry - Key: " + key + ", Value: " + value);
	        });
	    } else {
	        // For other types of caches, log a generic message or cast accordingly
	        System.out.println("Cache contents cannot be printed as the cache type is not a ConcurrentMapCache.");
	    }
	}
	
	
	
	
	@Cacheable(value = StudentConstant.STUDENT_DATA_VALUE, key = StudentConstant.STUDENT_DATA_KEY)
	public Student getStudentDetails(Long studentId) {

		try {
			// Fetch the student from the cache
			Student student = (Student) cacheManager.getCache(StudentConstant.STUDENT_DATA_VALUE).get(studentId).get();

			if (student == null) {
				log.info("Student details not found in cache.");
				throw new ResourceNotFoundException(ExceptionConstant.EXCEPTION_SRV02, "Student details not found in cache.");
			} else {
				log.info("Student details retrieved from cache.");
				throw new ResourceNotFoundException(ExceptionConstant.EXCEPTION_SRV02, "Student details retrieved from cache.");
			}

		} catch (Exception e) {
			log.error("Error retrieving student details from cache", e);
			throw new ResourceNotFoundException(ExceptionConstant.EXCEPTION_SRV02, "Error retrieving student details from cache");
		}
	}
	
	
	@CacheEvict(value = StudentConstant.STUDENT_DATA_VALUE, allEntries = true)
	public void clearAllCache() {
		System.out.println("************All Cache Removed************");
	}
}
