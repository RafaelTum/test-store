package com.arena.frontline.teststore.web.api;

import com.arena.frontline.teststore.service.CategoryService;
import com.arena.frontline.teststore.service.dto.CategoryDto;
import com.arena.frontline.teststore.utils.Mapper;
import com.arena.frontline.teststore.web.api.models.CategoryModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/tests/categories/")
@CrossOrigin
public class CategoryController {

  private CategoryService categoryService;
  private Mapper mapper;

  public CategoryController(CategoryService categoryService, Mapper mapper) {
    this.categoryService = categoryService;
    this.mapper = mapper;
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Update Category", tags = {"Category"})
  public void update(@RequestBody CategoryModel category, @PathVariable("id") String id) {

    CategoryDto oldCategory = new CategoryDto(id, "-----");
    CategoryDto newCategory = mapper.map(category, CategoryDto.class);

    categoryService.update(oldCategory, newCategory);
  }

}
