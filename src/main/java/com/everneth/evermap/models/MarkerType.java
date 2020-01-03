package com.everneth.evermap.models;

public enum MarkerType {
  SHOP("shop"), BASE("base");

  private String value;

  MarkerType(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static MarkerType fromString(String value) {
    for (MarkerType mt : MarkerType.values()) {
      if (mt.value.equalsIgnoreCase(value)) {
        return mt;
      }
    }
    return null;
  }
}