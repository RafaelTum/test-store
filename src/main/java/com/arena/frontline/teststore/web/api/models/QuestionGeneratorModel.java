package com.arena.frontline.teststore.web.api.models;

import com.arena.frontline.teststore.commons.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionGeneratorModel {
    private String name;
    private List<String> categoryIds = new ArrayList<>();
    private List<String> tagIds = new ArrayList<>();
    private List<Severity> severities = new ArrayList<>();
    private Integer count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof QuestionGeneratorModel)) return false;
        QuestionGeneratorModel that = (QuestionGeneratorModel) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getCategoryIds(), that.getCategoryIds()) &&
                Objects.equals(getTagIds(), that.getTagIds()) &&
                Objects.equals(getSeverities(), that.getSeverities()) &&
                Objects.equals(getCount(), that.getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCategoryIds(), getTagIds(), getSeverities(), getCount());
    }
}
