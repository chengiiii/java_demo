package cn.tenss.ioc;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import cn.tenss.ioc.annotation.Bean;
import cn.tenss.ioc.annotation.Inject;
import cn.tenss.ioc.bean.BeanDfine;

public class SimpIoc implements Ioc {
  private Map<String, BeanDfine> beanDefineMap = new ConcurrentHashMap<String, BeanDfine>();

  @Override
  public Object createBean(Class<?> clazz) {
    try {
      Object bean = clazz.newInstance();
      return inject(bean);
    } catch (Exception e) {
      System.out.println("createBean error: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Object getBean(String name) {
    BeanDfine beanDfine = beanDefineMap.get(name);
    if (beanDfine == null) {
      throw new RuntimeException("bean not found: " + name);
    }

    if (beanDfine.getIsSingleton()) {
      if (beanDfine.getBean() == null) {
        beanDfine.setBean(createBean(beanDfine.getClazz()));
      }
      return beanDfine.getBean();
    }

    return createBean(beanDfine.getClazz());
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getBean(Class<T> clazz) {
    for (BeanDfine beanDfine : beanDefineMap.values()) {
      if (beanDfine.getClazz().equals(clazz)) {
        return (T) getBean(beanDfine.getName());
      }
    }
    return null;
  }

  @Override
  public void addBean(Class<?> clazz) {
    String beanName = clazz.getSimpleName();
    beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
    addBean(beanName, clazz);
  }

  @Override
  public void addBean(String name, Class<?> clazz) {
    try {
      Bean beanAnnotation = clazz.getAnnotation(Bean.class);
      BeanDfine beanDfine = new BeanDfine(name, null, clazz, beanAnnotation.singleton());
      beanDefineMap.put(name, beanDfine);
    } catch (Exception e) {
      System.out.println("addBean error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void addBean(String name, Class<?> clazz, Object bean) {
    BeanDfine beanDfine = new BeanDfine(name, bean, clazz, true);
    beanDefineMap.put(name, beanDfine);
  }

  @Override
  public void removeBean(String name) {
    beanDefineMap.remove(name);
  }

  public List<BeanDfine> getBeanDefineList() {
    return beanDefineMap.values().stream().collect(Collectors.toList());
  }

  private Object inject(Object bean) {
    Class<?> clazz = bean.getClass();
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      Inject injectAnnotation = field.getAnnotation(Inject.class);
      if (injectAnnotation != null) {
        String beanName = injectAnnotation.value();
        if (beanName.isEmpty()) {
          beanName = field.getType().getSimpleName();
          beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
        }
        field.setAccessible(true);
        try {
          field.set(bean, getBean(beanName));
        } catch (Exception e) {
          System.out.println("inject error: " + e.getMessage());
          e.printStackTrace();
        }
      }
    }
    return bean;
  }
}
