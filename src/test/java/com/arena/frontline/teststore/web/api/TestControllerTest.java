package com.arena.frontline.teststore.web.api;

import com.arena.frontline.teststore.TestStoreApplication;
import com.arena.frontline.teststore.commons.Severity;
import com.arena.frontline.teststore.data.mongo.*;
import com.arena.frontline.teststore.data.mongo.repository.TestRepository;
import com.arena.frontline.teststore.error.ErrorDetails;
import com.arena.frontline.teststore.error.NotFoundErrorDetails;
import com.arena.frontline.teststore.service.dto.CategoryDto;
import com.arena.frontline.teststore.service.dto.QuestionDto;
import com.arena.frontline.teststore.service.dto.QuestionGeneratorDto;
import com.arena.frontline.teststore.service.dto.TagDto;
import com.arena.frontline.teststore.web.api.models.*;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestStoreApplication.class)
@WebAppConfiguration
public class TestControllerTest {

    private String applicationJsonUtf8Value = MediaType.APPLICATION_JSON_UTF8_VALUE;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TestRepository testRepository;

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private TestDocument testDocument;
    int questionsCount = 20;
    String testName = "Test";
    String categoryId = "catId";
    String tagId = "tagId";
    ArrayList<QuestionDto> generatedQuestions = createQuestionDtoList();


    @ClassRule
    public static final WireMockRule wireMockRule = new WireMockRule(8080);

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(context).build();

        testRepository.deleteAll();
        testDocument = new TestDocument();
        Question question = new Question("id", "QuestionName",
                Collections.singletonList(new Answer()), 2, Severity.HARD,
                new Category(categoryId, "Java"),
                Collections.singletonList(new Tag(tagId, "tag")));
        testDocument.setName(testName);
        testDocument.setQuestions(Collections.singletonList(question));
        testRepository.save(testDocument);

        QuestionGeneratorDto questionGeneratorDto = new QuestionGeneratorDto();
        questionGeneratorDto.setCount(questionsCount);
        questionGeneratorDto.setCategoryIds(Collections.singletonList(categoryId));
        questionGeneratorDto.setTagIds(Collections.singletonList(tagId));
        questionGeneratorDto.setSeverities(Collections.singletonList(Severity.MEDIUM));

        WireMock.stubFor(WireMock.post(urlPathEqualTo("/questiongenerator/generate"))
                .withRequestBody(containing(json(questionGeneratorDto)))
                .willReturn(new ResponseDefinitionBuilder()
                        .withBody(Json.write(generatedQuestions))
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader("Content-Type", applicationJsonUtf8Value)));
    }

    @Test
    public void getAllTests() throws Exception {

        mockMvc.perform(get("/tests"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(applicationJsonUtf8Value))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testDocument.getName())));
    }

    @Test
    public void addTest() throws Exception {
        String testNameToCreate = "Test1";
        TestCreateModel testModel = new TestCreateModel();
        testModel.setName(testNameToCreate);
        testModel.setQuestions(Collections.singletonList(new QuestionModel()));
//need to clone test
        this.mockMvc.perform(post("/tests/add")
                .contentType(applicationJsonUtf8Value)
                .content(json(testModel)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(applicationJsonUtf8Value))
                .andExpect(jsonPath("$.name", is(testNameToCreate)))
                .andExpect(jsonPath("$.questions", hasSize(1)))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void shouldCreateTestWithTheGivenCountOfQuestions() throws Exception {
        int expectedCount = questionsCount;
        QuestionGeneratorModel questionGeneratorModel = new QuestionGeneratorModel();
        questionGeneratorModel.setName(testName);
        questionGeneratorModel.setCount(expectedCount);
        questionGeneratorModel.setCategoryIds(Collections.singletonList(categoryId));
        questionGeneratorModel.setTagIds(Collections.singletonList(tagId));
        questionGeneratorModel.setSeverities(Collections.singletonList(Severity.MEDIUM));

        mockMvc.perform(post("/tests")
                .contentType(applicationJsonUtf8Value)
                .content(json(questionGeneratorModel)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(applicationJsonUtf8Value))
                .andExpect(jsonPath("$.name", is(testName)))
                .andExpect(jsonPath("$.questions", hasSize(expectedCount)))
                .andExpect(jsonPath("$.id", notNullValue()));

    }

    @Test
    public void shouldReturnNotFoundWhenTheExpectedCountByGivenFilterIsGreaterThenExistingConformingQuestions() throws Exception {
        QuestionGeneratorDto questionGeneratorDto = new QuestionGeneratorDto();
        questionGeneratorDto.setCount(questionsCount + 1);
        questionGeneratorDto.setCategoryIds(Collections.singletonList(categoryId));
        questionGeneratorDto.setTagIds(Collections.singletonList(tagId));
        questionGeneratorDto.setSeverities(Collections.singletonList(Severity.MEDIUM));

        ErrorDetails errorDetails = new NotFoundErrorDetails();
        errorDetails.setHttpStatus(404);
        errorDetails.setStatusName("NOT_FOUND");
        errorDetails.setException("Questions have not enough count: requestedCount = 21");
        errorDetails.setPath("uri=/questiongenerator/generate");

        WireMock.stubFor(WireMock.post(urlPathEqualTo("/questiongenerator/generate"))
                .withRequestBody(containing(json(questionGeneratorDto)))
                .willReturn(new ResponseDefinitionBuilder()
                        .withBody(Json.write(errorDetails))
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", applicationJsonUtf8Value)));

        int expectedCount = questionsCount + 1;
        QuestionGeneratorModel questionGeneratorModel = new QuestionGeneratorModel();
        questionGeneratorModel.setName(testName);
        questionGeneratorModel.setCount(expectedCount);
        questionGeneratorModel.setCategoryIds(Collections.singletonList(categoryId));
        questionGeneratorModel.setTagIds(Collections.singletonList(tagId));
        questionGeneratorModel.setSeverities(Collections.singletonList(Severity.MEDIUM));

        mockMvc.perform(post("/tests")
                .contentType(applicationJsonUtf8Value)
                .content(json(questionGeneratorModel)))
                .andExpect(content().contentType(applicationJsonUtf8Value))
                .andDo(print())
                .andExpect(jsonPath("$.httpStatus", is(errorDetails.getHttpStatus())))
                .andExpect(jsonPath("$.statusName", is(errorDetails.getStatusName())))
                .andExpect(jsonPath("$.exception", is(errorDetails.getException())))
                .andExpect(jsonPath("$.path", is(errorDetails.getPath())))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldEditTheNameByGivenIdAndNewName() throws Exception {
        TestUpdateModel testUpdateModel = new TestUpdateModel("nameAfterUpdate");

        mockMvc.perform(put("/tests/{id}", testDocument.getId())
                .contentType(applicationJsonUtf8Value)
                .content(json(testUpdateModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddQuestionsToTestById() throws Exception {
        QuestionModel questionModel = createQuestionModel();

        mockMvc.perform(post("/tests/{id}/questions", testDocument.getId())
                .contentType(applicationJsonUtf8Value)
                .content(json(Collections.singletonList(questionModel))))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindTestByExistingId() throws Exception {
        mockMvc.perform(get("/tests/{id}", testDocument.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(applicationJsonUtf8Value))
                .andExpect(jsonPath("$.id", is(testDocument.getId())))
                .andExpect(jsonPath("$.name", is(testDocument.getName())));

    }

    @Test
    public void shouldDeleteSelectedQuestionsFromTest() throws Exception {
        QuestionModel questionModel = createQuestionModel();

        mockMvc.perform(delete("/tests/{id}/questions", testDocument.getId())
                .contentType(applicationJsonUtf8Value)
                .content(json(Collections.singletonList(questionModel))))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteTestByGivenId() throws Exception {

        mockMvc.perform(delete("/tests/{id}", testDocument.getId()))
                .andExpect(status().isOk());
        Assert.assertFalse("deleted test must not exist",
                testRepository.findById(testDocument.getId()).isPresent());
    }

    private String json(Object o) throws IOException {
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

    private ArrayList<QuestionDto> createQuestionDtoList() {
        ArrayList<QuestionDto> generatedQuestions = new ArrayList<>();

        for (int i = 0; i < questionsCount; i++) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setName("question " + i);
            questionDto.setCategory(new CategoryDto());
            questionDto.setTags(Collections.singletonList(new TagDto()));
            questionDto.setSeverity(Severity.HARD);
            generatedQuestions.add(questionDto);
        }
        return generatedQuestions;
    }

    private QuestionModel createQuestionModel() {
        QuestionModel questionModel = new QuestionModel("id", "QuestionName",
                Collections.singletonList(new AnswerModel()), 2, Severity.HARD,
                new CategoryModel(categoryId, "Java"),
                Collections.singletonList(new TagModel(tagId, "tag")));
        return questionModel;
    }
}