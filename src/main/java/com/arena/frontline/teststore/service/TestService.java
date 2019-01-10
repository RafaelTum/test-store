package com.arena.frontline.teststore.service;

import com.arena.frontline.teststore.service.dto.QuestionDto;
import com.arena.frontline.teststore.service.dto.QuestionGeneratorDto;
import com.arena.frontline.teststore.service.dto.TestDto;

import java.util.List;

public interface TestService {
    List<QuestionDto> generate(QuestionGeneratorDto questionGeneratorDto);

    TestDto add(TestDto test);

    TestDto generateAndSave(QuestionGeneratorDto questionGeneratorDto, String name);

    List<TestDto> getAll();

    TestDto getById(String id);

    void delete(String id);

    void edit(String id, String newName);

    void addQuestions(String id, List<QuestionDto> questionsToAdd);

    void deleteQuestions(String id, List<QuestionDto> questionsToDelete);
}
