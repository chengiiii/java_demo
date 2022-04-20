package cn.tenss.ioc;

public interface Ioc {

  public Object createBean(Class<?> clazz);

  public Object getBean(String name);

  public <T> T getBean(Class<T> clazz);

  public void addBean(Class<?> clazz);

  public void addBean(String name, Class<?> clazz);

  public void addBean(String name, Class<?> clazz, Object bean);

  public void removeBean(String name);
}
