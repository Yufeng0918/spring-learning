# Spring Framework Notes

## 1. Spring框架简介
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

## 2. 环境搭建
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

## [3. 组件注册](./doc/spring-registry.md)


## 4. Injection
#### Constructor
```
<bean id="personDao" class="com.bp.spring.registry.dao.impl.PersonDaoBean"/>
<bean id="personService" class="service.impl.PersonServiceBean">
	<constructor-arg index="0" type="com.bp.spring.registry.dao.PersonDao" ref="personDao"/>
	<constructor-arg index="1" value="NUS"/>
</bean>
```
#### Properties
```xml
<bean id="personService" class="service.impl.PersonServiceBean">
	<property name="personDao">
		<bean class="com.bp.spring.registry.dao.impl.PersonDaoBean"/>
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
- Spring use DI to complement dependencies value injection
- @Autowired
    + find the component based on class type
    + if multiple components exists, inject the property based on property name
    + @Qualifier indicate the component to inject
    + must inject the value to property, @Autowired(required = false) will not report error if no value to inject
    + @Primary indicate primary choice if multiple components found
    + annotated in property, constructor, setter, the injected value is from context

Class | Layer
---|---
Service | business
Controller | controller
Repository | persistent
Component | unclassified 

```java
@Repository("personDao")
public class PersonDaoBean implements PersonDao {}

@Service("personService") 
public class PersonServiceBean implements PersonService {
	@Autowired
	private PersonDao personDao;
}
```
- @Resource
    + @Resource from JSR250
    + @Autowired from Spring
    + No support for @Primary and @Autowired(required = false)
- @Inject
    + @Inject from JSR330
    + support @Primary but no support @Autowired(required = false)
```groovy
compile group: 'javax.inject', name: 'javax.inject', version: '1'
```
- Aware component
    + get the spring underlying component
    + auto inject to object property
    + Aware component is handle by AwareProcessor
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
#### Profile
- switch envrionment setting
- how to switch
    - JVM parameter: Dspring.profile.active=test
    - source code: to set active profile, register configuration and refresh
    - if bean is not indicate profile, it will be loaded for all env
```java
@Configuration
@PropertySource("classpath:/dbconfig.properties")
public class MyConfigOfProfile implements EmbeddedValueResolverAware {


    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}")String pwd) throws Exception{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mysql");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }


    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}")String pwd) throws Exception{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/nacos");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }
}


public class IOCProfileTest {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    @Test
    public void testProfile() {
        applicationContext.getEnvironment().setActiveProfiles("test");
        applicationContext.register(MyConfigOfProfile.class);
        applicationContext.refresh();
    }
}
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

## 6. AOP
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
- Annotation	
    - indicate @EnableAspectJAutoProxy in Configuration
    - indicate @Aspect in aspect bean
```xml
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
</beans>
```
```java

@EnableAspectJAutoProxy
public class ApplicationConfig {

    @Bean
    public TxtAdvice txtAdvice() {
        return new TxtAdvice();
    }
}
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
	public void doAccessCheck(String userName) {}

	@AfterReturning(pointcut = "anyMethod()", returning="result")
	public void doAfterReturn(String result) {}

	@After("anyMethod()")
	public void doAfter() {}

	@AfterThrowing(pointcut="anyMethod()", throwing="e")	
	public void doAfterThrowing(Exception e){}

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

#### AOP Implementation
- @EnableAspectJAutoProxy
    + @Import(AspectJAutoProxyRegistrar.class)
        + AspectJAutoProxyRegistrar register bean
        + register AnnotationAwareAspectJAutoProxyCreator as internalAutoProxyCreator bean
    + AnnotationAwareAspectJAutoProxyCreator
        + AspectJAwareAdvisorAutoProxyCreator
            + AbstractAdvisorAutoProxyCreator
                + AbstractAutoProxyCreator
                    + implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
   + AbstractAutoProxyCreator
        + setBeanFactory
        + postProcessBeforeInstantiation
        + postProcessAfterInstantiation
   + AbstractAdvisorAutoProxyCreator
        + override setBeanFactory 
        + call initBeanFactory
   + AnnotationAwareAspectJAutoProxyCreator
        + initBeanFactory
- Creation Sequence
    + create AnnotationConfigApplicationContext
    + refresh() to call registerBeanPostProcessors()
        + get list context's BeanPostProcessor definition
        + contains internalAutoProxyCreator which is AnnotationAwareAspectJAutoProxyCreator
        + register BeanPostProcessor which implements PriorityOrdered
        + register BeanPostProcessor which implements Ordered
            + try to get internalAutoProxyCreator but it's null first time
            + create singleton instance of AnnotationAwareAspectJAutoProxyCreator for internalAutoProxyCreator
            + populatebBean() perform depenedency injection
            + initializeBean()
                + invokeAwareMethods() to execute setBeanFactory for BeanFactoryAware
                    + AnnotationAwareAspectJAutoProxyCreator execute initBeanFactory
                        + ReflectiveAspectJAdvisorFactory
                        + AspectJAdvisorBuilder
                + applyBeanPostProcessorBeforeInitialization()
                + invokeInitMethods()
                + applyBeanPostProcessorAfterInitialization
        + register regular BeanPostProcessor
    + add BeanPostProcessor to BeanFactory
    + AnnotationConfigApplicationContext
        + finishBeanFactoryInitialization
            + loop all the create and get bean via getBean(), doGetBean(), getSinglteon() 
            + createBean()
                + check if bean exist from cache, get bean directly if exist
                + if bean does not exist, create bean and put into cache
                    + resolveBeforeInstantiation: expect beanPostProcessor to return proxy
                        + applyBeanPostProcessBeforeInstantiation
                            if BeanPostProcessor is InstantiationAwareBeanPostProcessor, execute postProcessBeforeInstantiation()
                        + applyBeanPostProcessAfterInstantiation
                    + if no proxy, execute doCreate()
    + AnnotationAwareAspectJAutoProxyCreator
        + execute postProcessBeforeInstantiation()
            + verify if bean in advisedBean
            + verify is bean belong to Advice, Pointcut, @Aspect
            + verify is skip
                + get all the aspect's advice method, advice method is InstantiationModelAwarePointcutAdvisor
                + should skip always return false, means never skip
        + execute postProcessAfterInstantiation()
            + AbstractAutoProxyCreator execute wrapIfNecessary()
            + get all the InstantiationModelAwarePointcutAdvisor for current bean
            + filter all applicable advisor and sort the advisor and wrapped into Object[]
            + create proxy via createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean))
                + get all advisor
                + save into ProxyFactory
                + ProxyFactory create JDKDynamicAopProxy or ObjenesisCglibAopProxy(bean is implments interface)
            + return Proxy Object to context
- Execution Sequence
    + context contains proxy, proxy bean contains advisor and target class
    + CglibAopProxy intercept()
        + if no interceptor chain, direct method.invoke()
        + if has interceptor chain, chain execution till target object
            + get target class interceptor chain via this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
            + create methodInvocation new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy)
                + create list to store interceptor chain, e.g. List<Object> interceptorList = new ArrayList<>(advisors.length);
                + loop all InstantiationModelAwarePointcutAdvisor and wrap to MethodInterceptor
            + CglibMethodInvocation.proceed()
                + if no interceptor, execute method.invoke(target, methodname)
                + start index as = -1
                    + increase index and then get interceptor in sequence
                    + execute Interceptor.invoke(this), this = CglibMethodInvocation
                    + set the MethodInvocation to threadLocal
                    + execute CglibMethodInvocation.proceed() will loop same method again
        + Chain Sequence
            + AspectJAfterThrowingAdvice
                + CglibMethodInvocation.proceed()
                + invokeAdviceMethod() in catch block if there is exception 
            + AspectReturningAdviceInterceptor
                + CglibMethodInvocation.proceed()
                + advice.afterReturning() if there is no exception
            + AspectJAfterAdvice
                + CglibMethodInvocation.proceed()
                + invokeAdviceMethod() in finally block, no catch block
            + MethodBeforeAdviceInterceptor
                + execute beforeAdvice
                + CglibMethodInvocation.proceed()
    
- Notes:
    + BeanPostProcessor：proccesor handle the bean after bean has been instance
    + InstantiationAwareBeanPostProcessor: processor handle the bean before bean creation
    + AnnotationAwareAspectJAutoProxyCreator intercept before all bean creation

#### Transaction
- Transaction configuration
    + @EnableTransactionManagement in configuration
    + configure dataSource bean
    + configure transactionManager(datasource)
- add @Transactional in service layer
```java
@Configuration
@ComponentScan("com.bp.spring.aop.tx")
@EnableTransactionManagement
public class TxConfig {

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception{
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() throws Exception{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("password");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception{
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }
}

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void insertUser(){
        userDao.insert();
    }
}
```
- implementation
    + @EnableTransactionManagement import TransactionManagementConfigurationSelector
    + TransactionManagementConfigurationSelector import AutoProxyRegistrar and ProxyTransactionManagementConfiguration
        + AutoProxyRegistrar: register InfrastructureAdvisorAutoProxyCreator bean definition
            + InfrastructureAdvisorAutoProxyCreator wraped the target class to Proxy bean
        + ProxyTransactionManagementConfiguration: 
            + register transactionAdvisor
            + transactionAdvisor get the annotation metadata based on AnnotationTransactionAttributeSource
            + transactionAdvisor wrapped transactionInterceptor, transactionInterceptor is MethodInterceptor
            + transactionInterceptor
                + get transaction properties
                + get transaction manager, if qualifer is not declared, will get transaction manager based on class type

```java
public abstract class TransactionAspectSupport implements BeanFactoryAware, InitializingBean {
    @Nullable
	protected Object invokeWithinTransaction(Method method, @Nullable Class<?> targetClass, final InvocationCallback invocation) throws Throwable {

        TransactionInfo txInfo = createTransactionIfNecessary(ptm, txAttr, joinpointIdentification);
        Object retVal;
        try {
            retVal = invocation.proceedWithInvocation();
        } catch (Throwable ex) {
            completeTransactionAfterThrowing(txInfo, ex);
            throw ex;
        } finally {
            cleanupTransactionInfo(txInfo);
        }
    }
}
```

## 7. BeanFactoryPostProcessor
- bean defination is loaded into factory
- factory has not instance any object yet

**process**
+ ioc container 
+  invokeBeanFactoryPostProcessor()
    + find all BeanFactoryPostProcessor in BeanFactory
    + execute BeanFactoryPostProcessor postProcessBeanFactory method