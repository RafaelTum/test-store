package com.arena.frontline.teststore.service.impl;

import com.arena.frontline.teststore.error.RestTemplateErrorHandler;
import com.arena.frontline.teststore.service.QuestionGenerationService;
import com.arena.frontline.teststore.service.dto.QuestionDto;
import com.arena.frontline.teststore.service.dto.QuestionGeneratorDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class QuestionGenerationServiceImpl implements QuestionGenerationService {

  private final String questionStoreApiUrl;
  private RestOperations restTemplate;

  public QuestionGenerationServiceImpl(
      @Value("${test-generation.server.url}") String questionStoreApiUrl,
      RestTemplateErrorHandler exceptionHandler) {
    this.questionStoreApiUrl = questionStoreApiUrl;
    restTemplate = new RestTemplate();
    ((RestTemplate) restTemplate).setErrorHandler(exceptionHandler);
  }

  @Override
  public List<QuestionDto> generate(QuestionGeneratorDto questionGeneratorDto) {
    HttpEntity<QuestionGeneratorDto> request = new HttpEntity<>(questionGeneratorDto);
    ResponseEntity<List<QuestionDto>> response = restTemplate
        .exchange(getQuestionStoreGenerationUrl(), HttpMethod.POST, request,
            new ParameterizedTypeReference<List<QuestionDto>>() {
            });
    return response.getBody();
  }

  private String getQuestionStoreGenerationUrl() {
    return questionStoreApiUrl + "/questiongenerator/generate";
  }
}
