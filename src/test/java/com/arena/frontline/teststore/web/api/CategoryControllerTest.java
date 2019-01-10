package com.arena.frontline.teststore.web.api;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.arena.frontline.teststore.TestStoreApplication;
import com.arena.frontline.teststore.data.mongo.Category;
import com.arena.frontline.teststore.data.mongo.Question;
import com.arena.frontline.teststore.data.mongo.TestDocument;
import com.arena.frontline.teststore.data.mongo.repository.TestRepository;
import com.arena.frontline.teststore.web.api.models.CategoryModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestStoreApplication.class)
@WebAppConfiguration
public class CategoryControllerTest {

  protected String applicationJsonUtf8Value = MediaType.APPLICATION_JSON_UTF8_VALUE;

  @Autowired
  private WebApplicationContext context;
  @Autowired
  private TestRepository testRepository;

  private MockMvc mockMvc;
  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  private TestDocument test1;
  private TestDocument test2;
  private TestDocument test3;
  private String testName1 = "testName1";
  private String testName2 = "testName2";
  private String testName3 = "testName3";

  private Question question1;
  private Question question2;
  private Question question3;

  private Category category1;
  private Category category2;
  private Category category3;


  private String categoryId1 = "categoryId1";
  private String categoryName1 = "categoryName1";
  private String categoryId2 = "categoryId2";
  private String categoryName2 = "categoryName2";

  private String categoryId3 = "categoryId3";
  private String categoryName3 = "categoryName3";

  private int category1CountBeforeUpdate;
  private int updatedCategoriesCount;
  private int count;

  @Before
  public void setUp() throws Exception {
    mockMvc = webAppContextSetup(context).build();
    testRepository.deleteAll();

    category1 = new Category(categoryId1, categoryName1);
    category2 = new Category(categoryId2, categoryName2);
    category3 = new Category(categoryId3, categoryName3);

    question1 = new Question();
    question1.setCategory(category1);

    question2 = new Question();
    question2.setCategory(category1);

    question3 = new Question();
    question3.setCategory(category3);


    test1 = new TestDocument();
    test1.setName(testName1);
    test1.setQuestions(Arrays.asList(question3, question2));

    test2 = new TestDocument();
    test2.setName(testName2);
    test2.setQuestions(Arrays.asList(question3, question2));

    test3 = new TestDocument();
    test3.setName(testName3);
    test3.setQuestions(Arrays.asList(question3, question1, question2));

    testRepository.saveAll(Arrays.asList(test1, test2, test3));
  }

  @Test
  public void update() throws Exception {

    category1CountBeforeUpdate = getCategoryCountInDB(testRepository.findAll(), category1);

    CategoryModel categoryToUpdate = new CategoryModel(categoryId2, categoryName2);
    mockMvc.perform(put("/tests/categories/{id}", categoryId1)
        .contentType(applicationJsonUtf8Value)
        .content(json(categoryToUpdate)))
        .andExpect(status().isOk())
        .andDo(print());

    int category1CountAfterUpdate = getCategoryCountInDB(testRepository.findAll(), category1);


    Assert.assertEquals("category1 count after update should be 0",0, category1CountAfterUpdate);


    updatedCategoriesCount = getCategoryCountInDB(testRepository.findAll(), category2);
    Assert.assertEquals("Updated Categories count should be the same", category1CountBeforeUpdate,
        updatedCategoriesCount);
  }


  private int getCategoryCountInDB(List<TestDocument> allTestsFromDB, Category category) {
    count = 0;
    allTestsFromDB.forEach(test -> {
          test.getQuestions().forEach(question -> {
            if (question.getCategory().equals(category)) {
              count++;
            }
          });
        }
    );
    return count;
  }

  String json(Object o) throws IOException {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();

    this.mappingJackson2HttpMessageConverter
        .write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);

    return mockHttpOutputMessage.getBodyAsString();
  }

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {
    this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
        .findAny()
        .orElse(null);

    assertNotNull("the JSON message converter must not be null",
        this.mappingJackson2HttpMessageConverter);
  }


}
