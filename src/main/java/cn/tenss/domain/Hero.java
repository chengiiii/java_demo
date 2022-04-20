package cn.tenss.domain;

import cn.tenss.ioc.annotation.Bean;
import cn.tenss.ioc.annotation.Inject;

@Bean
public class Hero {
  private String name;

  @Inject
  private Outfit outfit;

  public Hero() {
  }

  public String getName() {
    return name = "Hero";
  }

  public void setName(String name) {
    this.name = name;
  }

  public void say() {
    System.out.println(getOutfit());
    System.out.println("My name is " + name + " and I wear " + outfit.getName() + " with " + outfit.getColor());
  }

  public Outfit getOutfit() {
    return outfit;
  }

  public void setOutfit(Outfit outfit) {
    this.outfit = outfit;
  }

  @Override
  public String toString() {
    return "Hero [name=" + getName() + ", outfit=" + getOutfit() + "]";
  }
}
