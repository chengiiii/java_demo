package cn.tenss;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.tenss.domain.Hero;
import cn.tenss.domain.League;
import cn.tenss.ioc.ClasspathClassReader;
import cn.tenss.ioc.Ioc;
import cn.tenss.ioc.SimpIoc;
import cn.tenss.ioc.annotation.Bean;

public class IocTest {
  private static Ioc ioc = new SimpIoc();

  private static Set<String> packages = new HashSet<>();

  public static void main(String[] args) throws Exception {
    IocTest.run(IocTest.class, args);
    League league = ioc.getBean(League.class);
    Hero hero = league.getHero();
    System.out.println(hero);
  }

  private static Stream<Class<?>> findClasses(String className) {
    ClasspathClassReader reader = new ClasspathClassReader();
    return reader.readClasses(className).stream();
  }

  public static void run(Class<?> clazz, String... args) {
    if (clazz == null) {
      List<StackTraceElement> st = Arrays.stream(Thread.currentThread().getStackTrace())
          .filter(trace -> trace.getMethodName().contains("main")).collect(Collectors.toList());
      if (st.size() > 0) {
        try {
          clazz = Class.forName(st.get(0).getClassName());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    Optional.of(clazz)
        .map(Class::getPackage)
        .map(Package::getName)
        .ifPresent(packages::add);

    initIoc();
  }

  public static void initIoc() {
    packages.stream().flatMap(IocTest::findClasses)
        .forEach(IocTest::parseAndCreate);
  }

  public static void parseAndCreate(Class<?> clazz) {
    if (clazz.isAnnotationPresent(Bean.class)) {
      ioc.addBean(clazz);
    }
  }
}
