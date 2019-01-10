package com.arena.frontline.teststore.service.dto;

import com.arena.frontline.teststore.commons.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionDto {
    private String id;
    private String name;
    private List<AnswerDto> answers = new ArrayList<>();
    private Integer durationInSeconds;
    private Severity severity;
    private CategoryDto category;
    private List<TagDto> tags = new ArrayList<>();

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

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(
            List<AnswerDto> answers) {
        this.answers = answers;
    }

    public Integer getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(
            CategoryDto category) {
        this.category = category;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(
            List<TagDto> tags) {
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
        QuestionDto that = (QuestionDto) o;
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
