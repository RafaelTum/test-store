package com.arena.frontline.teststore.service.unit;

import com.arena.frontline.teststore.commons.Severity;
import com.arena.frontline.teststore.data.mongo.Category;
import com.arena.frontline.teststore.data.mongo.Question;
import com.arena.frontline.teststore.data.mongo.Tag;
import com.arena.frontline.teststore.data.mongo.TestDocument;
import com.arena.frontline.teststore.data.mongo.repository.TestRepository;
import com.arena.frontline.teststore.error.exception.notfound.TestNotFoundException;
import com.arena.frontline.teststore.service.QuestionGenerationService;
import com.arena.frontline.teststore.service.TestService;
import com.arena.frontline.teststore.service.dto.*;
import com.arena.frontline.teststore.service.impl.TestServiceImpl;
import com.arena.frontline.teststore.utils.Mapper;
import com.arena.frontline.teststore.utils.MapperImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestServiceImplTest {

    @Mock
    private TestRepository testRepository;
    @Mock
    QuestionGenerationService questionGenerationService;
    private Mapper mapper = new MapperImpl();
    @InjectMocks
    private TestService testService = new TestServiceImpl(testRepository, mapper, questionGenerationService);

    private String name = "JavaTest";
    private String id = "7b3c82652379e240672b8a44";

    @Test
    public void generateQuestionList() {
        int questionCount = 100;
        CategoryDto category = new CategoryDto("catId", "Java");
        TagDto tag = new TagDto("tagId", "Spring");
        QuestionGeneratorDto questionGeneratorDto = new QuestionGeneratorDto(Collections.singletonList(category.getId()),
                Collections.singletonList(tag.getId()), Collections.singletonList(Severity.EASY), questionCount);
        List<QuestionDto> questionDtos = new ArrayList<>();

        for (int i = 0; i < questionCount; i++) {
            QuestionDto question = createQuestion("name", category, tag, Severity.EASY);
            questionDtos.add(question);
        }
        when(questionGenerationService.generate(questionGeneratorDto)).thenReturn(questionDtos);

        List<QuestionDto> questionDtoList = testService.generate(questionGeneratorDto);
        verify(questionGenerationService).generate(questionGeneratorDto);

        questionDtos.forEach(questionDto -> {
            assertEquals("categoryId should be same ", category.getId(),
                    questionDto.getCategory().getId());
        });

    }

    @Test
    public void create() {
        TestDocument testDocument = new TestDocument();
        testDocument.setName(name);
        testDocument.setQuestions(new ArrayList<>());

        when(testRepository.save(testDocument)).thenAnswer((Answer) invocation -> {
            TestDocument doc = invocation.getArgument(0);
            doc.setId(id);
            return doc;
        });

        TestDto testDto = mapper.map(testDocument, TestDto.class);

        TestDto createdTest = testService.add(testDto);

        verify(testRepository).save(Mockito.any(TestDocument.class));

        Assert.assertEquals("id should be the same", id, createdTest.getId());
    }

    @Test
    public void getAll() {
        TestDocument testDocument = new TestDocument();
        testDocument.setId(id);
        testDocument.setName(name);
        testDocument.setQuestions(new ArrayList<>());
        List<TestDocument> testDocuments = Collections.singletonList(testDocument);

        when(testRepository.findAll()).thenReturn(testDocuments);
        List<TestDto> testDtos = testService.getAll();
        verify(testRepository).findAll();

        Assert.assertEquals("list size should be the same", testDocuments.size(),
                testDtos.size());
    }

    @Test
    public void generateAndSave() {
    }

    @Test
    public void getByExistingId() {
        TestDocument testDocument = new TestDocument();
        testDocument.setId(id);
        testDocument.setName(name);
        testDocument.setQuestions(new ArrayList<>());

        when(testRepository.findById(id)).thenReturn(Optional.of(testDocument));

        TestDto testDto = testService.getById(id);
        verify(testRepository).findById(id);
        Assert.assertEquals("name should be the same", name, testDto.getName());
        Assert.assertEquals("id should be the same", id, testDto.getId());
    }

    @Test(expected = TestNotFoundException.class)
    public void getByNonExistingIdMustThrowTestNotFoundException() {
        String testId = "NotExistingId";

        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        testService.getById(testId);

        verify(testRepository).findById(testId);
    }

    @Test
    public void delete() {
        testService.delete(id);
        verify(testRepository).deleteById(id);
    }

    @Test
    public void editWithExistingTestIdMustSuccess() {
        TestDocument testDocument = new TestDocument();
        testDocument.setId(id);
        testDocument.setName(name);
        testDocument.setQuestions(new ArrayList<>());

        String newName = "test";
        when(testRepository.findById(id)).thenReturn(Optional.of(testDocument));
        testDocument.setName(newName);
        when(testRepository.save(testDocument)).thenReturn(testDocument);
        testService.edit(id, newName);
        verify(testRepository).findById(id);
        verify(testRepository).save(testDocument);
    }

    @Test(expected = TestNotFoundException.class)
    public void updateWithNonExistingTestIdMustThrowTestNotFoundException() {
        String testId = "NotExistingId";
        String newName = "TestNewName";

        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        testService.edit(testId, newName);
    }

    @Test
    public void addQuestionsToTestWithExistingTestIdMustSuccess() {
        TestDocument testDocument = createTestDocument();

        when(testRepository.findById(id)).thenReturn(Optional.of(testDocument));

        QuestionDto questionToAdd = createQuestion("NewQuestion",
                new CategoryDto("catId", "Hibernate"), new TagDto("tagId", "ORM"),
                Severity.HARD);
        List<QuestionDto> questionsToAdd = new ArrayList<>(Collections.singletonList(questionToAdd));
        testService.addQuestions(id, questionsToAdd);
        verify(testRepository).findById(id);
        verify(testRepository).save(testDocument);
    }

    @Test(expected = TestNotFoundException.class)
    public void addQuestionsToTestWithNonExistingTestIdMustThrowTestNotFoundException() {
        String testId = "NotExistingId";

        QuestionDto questionToAdd = createQuestion("NewQuestion",
                new CategoryDto("catId", "Hibernate"), new TagDto("tagId", "ORM"),
                Severity.HARD);
        List<QuestionDto> questionsToAdd = new ArrayList<>(Collections.singletonList(questionToAdd));

        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        testService.addQuestions(testId, questionsToAdd);
    }

    @Test
    public void deleteQuestionsFromTestWithExistingTestIdMustSuccess() {
        TestDocument testDocument = createTestDocument();
        when(testRepository.findById(id)).thenReturn(Optional.of(testDocument));
        QuestionDto questionToDelete = createQuestion(name, new CategoryDto("catId", "Java"),
                new TagDto("tagId", "Spring"), Severity.HARD);
        List<QuestionDto> questionsToDelete = new ArrayList<>(Collections.singletonList(questionToDelete));
        testService.deleteQuestions(id, questionsToDelete);
        verify(testRepository).findById(id);
        verify(testRepository).save(testDocument);
    }

    @Test(expected = TestNotFoundException.class)
    public void deleteQuestionsFromTestWithNonExistingTestIdMustThrowTestNotFoundException() {
        String testId = "NotExistingId";

        QuestionDto questionToDelete = createQuestion("NewQuestion",
                new CategoryDto("catId", "Hibernate"), new TagDto("tagId", "ORM"),
                Severity.HARD);
        List<QuestionDto> questionsToDelete = new ArrayList<>(Collections.singletonList(questionToDelete));

        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        testService.deleteQuestions(testId, questionsToDelete);

    }

    private QuestionDto createQuestion(String name, CategoryDto category, TagDto tag, Severity severity) {
        QuestionDto question = new QuestionDto();
        question.setName(name);
        question.setCategory(category);
        question.setTags(Collections.singletonList(tag));
        question.setSeverity(severity);
        return question;
    }

    private Question createQuestionDoc(String name, Category category, Tag tag, Severity severity) {
        Question question = new Question();
        question.setName(name);
        question.setCategory(category);
        question.setTags(Collections.singletonList(tag));
        question.setSeverity(severity);
        return question;
    }

    private TestDocument createTestDocument(){
        TestDocument testDocument = new TestDocument();
        testDocument.setId(id);
        testDocument.setName(name);
        Category category = new Category("catId", "Java");
        Tag tag = new Tag("tagId", "Spring");
        Question questionDocument = createQuestionDoc(name, category, tag, Severity.HARD);
        testDocument.setQuestions(Collections.singletonList(questionDocument));
        return testDocument;
    }

}