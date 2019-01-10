package com.arena.frontline.teststore.service.impl;

import com.arena.frontline.teststore.data.mongo.Question;
import com.arena.frontline.teststore.data.mongo.TestDocument;
import com.arena.frontline.teststore.data.mongo.repository.TestRepository;
import com.arena.frontline.teststore.error.exception.notfound.TestNotFoundException;
import com.arena.frontline.teststore.service.QuestionGenerationService;
import com.arena.frontline.teststore.service.TestService;
import com.arena.frontline.teststore.service.dto.QuestionDto;
import com.arena.frontline.teststore.service.dto.QuestionGeneratorDto;
import com.arena.frontline.teststore.service.dto.TestDto;
import com.arena.frontline.teststore.utils.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

  private TestRepository testRepository;
  private Mapper mapper;
  private QuestionGenerationService questionGenerationService;

  public TestServiceImpl(TestRepository testRepository, Mapper mapper,
      QuestionGenerationService questionGenerationService) {
    this.testRepository = testRepository;
    this.mapper = mapper;
    this.questionGenerationService = questionGenerationService;
  }

  @Override
  public List<QuestionDto> generate(QuestionGeneratorDto questionGeneratorDto) {
    return questionGenerationService.generate(questionGeneratorDto);
  }

  @Override
  public List<TestDto> getAll() {
    List<TestDto> testDtos = mapper.map(testRepository.findAll(), TestDto.class);
    return testDtos;
  }

  @Override
  public TestDto add(TestDto test) {
    TestDocument testDocument = mapper.map(test, TestDocument.class);
    testRepository.save(testDocument);

    TestDto savedTest = mapper.map(testDocument, TestDto.class);
    return savedTest;
  }

  @Override
  public TestDto generateAndSave(QuestionGeneratorDto questionGeneratorDto, String name) {
    TestDto testDto = new TestDto();
    testDto.setName(name);
    testDto.setQuestions(generate(questionGeneratorDto));
    return add(testDto);
  }

  @Override
  public TestDto getById(String id) {
    TestDocument testDocument = testRepository.findById(id)
        .orElseThrow(() -> new TestNotFoundException(id));
    return mapper.map(testDocument, TestDto.class);
  }

  @Override
  public void delete(String id) {
    testRepository.deleteById(id);
  }

  @Override
  public void edit(String id, String newName) {
    TestDocument testDocument = testRepository.findById(id)
        .orElseThrow(() -> new TestNotFoundException(id));
    testDocument.setName(newName);
    testRepository.save(testDocument);
  }

  @Override
  public void addQuestions(String id, List<QuestionDto> questionsToAdd) {
    List<Question> questionsToBeAdd = mapper.map(questionsToAdd, Question.class);
    TestDocument testDocument = testRepository.findById(id)
        .orElseThrow(() -> new TestNotFoundException(id));
    List<Question> list = new ArrayList<>(testDocument.getQuestions());
    questionsToBeAdd.forEach(questionDocument -> list.add(questionDocument));
    testDocument.setQuestions(list);
    testRepository.save(testDocument);
  }

  @Override
  public void deleteQuestions(String id, List<QuestionDto> questionsToDelete) {
    List<Question> questionDocuments = mapper.map(questionsToDelete, Question.class);
    TestDocument testDocument = testRepository.findById(id)
        .orElseThrow(() -> new TestNotFoundException(id));
    List<Question> listForDeletion = new ArrayList<>(testDocument.getQuestions());
    questionDocuments.forEach(questionDocument -> listForDeletion.remove(questionDocument));
    testDocument.setQuestions(listForDeletion);
    testRepository.save(testDocument);
  }
}
