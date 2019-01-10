package com.arena.frontline.teststore.web.api.models;

import com.arena.frontline.teststore.commons.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionModel {
    private String id;
    private String name;
    private List<AnswerModel> answers = new ArrayList<>();
    private Integer durationInSeconds;
    private Severity severity;
    private CategoryModel category;
    private List<TagModel> tags = new ArrayList<>();

    public QuestionModel() {
    }

    public QuestionModel(String id, String name, List<AnswerModel> answers,
                         Integer durationInSeconds, Severity severity, CategoryModel category,
                         List<TagModel> tags) {
        this.id = id;
        this.name = name;
        this.answers = answers;
        this.durationInSeconds = durationInSeconds;
        this.severity = severity;
        this.category = category;
        this.tags = tags;
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

    public List<AnswerModel> getAnswers() {
        return answers;
    }

    public void setAnswers(
            List<AnswerModel> answers) {
        this.answers = answers;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(
            CategoryModel category) {
        this.category = category;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(
            List<TagModel> tags) {
        this.tags = tags;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionModel that = (QuestionModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(answers, that.answers) &&
                severity == that.severity &&
                Objects.equals(durationInSeconds, that.durationInSeconds) &&
                Objects.equals(category, that.category) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, answers, durationInSeconds, severity, category, tags);
    }
}
