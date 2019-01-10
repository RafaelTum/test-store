package com.arena.frontline.teststore.data.mongo.repository;

import com.arena.frontline.teststore.data.mongo.TestDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<TestDocument, String> {
}
