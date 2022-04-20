package cn.tenss.ioc;

import java.util.Set;

public interface ClassReader {
  Set<Class<?>> readClasses(String packageName);
}
