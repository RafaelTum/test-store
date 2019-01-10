package com.arena.frontline.teststore.service.impl;

import com.arena.frontline.teststore.data.mongo.Category;
import com.arena.frontline.teststore.data.mongo.TestDocument;
import com.arena.frontline.teststore.service.CategoryService;
import com.arena.frontline.teststore.service.dto.CategoryDto;
import com.arena.frontline.teststore.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CloseableIterator;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  private MongoTemplate mongoTemplate;
  private Mapper mapper;
  private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

  public CategoryServiceImpl(MongoTemplate mongoTemplate, Mapper mapper) {
    this.mongoTemplate = mongoTemplate;
    this.mapper = mapper;
  }

  @Override
  public void update(CategoryDto oldCategory, CategoryDto newCategory) {
    Query query = new Query();
    query.addCriteria(Criteria.where("questions.category.id").is(oldCategory.getId()));
    Category categoryToUpdate = mapper.map(newCategory, Category.class);
    try (CloseableIterator<TestDocument> tests = mongoTemplate.stream(query, TestDocument.class)) {
      tests.forEachRemaining(t -> {
        t.getQuestions().forEach(q -> {
          if (q.getCategory().getId().equals(oldCategory.getId())) {
            q.setCategory(categoryToUpdate);
          }
        });
        mongoTemplate.save(t);
      });
    }
  }

}
