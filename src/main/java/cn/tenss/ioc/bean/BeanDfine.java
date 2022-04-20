package cn.tenss.ioc.bean;

public class BeanDfine {
  private Object bean;
  private Class<?> clazz;
  private String name;
  private Boolean isSingleton;

  public BeanDfine(String name, Object bean) {
    this(name, bean, bean.getClass());
  }

  public BeanDfine(String name, Object bean, Class<?> clazz) {
    this(name, bean, clazz, true);
  }

  public BeanDfine(String name, Object bean, Class<?> clazz, Boolean isSingleton) {
    this.name = name;
    this.bean = bean;
    this.clazz = clazz;
    this.isSingleton = isSingleton;
  }

  public Object getBean() {
    return bean;
  }

  public void setBean(Object bean) {
    this.bean = bean;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }

  public Boolean getIsSingleton() {
    return isSingleton;
  }

  public void setIsSingleton(Boolean isSingleton) {
    this.isSingleton = isSingleton;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
