# Spring Framework Notes

## 1. Spring Framework
#### Framework for lightweight framework for building Java applications
+ Lightweight
	* Build any application
	* Less code to gain the benefits
+ Dependency Injection
	* Decouple the layer/class
	* Simplified application configuration
	* Management of common dependencies in a single repository
	* Interface Philosophy 
	* Improved testability
+ Aspect Orient Programming
	* Declarative programming
	* Eliminating boilerplate code with aspects and templates
+ Transaction Management
+ Spring Expression Language (SpEL)
+ Validation in Spring
+ Accessing Data in Spring
+ Object/XML Mapping (OXM) in Spring in Spring
+ Managing Transactions
+ MVC in the Web Tier
***   

## 2. Spring Environment
#### Required
+ Core:
    + dist\spring.jar
    + lib\jakarta-commons\commons-logging.jar
+ AOP:
    + lib/aspectj/aspectjweaver.jar
    + aspectjrt.jar
    + lib/cglib/cglib-nodep-2.1_3.jar
+ Annotation:
    + lib\j2ee\common-annotations.jar
#### Modules
+ Core
	+ context: ApplicationContext
	+ core: Spring core
	+ bean: Spring beans
	+ aspects: AspectJ
	+ aop: AOP function
+ Data access & integration
    + jdbc: JDBC support
	+ orm:	Hibernate, MyBatis
+ Test
	+ test: Mock test
+ Web
	+ web:	Web ApplicationContext
	+ web servlet:  MVC
***	

## 3. Spring IOC
#### Spring Bean Factory VS POJO Factory
+ POJO Factory:	`Person p1 = PersonFactory.getPerson("Chinese");`
+ Spring Factory: `Person p1 = (Person) factory.getBean("chinese");`
#### Dependency [Lookup VS Injection]
+ Pull:	Dependent Object <Lookup> Container
+ Push:	Dependent Object <Inject> Container
+ Spring Process
	+ Dependency Lookup to access the initial set of components
	+ Dependency Injection by Auto wired
+ BeanFactory
	+ Dependency Injection container to manage components
	+ Loading config for BeanDefinition
+ XML Config
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- Spring name space declaration -->
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd">
<!-- Spring bean definition -->
<bean id="chinese" class="Chinese"/>
<bean id="american" class="American"/>
</beans>
```
```java
public class S02_SpringIOC {
    ApplicationContext factory = new ClassPathXmlApplicationContext("ioc/s01/bean/applicationContext.xml");
    Person p1 = (Person) factory.getBean("chinese");
}
```
#### @Configuration
- @Bean(value = "..."), value is bean name
- @ComponentScan("...") to scan package
    + excludeFilters = {@Filter(type = FilterType.ANNOTATION, classes = {Controller.class}) })
    + includeFilters and useDefaultFilters = false
    + @FilterType
        + ANNOTATION: @Controller, @Service
        + ASSIGNABLE_TYPE: class name
        + ASPECT_J, REGX: rarely use
```java
// Configuration class
@Configuration
@ComponentScan("com.bp.spring.ioc.s01.bean")
public class PersonConfig {

	@Bean
	public Chinese chinese(){
		return new Chinese();
	}
	@Bean("american")
	public American american01(){
		return new American();
	}
}

public class S02_SpringIOC {
    ApplicationContext factory = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    Person p1 = (Person) factory.getBean("chinese");
}
```
#### Customized Filter
- create class to implement TypeFilter, return true to create bean and ignore if its false
- import customized filter in @ComponentScan
```java
public class MyFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
            throws IOException {

        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        Resource resource = metadataReader.getResource();
        return false;
    }
}

@Configuration
@ComponentScan(value = "com.bp.spring.ioc.s01.bean", includeFilters = {
        @Filter(type = FilterType.CUSTOM, classes = {MyFilter.class})
}, useDefaultFilters = false)
public class ApplicationConfig {}
```
#### Bean Scope
+ Singleton
    + Instance Bean when application startup
    + SCOPE_SINGLETON, eager-init
```
@Bean
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public SingletonBean singletonBean(){
	return new SingletonBean();
}
```
+ Prototype
    + Instance Bean when application.getBean()
    + SCOPE_PROTOTYPE, lazy-init
```
@Bean
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public PrototypeBean prototypeBean(){
	return new PrototypeBean();
}
```
+ Request:	
    + In a web application, one instance of the bean is created for each request
    + ScopedProxyMode.INTERFACES inject interface into context, interface proxy the concrete bean
```
@Bean
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public RequestBean requestBean(){
	return new RequestBeanImpl();
}
```
- Session:	
   + In a web application, one instance of the bean is created for each session
   + ScopedProxyMode.TARGET_CLASS generate interface and inject into context, interface proxy the concrete bean
```
@Bean
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public SessionBean sessionBean(){
	return new SessionBean();
}
```
#### Lazy Initialization
- XML
```xml
<bean id="orderServieBean" class="itcast.OrderServiceBean" lazy-init="true"/>
```
- Annotation
```java
public class ApplicationConfig {

    @Lazy
    @Bean
    public American american() {
        System.out.println("add american");
        return new American();
    }
}
```
#### @Conditional 
- @Conditional contains multiple Condition.class
- bean only registered if condition fulfill
- annotated for class or method
```java
public class ApplicationConfig {

    @Bean("linus")
    @Conditional({ LinuxCondition.class})
    public Chinese chinese2() {
        return new Chinese("Linus", 50);
    }
}


public class LinuxCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        ClassLoader classLoader = context.getClassLoader();
        Environment environment = context.getEnvironment();
        BeanDefinitionRegistry registry = context.getRegistry();

        String property = environment.getProperty("os.name");
        if (property.toLowerCase().contains("mac")) {
            return true;
        }
        return false;
    }
}
```
#### @Import
- @ComponentScan：scan owe component,  @Controller, @Service, @Repository, @Component
- @Bean: 3rd party component
- @Import: import component to context
```java
@Import(Color.class)
public class MyConfig {
}
```
- @ImportSelector: return the import component full path name
```java
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"Blue", "Yellow"};
    }
}

@Import({Color.class, MyImportSelector.class })
public class MyConfig { }
```
- @ImportBeanDefinitionRegistrar: register bean via registerBeanDefinition method
```java
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

            RootBeanDefinition rainBowBean = new RootBeanDefinition(RainBow.class);
            registry.registerBeanDefinition("rainBow", rainBowBean);
    }
}

@Configuration
@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MyConfig { }
```
#### FactoryBean
- create factoryBean to implements FactoryBean<Color>
- getBean("factoryName") to return object created by factory
- getBean("&factoryName") to return factory self

```java
public class IOCTest {

    @Test
    public void testImport() {

        Object object = applicationContext.getBean("colorFactoryBean");
        Object factoryBean = applicationContext.getBean("&colorFactoryBean");
        System.out.println("bean name: " + object.getClass());
        System.out.println("factory bean: " + factoryBean.getClass());
    }
}

public class ColorFactoryBean implements FactoryBean<Color> {
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
```
***

## 4. Injection
#### Constructor
```
<bean id="personDao" class="dao.impl.PersonDaoBean"/>
<bean id="personService" class="service.impl.PersonServiceBean">
	<constructor-arg index="0" type="dao.PersonDao" ref="personDao"/>
	<constructor-arg index="1" value="NUS"/>
</bean>
```
#### Properties
```xml
<bean id="personService" class="service.impl.PersonServiceBean">
	<property name="personDao">
		<bean class="dao.impl.PersonDaoBean"/>
	</property>
	<property name="name" value="NUS"/>
	<property name="id" value="1"/>
</bean>
```
#### @Value
- direct value: @Value("SG")
- SpEL: @Value("#{20-2}")
#### PropetySource
- load the person.properties to get person.nickName value
- inject person.nickName value into Person
- value in property file is inject to Environment's Property
```java
@Configuration
@PropertySource("classpath:/person.properties")
public class MyConfigOfPropertyValue {

    @Bean
    public Chinese person() {
        return new Chinese();
    }
}

public class Chinese implements Person {
    @Value("${person.nickName}")
    private String nickName;
}

public class IOCPropertyValue {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfPropertyValue.class);

    @Test
    public void testInject() {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("person.nickName");
        System.out.println(property);
        printBeans();
    }
}
```
#### Dependency Injection
- Spring use DI to complement denpendencies value injection
- @Autowired

Class | Layer
---|---
Service | business
Controller | controller
Repository | persistent
Component | unclassified 

```java
@Repository("personDao")
public class PersonDaoBean implements PersonDao {...}

@Service("personService") 
public class PersonServiceBean implements PersonService {
	@Autowired
	private PersonDao personDao;
}
```
#### Collection
```
<list>
	<value>NUS_KT</value>
	<value>NTU_BY</value>
	<value>SMU_BT</value>
</list>

<set>
	<value>NUS</value>
	<value>NTU</value>
	<value>SMU</value>
</set>

<props>
	<prop key="key1">NUS_K1</prop>
	<prop key="key2">NTU_K2</prop>
	<prop key="key3">SMU_K3</prop>
</props>

<map>
	<entry key="key-1" value="NUS-K1"/>
	<entry key="key-2" value="NTU-K1"/>
	<entry key="key-3" value="SMU-K1"/>
</map>
```
***
		
## 5. Bean Lifecycle
#### Life
+ AnnotationConfigApplicationContext instanced
    + createBean()
    + populateBean(): set bean's properties
    + initializeBean()
        + applyBeanPostProcessorBeforeInitialization()
            + loop all BeanPostProcessor to execute postProcessBeforeInitialization
            + if return null will break loop
        + invokeInitMethod
        + applyBeanPostProcessorAfterInitialization(): loop all BeanPostProcessor to execute postProcessAfterInitialization
+ Initial
    + Instantiate
    + Populate properties
    + BeanNameAware[setBeanName()]
    + BeanFactoryAware[setBeanFactory()]
    + ApplicationContextAware[setApplicationContext()]
    + BeanPostProcessors[Pre-initialization]
    + Customize init-method
    + BeanPostProcessors[Post-initialization]
+ Ready -> Shutdown
+ Destroy
	+ DisposableBean[destroy()]
	+ Customize destroy-method

```
//init && destory method declared in XML
<bean id="chinese" class="Chinese" init-method="init" destroy-method="close">


//init && destory method declared in Java
@Bean(initMethod="customizeInit", destroyMethod = "customizeDestroy")
public SpringBean springBean(){
	return new SpringBean();
}
```
#### Init & Destory
- declare in @Bean
- Singleton
    - init method invoke after instanced and properties injected
    - destory method invoke when context close
- Prototype
    - only invoke init method
```java
@Configuration
public class MyConfigOfLifeCycle {

    @Bean(initMethod = "init", destroyMethod = "destory")
    public Car car() {
        return new Car();
    }
}

```
#### InitializingBean & DisposableBean
- implements InitializingBean and override afterPropertiesSet()
- implements DisposableBean and override destory()
```java
@Component
public class Cat implements InitializingBean, DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("cat destory");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat after properties set");
    }
}

```
#### @PostConstruct && @PreDestroy
- JSR250: @PostConstruct & @PreDestroy
- InitDestroyAnnotationBeanPostProcessor set @PostConstruct as initAnnotationType
- InitDestroyAnnotationBeanPostProcessor set @PreDestroy as destroyAnnotationType
- specific way of BeanPostProcessor
```java
@Component
public class Dog {

    @PostConstruct
    public void init() { System.out.println("dog init"); }

    @PreDestroy
    public void destory(){ System.out.println("dog destory"); }
}
```
#### BeanPostProcessor
- implements BeanPostProcessor 
- sequence
    + constructor
    + invoke BeanPostProcessor's postProcessBeforeInitialization method
    + init(), @PostConstruct, afterPropertiesSet()
    + invoke BeanPostProcessor's postProcessAfterInitialization method
- @Autowired, @PostConstruct, @PreDestory is implemented by BeanPostProcessor
```java
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization .. " + beanName + ": " + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization .. " + beanName + ": " + bean);
        return bean;
    }
}
```
***



## 9. Aspect Of Programming
#### Overview
- An aspect’s functionality (advice) is woven into a program’s execution at one or more join points
#### AOP by JDK|CGLB
- Instance targetObject and return Proxy
- Get targetObject and invoke

#### Spring AOP Annotation
- Advice
    - describing the job that an aspect will perform
    - advice addresses the question of when to perform
    - type: Before, After, After-returning, After-throwing, Around
- Join Points
	- A join point is a point in the execution of the application where an aspect can be plugged in
- Pointcuts
	- Expression for join points

```
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:context="http://www.springframework.org/schema/context" 
	   xmlns:aop="http://www.springframework.org/schema/aop"      
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
		   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
		   http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
		   http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd">
		<aop:aspectj-autoproxy/> 
		<!--Declare the AOP Bean-->
		<bean id="myInterceptor" class="aop.MyInterceptor"/>
</beans>


@Aspect
public class MyInterceptor {
	
	/* Expression 
	Pointcut("execution(returnValue package.[child package].class.method(arguments))")
	*/
	@Pointcut("execution(* service.impl.PersonServiceBean.*(..))")
	private void anyMethod() {}
	
	@Before("anyMethod()")
	public void doAccessCheck() { System.out.println("before info");}
	
	@Before("anyMethod() && args(userName)") 
	public void doAccessCheck(String userName) {...}

	@AfterReturning(pointcut = "anyMethod()", returning="result")
	public void doAfterReturn(String result) {...}

	@After("anyMethod()")
	public void doAfter() {...}

	@AfterThrowing(pointcut="anyMethod()", throwing="e")	
	public void doAfterThrowing(Exception e){...}

	@Around("anyMethod()")	
	public Object doProfiling(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		return result;
	}
}
```
#### AOP Expression
Result | Expression
---|---
return type is String | "execution(java.lang.String service.impl.PersonServiceBean.*(..))"
first argument is String | "execution(java.lang.String service.impl.PersonServiceBean.*(java.lang.String..))"
return type is not void | "execution(!void service.impl.PersonServiceBean.*(..))"
all the class under package | "execution(* service..*.*(..))"

#### Spring AOP XML
```
<aop:config>
	<aop:aspect id="asp" ref="aspectbean">
		<aop:pointcut id="mycut" expression="execution(* service.impl.PersonServiceBean.*(..))"/>
		<aop:before pointcut-ref="mycut"  method="doAccessCheck"/>
		<aop:after-returning pointcut-ref="mycut" method="doAfterReturn"/>
		<aop:after pointcut-ref="mycut" method="doAfter"/>
		<aop:after-throwing pointcut-ref="mycut" method="doAfterThrowing"/>
		<aop:around pointcut-ref="mycut" method="doProfiling"/>
	</aop:aspect>
</aop:config>
```
