package cn.tenss.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Bean {
  String value() default "";

  boolean singleton() default true;
}
