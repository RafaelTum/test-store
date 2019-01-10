package com.arena.frontline.teststore.service.unit;

import com.arena.frontline.teststore.commons.Severity;
import com.arena.frontline.teststore.error.ErrorDetails;
import com.arena.frontline.teststore.error.NotFoundErrorDetails;
import com.arena.frontline.teststore.error.RestTemplateErrorHandler;
import com.arena.frontline.teststore.error.exception.FrontLineApiErrorDetailsException;
import com.arena.frontline.teststore.service.QuestionGenerationService;
import com.arena.frontline.teststore.service.dto.CategoryDto;
import com.arena.frontline.teststore.service.dto.QuestionDto;
import com.arena.frontline.teststore.service.dto.QuestionGeneratorDto;
import com.arena.frontline.teststore.service.dto.TagDto;
import com.arena.frontline.teststore.service.impl.QuestionGenerationServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuestionGenerationServiceImplTest {

    @Mock
    private RestOperations restTemplate = Mockito.mock(RestTemplate.class);
    @Mock
    RestTemplateErrorHandler errorHandler = Mockito.mock(RestTemplateErrorHandler.class);
    @InjectMocks
    private QuestionGenerationService questionGenerationService = new QuestionGenerationServiceImpl(
            "http://localhost:8080", errorHandler);

    @Test
    public void shouldGetQuestionsFromApi() {
        String url = "http://localhost:8080/questiongenerator/generate";
        CategoryDto category = new CategoryDto("catId", "Java");
        TagDto tag = new TagDto("tagId", "Spring");
        int questionCount = 1;
        QuestionGeneratorDto questionGeneratorDto = new QuestionGeneratorDto(Collections.singletonList(category.getId()),
                Collections.singletonList(tag.getId()), Collections.singletonList(Severity.EASY), questionCount);
        HttpEntity<QuestionGeneratorDto> request = new HttpEntity<>(questionGeneratorDto);

        List<QuestionDto> questionDtoList = new ArrayList<>();
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId("123");
        questionDtoList.add(questionDto);

        ResponseEntity response = mock(ResponseEntity.class);

        when(restTemplate.exchange(ArgumentMatchers.eq(url), ArgumentMatchers.eq(HttpMethod.POST),
                ArgumentMatchers.eq(request), ArgumentMatchers.eq(new ParameterizedTypeReference<List<QuestionDto>>() {
                }))).thenReturn(response);
        when(response.getBody()).thenReturn(questionDtoList);

        List<QuestionDto> requestedList = questionGenerationService.generate(questionGeneratorDto);


        verify(restTemplate).exchange(ArgumentMatchers.eq(url), ArgumentMatchers.eq(HttpMethod.POST),
                ArgumentMatchers.eq(request), ArgumentMatchers.eq(new ParameterizedTypeReference<List<QuestionDto>>() {
                }));
        Assert.assertEquals("the count should be the same", questionCount, requestedList.size());
        Assert.assertEquals("the id should be the 123", "123", requestedList.get(0).getId());

    }
}
