package com.arena.frontline.teststore.web.api.models;

import java.util.Objects;

public class TagModel {
    private String id;
    private String name;

    public TagModel() {
    }

    public TagModel(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagModel)) return false;
        TagModel tagModel = (TagModel) o;
        return Objects.equals(getId(), tagModel.getId()) &&
                Objects.equals(getName(), tagModel.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
