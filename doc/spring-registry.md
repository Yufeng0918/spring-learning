# 组件注册

## 1. Configruation注册组件

### @Bean创建对象

```java
@Configuration  //告诉Spring这是一个配置类
public class ConfigurationConfig {

    //给容器中注册一个Bean;类型为返回值的类型，id默认是用方法名作为id
    @Bean("person")
    public Person person01() {
        return new Person("lisi", 20);
    }
 }
```



### @ComponentScan自动扫描

自动扫描`@Controller`、`@Service`、`@Repository`注解：

```java
@Configuration  //告诉Spring这是一个配置类
@ComponentScan("com.bp.spring.registry.*")
public class ComponentScanConfig {

}
```



#### exludeFilters

```java
@ComponentScan(value = "com.bp.spring.registry.*",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BookController.class)})
public class ComponentScanExcludeFilterConfig {
```



#### includeFilters

需要添加**useDefaultFilters = false**

```JAVA
@ComponentScan(value = "com.bp.spring.registry.*",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class, BookDao.class})}, useDefaultFilters = false)
public class ComponentScanIncludeFilterConfig {

}
```



#### 自定义Filters

编写自定义filter继承TypeFilter并重写match方法

```java
@Configuration  //告诉Spring这是一个配置类
@ComponentScan(value = "com.bp.spring.registry.*",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BookController.class)})
public class ComponentScanExcludeFilterConfig {

```

```java
public class MyTypeFilter implements TypeFilter {

    /**
     * metadataReader：读取到的当前正在扫描的类的信息
     * metadataReaderFactory:可以获取到其他任何类信息的
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
            throws IOException {
        // TODO Auto-generated method stub
        //获取当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类资源（类的路径）
        Resource resource = metadataReader.getResource();

        String className = classMetadata.getClassName();
        System.out.println("--->" + className);
        if (!className.contains("ller")) {
            return true;
        }
        return false;
    }
}
```



### @Conditional按条件创建Bean

只创建根据conditional满足条件的bean

```JAVA
@Configuration
public class ConditionalConfig {

    @Bean("bill")
    @Conditional(WindowsCondition.class)
    public Person person01() {
        return new Person("Bill Gates", 62);
    }

    @Conditional(LinuxCondition.class)
    @Bean("linus")
    public Person person02() {
        return new Person("linus", 48);
    }
}

public class WindowsCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment environment = context.getEnvironment();
		String property = environment.getProperty("os.name");
		if(property.contains("Windows")){
			return true;
		}
		return false;
	}
}


public class LinuxCondition implements Condition {

	/**
	 * ConditionContext：判断条件能使用的上下文（环境）
	 * AnnotatedTypeMetadata：注释信息
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// TODO是否linux系统
		//1、能获取到ioc使用的beanfactory
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		//2、获取类加载器
		ClassLoader classLoader = context.getClassLoader();
		//3、获取当前环境信息
		Environment environment = context.getEnvironment();
		//4、获取到bean定义的注册类
		BeanDefinitionRegistry registry = context.getRegistry();
		String property = environment.getProperty("os.name");
		//可以判断容器中的bean注册情况，也可以给容器中注册bean
		boolean definition = registry.containsBeanDefinition("person");
		if(property.contains("linux")){
			return true;
		}
		return false;
	}
}
```



## 2. @Import导入组件

### 直接导入

```JAVA
@Configuration  //告诉Spring这是一个配置类
@Import({Color.class, Red.class})
public class ImportConfig {

}
```



### Selector导入

实现ImportSelector的selectImports方法，返回全类路劲名

```JAVA
@Import({MyImportSelector.class})
public class ImportSelectorConfig {

}

public class MyImportSelector implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		// TODO Auto-generated method stub
		return new String[]{"com.bp.spring.registry.domain.Yellow"};
	}
}
```



### ImportBeanDefinitionRegistrar动态导入

实现ImportBeanDefinitionRegistrar，通过BeanDefinitionRegistry对bean进行动态导入

```JAVA
@Configuration  //告诉Spring这是一个配置类
@Import({Blue.class, Red.class, MyImportBeanDefinitionRegistrar.class})
public class ImportBeanDefinitionRegistrarConfig {

}

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		
		boolean definition1 = registry.containsBeanDefinition("com.bp.spring.registry.domain.Red");
		boolean definition2 = registry.containsBeanDefinition("com.bp.spring.registry.domain.Blue");
		if(definition1 && definition2){
			RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
			registry.registerBeanDefinition("rainBow", beanDefinition);
		}
	}

}
```



## 3. FactoryBean创建对象

```JAVA
@Configuration  //告诉Spring这是一个配置类
public class ConfigurationConfig {
   @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }
}

public class ColorFactoryBean implements FactoryBean<Color> {

    //返回一个Color对象，这个对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean...getObject...");
        return new Color();
    }
    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    //是单例？
    //true：这个bean是单实例，在容器中保存一份
    //false：多实例，每次获取都会创建一个新的bean；
    @Override
    public boolean isSingleton() {
        // TODO Auto-generated method stub
        return true;
    }
}



```

+  applicationContext.getBean("colorFactoryBean") 获取的是FactoryBean创建的对象
+ applicationContext.getBean("**&colorFactoryBean**")获取的是FactoryBean对象

```JAVA
public class RegistryTest{
    
    @Test
    public void testFactoryBean() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }

        Color color1  = (Color) applicationContext.getBean("colorFactoryBean");
        Color color2  = (Color) applicationContext.getBean("colorFactoryBean");

        ColorFactoryBean colorFactoryBean = (ColorFactoryBean) applicationContext.getBean("&colorFactoryBean");
        Color color3 = colorFactoryBean.getObject();
        System.out.println(colorFactoryBean.getClass());
        System.out.println(color1 == color2);
        System.out.println(color2 == color3);
    }
}
```





## 4. @Scope表明组件注册域

+ singleton是默认
+ prototype每次创建一个组件

```JAVA
@Configuration  //告诉Spring这是一个配置类
public class PrototypeBeanConfig {

    //给容器中注册一个Bean;类型为返回值的类型，id默认是用方法名作为id
    @Scope("prototype")
    @Bean("person")
    public Person person01() {
        return new Person("lisi", 20);
    }
}
```



## 5. @Lazy懒加载

第一个调用这个bean对象时才创建

```JAVA
@Configuration  //告诉Spring这是一个配置类
public class LazyBeanConfig {

    //给容器中注册一个Bean;类型为返回值的类型，id默认是用方法名作为id
    @Lazy
    @Bean("person")
    public Person person01() {
        return new Person("lisi", 20);
    }
}
```

