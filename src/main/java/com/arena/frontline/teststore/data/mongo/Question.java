package com.arena.frontline.teststore.data.mongo;

import com.arena.frontline.teststore.commons.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {

    private String id;
    private String name;
    private List<Answer> answers = new ArrayList<>();
    private Integer durationInSeconds;
    private Severity severity;
    private Category category;
    private List<Tag> tags = new ArrayList<>();

    public Question() {
    }

    public Question(String id, String name, List<Answer> answers, Integer durationInSeconds,
                    Severity severity, Category category, List<Tag> tags) {
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(
            List<Answer> answers) {
        this.answers = answers;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(
            Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(
            List<Tag> tags) {
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
        Question that = (Question) o;
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
