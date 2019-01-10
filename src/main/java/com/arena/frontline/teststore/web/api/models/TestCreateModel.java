package com.arena.frontline.teststore.web.api.models;

import java.util.List;
import java.util.Objects;

public class TestCreateModel {
    private String name;
    private List<QuestionModel> questions;

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
        TestCreateModel testModel = (TestCreateModel) o;
        return  Objects.equals(getName(), testModel.getName()) &&
                Objects.equals(getQuestions(), testModel.getQuestions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getQuestions());
    }
}
