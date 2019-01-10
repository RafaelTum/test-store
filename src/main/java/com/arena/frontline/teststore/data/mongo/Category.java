package com.arena.frontline.teststore.data.mongo;

import java.util.Objects;

public class Category {
  private String id;
  private String name;

  public Category() {
  }

  public Category(String id, String name) {
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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category that = (Category) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name);
  }
}
