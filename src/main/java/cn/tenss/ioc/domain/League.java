package cn.tenss.ioc.domain;

import cn.tenss.ioc.annotation.Bean;
import cn.tenss.ioc.annotation.Inject;

@Bean
public class League {
  private String name = "League";
  @Inject
  private Hero hero;

  public void play() {
    hero.say();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Hero getHero() {
    return hero;
  }

  public void setHero(Hero hero) {
    this.hero = hero;
  }
}
