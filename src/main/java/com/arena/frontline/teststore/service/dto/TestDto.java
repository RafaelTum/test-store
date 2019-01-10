package com.arena.frontline.teststore.service.dto;

import java.util.List;
import java.util.Objects;

public class TestDto {
    private String id;
    private String name;
    private List<QuestionDto> questions;

    public TestDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestDto)) return false;
        TestDto testDto = (TestDto) o;
        return Objects.equals(getId(), testDto.getId()) &&
                Objects.equals(getName(), testDto.getName()) &&
                Objects.equals(getQuestions(), testDto.getQuestions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getQuestions());
    }
}
