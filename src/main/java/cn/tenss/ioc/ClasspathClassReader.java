package cn.tenss.ioc;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import cn.tenss.IocTest;

public class ClasspathClassReader implements ClassReader {

  @Override
  public Set<Class<?>> readClasses(String className) {
    String packageName = className.replaceAll("\\.", "/");
    try {
      Enumeration<URL> dirs = IocTest.class.getClassLoader().getResources(packageName);
      while (dirs.hasMoreElements()) {
        URL url = dirs.nextElement();
        return findClassByPackage(className, new URI(url.getFile()).getPath());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Set<Class<?>> findClassByPackage(String packageName, String packagePath) {
    Set<Class<?>> classes = new HashSet<>();
    if (packagePath == null || packagePath.isEmpty()) {
      throw new IllegalArgumentException("packagePath is null or empty");
    }

    File dir = new File(packagePath);
    if (!dir.exists() || !dir.isDirectory()) {
      throw new IllegalArgumentException("packagePath is not a directory");
    }

    File[] files = dir.listFiles(file -> file.isDirectory() || file.getName().endsWith(".class"));

    for (File file : files) {
      if (file.isDirectory()) {
        classes.addAll(findClassByPackage(packageName + "." + file.getName(), file.getAbsolutePath()));
      } else {
        String className = file.getName().substring(0, file.getName().length() - 6);
        String classFullName = packageName + "." + className;
        try {
          classes.add(Class.forName(classFullName));
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    }

    return classes;
  }

}
