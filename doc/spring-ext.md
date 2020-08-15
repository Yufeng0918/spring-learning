# 扩展原理
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

invokeBeanFactoryPostProcessors(beanFactory)

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





## 4. 容器启动原理

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



### 4.1 prepareRefresh刷新前的预处理

+ initPropertySources()初始化一些属性设置;子类自定义个性化的属性设置方法；
+ getEnvironment().validateRequiredProperties();检验属性的合法等
+ earlyApplicationEvents= new LinkedHashSet<ApplicationEvent>()保存容器中的一些早期的事件；



### 4.2 obtainFreshBeanFactory获取BeanFactory

+ refreshBeanFactory()创建BeanFactory,创建了一个DefaultListableBeanFactory()并设置id

+ getBeanFactory()**返回刚才GenericApplicationContext创建的BeanFactory对象**

  

### 4.3 prepareBeanFactory的预准备工作

+ 设置BeanFactory的类加载器、支持表达式解析器
+ 添加部分BeanPostProcessor, **比如ApplicationContextAwareProcessor**
+ 设置忽略的自动装配的接口EnvironmentAware、EmbeddedValueResolverAware、xxx；
+ 注册可以解析的自动装配；能直接在任何组件中自动注入：BeanFactory，ResourceLoader， ApplicationEventPublisher，ApplicationContext
+ 添加BeanPostProcessor【ApplicationListenerDetector】
+ 添加编译时的AspectJ；
+ 给BeanFactory中注册一些能用的组件
  + environment: ConfigurableEnvironment
  + systemProperties
  + systemEnvironment



### 4.4 postProcessBeanFactory准备工作完成后进行的后置处理工作

+ 子类通过重写这个方法来在BeanFactory创建并预准备完成以后做进一步的设置



### 4.5 invokeBeanFactoryPostProcessors执行BeanFactoryPostProcessor的方法

在BeanFactory标准初始化之后执行BeanFactoryPostProcessor和BeanDefinitionRegistryPostProcessor
**执行BeanDefinitionRegistryPostProcessor**

+ 获取所有的BeanDefinitionRegistryPostProcessor；
+ 执行实现了PriorityOrdered优先级接口的postProcessBeanDefinitionRegistry(registry)
+ 执行实现了Ordered顺序接口的postProcessBeanDefinitionRegistry(registry)
+ 最后执行没有实现任何优先级或者是顺序接口的postProcessor.postProcessBeanDefinitionRegistry(registry)

**执行BeanFactoryPostProcessor的方法**

+ 获取所有的BeanFactoryPostProcessor
+ 执行实现了PriorityOrdered的postProcessor.postProcessBeanFactory()
+ 执行实现了Ordered顺序接口的postProcessor.postProcessBeanFactory()
+ 执行没有实现任何优先级或者是顺序接口的postProcessor.postProcessBeanFactory()



### 4.6 registerBeanPostProcessors注册BeanPostProcessor

不同接口类型的BeanPostProcessor在Bean创建前后的执行时机是不一样的

+ BeanPostProcessor
+ DestructionAwareBeanPostProcessor
+ InstantiationAwareBeanPostProcessor
+ SmartInstantiationAwareBeanPostProcessor
+ MergedBeanDefinitionPostProcessor

**注册BeanPostProcessor**		

+ 获取所有的 BeanPostProcessor

+ 先注册PriorityOrdered优先级接口的BeanPostProcessor, 把每一个BeanPostProcessor添加到BeanFactory中

+ 再注册Ordered接口的

+ 最后注册没有实现任何优先级接口的

+ 最终注册MergedBeanDefinitionPostProcessor

+ 注册一个ApplicationListenerDetector

  

### 4.7 initMessageSource初始化MessageSource组件

+ 获取BeanFactory
+ 看容器中是否有id为messageSource的，类型是MessageSource的组件
  + 如果有赋值给messageSource
  + 如果没有自己创建一个DelegatingMessageSource
+ MessageSource：取出国际化配置文件中的某个key的值；能按照区域信息获取
+ **把创建好的MessageSource注册在容器中**



### 4.8 initApplicationEventMulticaster初始化EventMulticaster

+ 获取BeanFactory
+ 从BeanFactory中获取applicationEventMulticaster的ApplicationEventMulticaster
+ 将创建的ApplicationEventMulticaster添加到BeanFactory中，以后其他组件直接自动注入



### 4.9 onRefresh()留给子容器

子类重写这个方法，在容器刷新的时候可以自定义逻辑



### 4.10 registerListeners注册ApplicationListener

+ 从容器中拿到所有的ApplicationListener

+ 将每个监听器添加到事件派发器中

+ 派发之前步骤产生的事件

  

### 4.11 finishBeanFactoryInitialization初始化余下的单实例Bean

#### 宏观流程: BeanFactory.preInstantiateSingletons()初始化后剩下的单实例bean

+ 获取容器中的所有Bean，依次进行初始化和创建对象
+ 获取Bean的定义信息；RootBeanDefinition
+ Bean不是抽象的，是单实例的，是懒加载
  + 判断是否是FactoryBean；是否是实现FactoryBean接口的Bean；
  + 不是工厂Bean利用getBean(beanName)创建对象
    + doGetBean(name, null, null, false)
    + 先获取缓存中保存的单实例Bean。如果能获取到说明这个Bean之前被创建过（所有创建过的单实例Bean都会被缓存)
    + 缓存中获取不到，开始Bean的创建对象流程；
    + 标记当前bean已经被创建
    + 获取Bean的定义信息
    + **获取当前Bean依赖的其他Bean, 如果有按照getBean()把依赖的Bean先创建出来**
    + **启动单实例Bean的创建流程(微观流程)**



#### 微观流程：创建单实例Bean

+ createBean(beanName, mbd, args)
+ **BeanPostProcessor先拦截返回代理对象r**esolveBeforeInstantiation(beanName, mbdToUse), **InstantiationAwareBeanPostProcessor提前执行**
  + postProcessBeforeInstantiation()
  + postProcessAfterInitialization()；
+ 如果前面的InstantiationAwareBeanPostProcessor没有返回代理对象进入4
+ doCreateBean(beanName, mbdToUse, args)创建Bean
  + createBeanInstance(beanName, mbd, args)利用工厂方法或者对象的构造器创建出Bean
  + applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName)调用**MergedBeanDefinitionPostProcessor的postProcessMergedBeanDefinition(mbd, beanType, beanName)**
+ **Bean属性赋值**populateBean(beanName, mbd, instanceWrapper);
  + 执行InstantiationAwareBeanPostProcessor后置处理器的postProcessAfterInstantiation()
  + 执行InstantiationAwareBeanPostProcessor后置处理器postProcessPropertyValues()
  + 应用Bean属性的值；为属性利用setter方法等进行赋值，**applyPropertyValues(beanName, mbd, bw, pvs)**
+ **Bean初始化**initializeBean(beanName, exposedObject, mbd)
  + 执行Aware接口方法, invokeAwareMethods(beanName, bean)
  + **执行初始化之前方法**，applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName)
  + **invokeInitMethods(beanName, wrappedBean, mbd)执行接口初始化**
  + **执行初始化之后**，applyBeanPostProcessorsAfterInitializationBeanPostProcessor.postProcessAfterInitialization()
+ 注册Bean的销毁方法
+ **将创建的Bean添加到缓存中singletonObjects**
+ 检查所有的Bean是否是**SmartInitializingSingleton接口的；如果是；就执行afterSingletonsInstantiated()；**



### 4.12 finishRefresh完成BeanFactory的创建工作

+ **initLifecycleProcessor()初始化和生命周期有关的后置处理器**
  + 默认从容器中找是否有lifecycleProcessor的组件
  + 如果没有new DefaultLifecycleProcessor()加入到容器；
+ **执行LifecycleProcessor.onRefresh方法**
+ publishEvent(new ContextRefreshedEvent(this))**发布容器刷新完成事件**
+ liveBeansView.registerApplicationContext(this)

