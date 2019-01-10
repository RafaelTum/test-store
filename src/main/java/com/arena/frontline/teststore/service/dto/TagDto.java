package com.arena.frontline.teststore.service.dto;

import java.util.Objects;

public class TagDto {
    private String id;
    private String name;

    public TagDto() {
    }

    public TagDto(String id, String name) {
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
        if (!(o instanceof TagDto)) return false;
        TagDto tagDto = (TagDto) o;
        return Objects.equals(getId(), tagDto.getId()) &&
                Objects.equals(getName(), tagDto.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
