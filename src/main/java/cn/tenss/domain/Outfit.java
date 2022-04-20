package cn.tenss.domain;

import cn.tenss.ioc.annotation.Bean;

@Bean
public class Outfit {
  private String name = "T-shirt";
  private String color = "red";

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }
}
