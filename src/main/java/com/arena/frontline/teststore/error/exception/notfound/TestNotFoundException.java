package com.arena.frontline.teststore.error.exception.notfound;

public class TestNotFoundException extends ItemNotFoundException{

  public TestNotFoundException(String testId) {
    super(testId, "Test");
  }
}
