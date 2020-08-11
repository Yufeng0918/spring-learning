# 生命周期

## 1. initMethod和destroyMethod管理

```java
@ComponentScan("com.bp.spring.lifecycle")
public class LifecyecleConfig {

    @Bean(initMethod="init",destroyMethod="detory")
    public Car car(){
        return new Car();
    }
}
```





## 2. 实现InitializingBean和DisposableBean接口

重写afterPropertiesSet和destroy方法

```java
@Component
public class Cat implements InitializingBean, DisposableBean {

    public Cat(){
        System.out.println("cat constructor...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("cat...destroy...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat...afterPropertiesSet...");
    }

}
```



## 3. 添加@PostConstruct和@PreDestroy注解

```JAVA
public class Dog implements ApplicationContextAware {
    //对象创建并赋值之后调用
    @PostConstruct
    public void init(){
        System.out.println("Dog....@PostConstruct...");
    }

    //容器移除对象之前
    @PreDestroy
    public void detory(){
        System.out.println("Dog....@PreDestroy...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
```



## 4. BeanPostProcessor

### 调用流程

+ 创建Bean并赋值
+ 调用postProcessBeforeInitialization
+ 初始化
+ 调用postProcessAfterInitialization

```JAVA
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization..."+beanName+"=>"+bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization..."+beanName+"=>"+bean);
        return bean;
    }

}

```

### 源码解析

+ this.populateBean(beanName, mbd, instanceWrapper)为bean赋值
+ this.applyBeanPostProcessorsBeforeInitialization在初始化前调用
+ invokeInitMethods 初始化方法
+ applyBeanPostProcessorsAfterInitialization在初始化后调用

```java
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

	protected Object doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args) throws BeanCreationException {
		try {
            this.populateBean(beanName, mbd, instanceWrapper);
            exposedObject = this.initializeBean(beanName, exposedObject, mbd);
        } catch (Throwable var18) {
            if (var18 instanceof BeanCreationException && beanName.equals(((BeanCreationException)var18).getBeanName())) {
                throw (BeanCreationException)var18;
            }

            throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Initialization of bean failed", var18);
        }
	}

	 protected Object initializeBean(String beanName, Object bean, @Nullable RootBeanDefinition mbd) {
	 	if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(() -> {
                this.invokeAwareMethods(beanName, bean);
                return null;
            }, this.getAccessControlContext());
        } else {
            this.invokeAwareMethods(beanName, bean);
        }

        Object wrappedBean = bean;
        if (mbd == null || !mbd.isSynthetic()) {
            wrappedBean = this.applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        }

        try {
            this.invokeInitMethods(beanName, wrappedBean, mbd);
        } catch (Throwable var6) {
            throw new BeanCreationException(mbd != null ? mbd.getResourceDescription() : null, beanName, "Invocation of init method failed", var6);
        }

        if (mbd == null || !mbd.isSynthetic()) {
            wrappedBean = this.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        }

        return wrappedBean;
	 }
}
```



### Spring应用

**AutowiredAnnotationBeanPostProcessor**

自动对成员变量进行赋值



**ApplicationContextAwareProcessor**

对实现ApplicationContextAware的Bean注入容器对象

```JAVA
class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ConfigurableApplicationContext applicationContext;
    private final StringValueResolver embeddedValueResolver;

    public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.embeddedValueResolver = new EmbeddedValueResolver(applicationContext.getBeanFactory());
    }

    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof EnvironmentAware) && !(bean instanceof EmbeddedValueResolverAware) && !(bean instanceof ResourceLoaderAware) && !(bean instanceof ApplicationEventPublisherAware) && !(bean instanceof MessageSourceAware) && !(bean instanceof ApplicationContextAware)) {
            return bean;
        } else {
            AccessControlContext acc = null;
            if (System.getSecurityManager() != null) {
                acc = this.applicationContext.getBeanFactory().getAccessControlContext();
            }

            if (acc != null) {
                AccessController.doPrivileged(() -> {
                    this.invokeAwareInterfaces(bean);
                    return null;
                }, acc);
            } else {
                this.invokeAwareInterfaces(bean);
            }

            return bean;
        }
    }

    private void invokeAwareInterfaces(Object bean) {
        if (bean instanceof EnvironmentAware) {
            ((EnvironmentAware)bean).setEnvironment(this.applicationContext.getEnvironment());
        }

        if (bean instanceof EmbeddedValueResolverAware) {
            ((EmbeddedValueResolverAware)bean).setEmbeddedValueResolver(this.embeddedValueResolver);
        }

        if (bean instanceof ResourceLoaderAware) {
            ((ResourceLoaderAware)bean).setResourceLoader(this.applicationContext);
        }

        if (bean instanceof ApplicationEventPublisherAware) {
            ((ApplicationEventPublisherAware)bean).setApplicationEventPublisher(this.applicationContext);
        }

        if (bean instanceof MessageSourceAware) {
            ((MessageSourceAware)bean).setMessageSource(this.applicationContext);
        }

        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware)bean).setApplicationContext(this.applicationContext);
        }

    }
}

```



