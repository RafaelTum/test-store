package com.arena.frontline.teststore.data.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "test")
public class TestDocument {
    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("questions")
    private List<Question> questions = new ArrayList<>();

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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestDocument)) return false;
        TestDocument that = (TestDocument) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getQuestions(), that.getQuestions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getQuestions());
    }
}
