package com.arena.frontline.teststore.web.api;

import com.arena.frontline.teststore.service.TestService;
import com.arena.frontline.teststore.service.dto.QuestionDto;
import com.arena.frontline.teststore.service.dto.QuestionGeneratorDto;
import com.arena.frontline.teststore.service.dto.TestDto;
import com.arena.frontline.teststore.utils.Mapper;
import com.arena.frontline.teststore.web.api.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/tests")
@CrossOrigin
public class TestController {
    private TestService testService;
    private Mapper mapper;

    public TestController(TestService testService, Mapper mapper) {
        this.testService = testService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<TestModel>> get() {
        List<TestModel> tests = mapper.map(testService.getAll(), TestModel.class);
        return ResponseEntity.ok().body(tests);
    }

    @PostMapping("/add")
    public ResponseEntity<TestModel> addTest(@RequestBody TestCreateModel test) {
        TestDto testDto = mapper.map(test, TestDto.class);
        TestDto savedTestDto = testService.add(testDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTestDto.getId())
                .toUri();

        TestModel savedTest = mapper.map(savedTestDto, TestModel.class);
        return ResponseEntity.created(location).body(savedTest);
    }

    @PostMapping
    public ResponseEntity<TestModel> generateTest(@RequestBody QuestionGeneratorModel questionGeneratorModel){
        String name = questionGeneratorModel.getName();
        QuestionGeneratorDto questionGeneratorDto = mapper.map(questionGeneratorModel, QuestionGeneratorDto.class);
        TestDto generatedTest = testService.generateAndSave(questionGeneratorDto, name);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(generatedTest.getId())
                .toUri();
        TestModel testModel = mapper.map(generatedTest,TestModel.class);
        return ResponseEntity.created(location).body(testModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@RequestBody TestUpdateModel testUpdateModel, @PathVariable("id") String id){
        testService.edit(id, testUpdateModel.getName());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{id}/questions")
    public ResponseEntity addQuestionsToTest(@PathVariable("id") String id,
                                             @RequestBody List<QuestionModel> questionModelList ){
        List<QuestionDto> questionsToBeAdd = mapper.map(questionModelList, QuestionDto.class);
        testService.addQuestions(id, questionsToBeAdd);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/questions")
    public ResponseEntity deleteQuestionsFromTest(@PathVariable("id") String id,
                                             @RequestBody List<QuestionModel> questionModelList ){
        List<QuestionDto> questionsToBeDeleted = mapper.map(questionModelList, QuestionDto.class);
        testService.deleteQuestions(id, questionsToBeDeleted);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        testService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public TestModel get(@PathVariable("id") String id){
        TestModel test = mapper.map(testService.getById(id), TestModel.class);
        return test;
    }

}
