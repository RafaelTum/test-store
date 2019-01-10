package com.arena.frontline.teststore.service.dto;

import com.arena.frontline.teststore.commons.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionGeneratorDto {
    private List<String> categoryIds = new ArrayList<>();
    private List<String> tagIds = new ArrayList<>();
    private List<Severity> severities = new ArrayList<>();
    private Integer count;

    public QuestionGeneratorDto() {
    }

    public QuestionGeneratorDto(List<String> categoryIds, List<String> tagIds, List<Severity> severities, Integer count) {
        this.categoryIds = categoryIds;
        this.tagIds = tagIds;
        this.severities = severities;
        this.count = count;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
    }

    public List<Severity> getSeverities() {
        return severities;
    }

    public void setSeverities(List<Severity> severities) {
        this.severities = severities;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionGeneratorDto)) return false;
        QuestionGeneratorDto that = (QuestionGeneratorDto) o;
        return Objects.equals(getCategoryIds(), that.getCategoryIds()) &&
                Objects.equals(getTagIds(), that.getTagIds()) &&
                Objects.equals(getSeverities(), that.getSeverities()) &&
                Objects.equals(getCount(), that.getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryIds(), getTagIds(), getSeverities(), getCount());
    }
}
