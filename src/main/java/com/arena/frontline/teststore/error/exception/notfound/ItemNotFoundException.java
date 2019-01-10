package com.arena.frontline.teststore.error.exception.notfound;

public class ItemNotFoundException extends RuntimeException {
  private String itemId;

  public ItemNotFoundException(String itemId, String itemName) {
    super(String.format("%s not found: id = %s", itemName, itemId));
    this.itemId = itemId;
  }

  public String getItemId() {
    return itemId;
  }
}
