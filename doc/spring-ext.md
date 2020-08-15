# 扩展原理
```JAVA
@Override
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			// Prepare this context for refreshing.
			prepareRefresh();

			// Tell the subclass to refresh the internal bean factory.
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

			// Prepare the bean factory for use in this context.
			prepareBeanFactory(beanFactory);

			try {
				// Allows post-processing of the bean factory in context subclasses.
				postProcessBeanFactory(beanFactory);

				// Invoke factory processors registered as beans in the context.
				invokeBeanFactoryPostProcessors(beanFactory);

				// Register bean processors that intercept bean creation.
				registerBeanPostProcessors(beanFactory);

				// Initialize message source for this context.
				initMessageSource();

				// Initialize event multicaster for this context.
				initApplicationEventMulticaster();

				// Initialize other special beans in specific context subclasses.
				onRefresh();

				// Check for listener beans and register them.
				registerListeners();

				// Instantiate all remaining (non-lazy-init) singletons.
				finishBeanFactoryInitialization(beanFactory);

				// Last step: publish corresponding event.
				finishRefresh();
			}

			catch (BeansException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Exception encountered during context initialization - " +
							"cancelling refresh attempt: " + ex);
				}

				// Destroy already created singletons to avoid dangling resources.
				destroyBeans();

				// Reset 'active' flag.
				cancelRefresh(ex);

				// Propagate exception to caller.
				throw ex;
			}

			finally {
				// Reset common introspection caches in Spring's core, since we
				// might not ever need metadata for singleton beans anymore...
				resetCommonCaches();
			}
		}
	}
```



## 1. BeanPostProcessor

BeanFactory的后置处理器；

+ 在BeanFactory标准初始化之后调用，来定制和修改BeanFactory的内容
+ 所有的bean定义已经保存加载到beanFactory，但是bean的实例还未创建

```JAVA
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        System.out.println("MyBeanFactoryPostProcessor");
        int count = configurableListableBeanFactory.getBeanDefinitionCount();
        String[] names = configurableListableBeanFactory.getBeanDefinitionNames();
        System.out.println("total: " + count);
        System.out.println(Arrays.asList(names));
    }

}
```



**原理**

**invokeBeanFactoryPostProcessors(beanFactory);**

+ 直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
+ 在初始化创建其他组件前面执行

 



## 2. BeanDefinitionRegistryPostProcessor

是BeanFactoryPostProcessor中的一种，但是优先于其他BeanFactoryPostProcessor执行

**利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件**；

 ```JAVA
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry...bean的数量："+registry.getBeanDefinitionCount());
        //RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Blue.class).getBeanDefinition();
        registry.registerBeanDefinition("hello", beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor...bean的数量："+beanFactory.getBeanDefinitionCount());
    }
}

 ```

**原理**

invokeBeanFactoryPostProcessors(beanFactory);

+ 从容器中获取到所有的**BeanDefinitionRegistryPostProcessor**组件。
  + 依次触发所有的postProcessBeanDefinitionRegistry()方法
  + 再来触发postProcessBeanFactory()方法BeanFactoryPostProcessor；

+ 再来从容器中找到BeanFactoryPostProcessor组件；然后依次触发postProcessBeanFactory()方法





## 3. ApplicationListener

监听容器中发布的事件。事件驱动模型开发；

```JAVA
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("收到事件："+event);
    }
}
```

**原理**

**初始化广播器**

+ initApplicationEventMulticaster()**初始化ApplicationEventMulticaster**

+ 先去容器中找有没有id=“applicationEventMulticaster”的组件
+ 如果没有this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);

**添加监听器**

+ registerListeners()**添加listener**

+ 从容器中拿到所有的监听器，把他们注册到applicationEventMulticaster中

**广播时间**

+ publishEvent(new ContextRefreshedEvent(this))**广播**

+ 获取事件的多播器（派发器）：getApplicationEventMulticaster()， multicastEvent派发事件：
+ 获取到所有的ApplicationListener；
  + 如果有Executor，可以支持使用Executor进行异步派发；
  + 否则，同步的方式直接执行listener方法；invokeListener(listener, event);


