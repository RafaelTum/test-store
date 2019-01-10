package com.arena.frontline.teststore.service;

import com.arena.frontline.teststore.service.dto.CategoryDto;

public interface CategoryService {

  void update(CategoryDto oldCategory, CategoryDto newCategory);

}
