package com.arena.frontline.teststore.service.unit;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arena.frontline.teststore.data.mongo.Category;
import com.arena.frontline.teststore.data.mongo.TestDocument;
import com.arena.frontline.teststore.service.CategoryService;
import com.arena.frontline.teststore.service.dto.CategoryDto;
import com.arena.frontline.teststore.service.impl.CategoryServiceImpl;

import com.arena.frontline.teststore.utils.Mapper;
import com.arena.frontline.teststore.utils.MapperImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CloseableIterator;

@RunWith(MockitoJUnitRunner.class)
public class CategeoryServiceImplTest {

  @Mock
  private MongoTemplate mongoTemplate;
  private Mapper mapper = new MapperImpl();
  @InjectMocks
  private CategoryService categeoryService = new CategoryServiceImpl(mongoTemplate, mapper);

  private Category oldCategory;
  private Category newCategory;

  private CategoryDto oldCategoryDto;
  private CategoryDto newCategoryDto;

  private TestDocument test1;
  private TestDocument test2;

  private List<TestDocument> tests;

  @Before
  public void setup() {
    oldCategory = new Category("categoryId", "MongoDB");
    newCategory = new Category("newCategoryId", "SQL");

    oldCategoryDto = new CategoryDto("categoryId", "MongoDB");
    newCategoryDto = new CategoryDto("newCategoryId", "SQL");

    test1 = new TestDocument();
    test2 = new TestDocument();

    tests = Arrays.asList(test1, test2);

  }

  @Test
  public void update() {
    Query query = new Query();
    query.addCriteria(Criteria.where("questions.category.id").is(oldCategory.getId()));

    when(mongoTemplate.stream(query, TestDocument.class)).thenReturn(
        new CloseableIterator<TestDocument>() {
          private int current = 0;
          @Override
          public void close() {

          }

          @Override
          public boolean hasNext() {
            return current<tests.size();
          }

          @Override
          public TestDocument next() {
            return tests.get(current++);
          }
        });

    categeoryService.update(oldCategoryDto, newCategoryDto);
    verify(mongoTemplate).stream(query, TestDocument.class);
  }

}
