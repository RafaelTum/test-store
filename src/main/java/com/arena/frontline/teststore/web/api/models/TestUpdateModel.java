package com.arena.frontline.teststore.web.api.models;

import java.util.Objects;

public class TestUpdateModel {
    public TestUpdateModel() {
    }

    public TestUpdateModel(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestUpdateModel)) return false;
        TestUpdateModel that = (TestUpdateModel) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
