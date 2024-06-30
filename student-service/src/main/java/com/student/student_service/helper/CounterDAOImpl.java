package com.student.student_service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.student.student_service.collection.Counters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CounterDAOImpl implements CounterDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public long getNextSequenceOfField(String fieldName) {

		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(fieldName));
		
		try {
			Counters counters = mongoTemplate.findAndModify(query, new Update().inc("sequence", 1), FindAndModifyOptions.options().returnNew(true), Counters.class);
			
			if(counters != null) {
				return counters.getSequence();
			}else {
				return insertSequence(fieldName);
			}
		} catch (Exception e) {
			log.error("Sequence is not added {}", e.getMessage());
			return insertSequence(fieldName);
		}
	}

	private long insertSequence(String fieldName) {
		Counters counters = new Counters();
		counters.setName(fieldName);
		counters.setSequence(1000);
		return mongoTemplate.insert(counters).getSequence();
	}

}
