package com.arena.frontline.teststore.service;

import com.arena.frontline.teststore.service.dto.QuestionDto;
import com.arena.frontline.teststore.service.dto.QuestionGeneratorDto;

import java.util.List;

public interface QuestionGenerationService {
    List<QuestionDto> generate(QuestionGeneratorDto questionGeneratorDto);
}
