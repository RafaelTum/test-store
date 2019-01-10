package com.arena.frontline.teststore.web.api.models;

import com.arena.frontline.teststore.service.dto.QuestionDto;
import com.arena.frontline.teststore.service.dto.TestDto;

import java.util.List;
import java.util.Objects;

public class TestModel {
    private String id;
    private String name;
    private List<QuestionModel> questions;

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

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestModel)) return false;
        TestModel testModel = (TestModel) o;
        return Objects.equals(getId(), testModel.getId()) &&
                Objects.equals(getName(), testModel.getName()) &&
                Objects.equals(getQuestions(), testModel.getQuestions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getQuestions());
    }
}
