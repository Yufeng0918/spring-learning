# 自动装配

## 1. @Autowire自动装配

```JAVA
@Controller
public class BookController {

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
}    
```



### @Qualifier选择Bean

```JAVA
@Service
@Getter
public class BookService {

    @Qualifier("bookDao1")
    @Autowired(required = false)
    private BookDao bookDao;
}

@Repository("bookDao1")
public class BookDao {

}    
```



### @Primary设置默认Bean

```JAVA
@ComponentScan
public class ConfigurationConfig {

    @Primary
    @Bean("bookDao2")
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();
        bookDao.label = 2;
        return bookDao;
    }
}
```

@Autowired自动注入

+ 默认优先按照类型去容器中找对应的组件:applicationContext.getBean(BookDao.class);找到就赋值
+ 如果找到多个相同类型的组件，**再将属性的名称作为组件的id去容器中查找** applicationContext.getBean("bookDao")

+ @Qualifier("bookDao"), **使用@Qualifier指定需要装配的组件的id**，而不是使用属性名
+ 自动装配默认一定要将属性赋值好，没有就会报错,  **可以使用@Autowired(required=false);**
+ **@Primary：让Spring进行自动装配的时候**，默认使用首选的bean；也可以继续使用@Qualifier指定需要装配的bean的名字
+ @Autowired可以放在setter和constructor上面



## 2. @Resource和@Inject自动装配

@Resource 和 @Inject 是java规范

**没有支持`@Primary`和`@Autowired(required = false)`**一样的功能

```JAVA
@Service
public class BookService {

    @Resource
    private TagDao tagDao;

    @Inject
    private SlotDao slotDao;
}

```



## 3. 实现Aware接口来获取底层组件

**ApplicationContextAware**:  获取容器对象

**BeanNameAware**： 获取当前Bean名字

**EmbeddedValueResolverAware**：获取解析字符串的resolver

```java
@Component
public class Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("传入的ioc："+applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("当前bean的名字："+name);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        // TODO Auto-generated method stub
        String resolveStringValue = resolver.resolveStringValue("你好 ${os.name} 我是 #{20*18}");
        System.out.println("解析的字符串："+resolveStringValue);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
```



## 4. @Profile实现环境切换

### 配置环境

```java
PropertySource("classpath:/dbconfig.properties")
@Configuration
public class ProfileConfig implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    private StringValueResolver valueResolver;

    private String  driverClass;


    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}")String pwd) throws Exception{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }


    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}")String pwd) throws Exception{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/oa");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${db.password}")String pwd) throws Exception{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/cloud");

        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.valueResolver = resolver;
        driverClass = valueResolver.resolveStringValue("${db.driverClass}");
    }
}
```

### 切换环境

命令行切换

```SHELL
-Dspring.profile.active=test
```

代码切换

```JAVA
AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
applicationContext.getEnvironment().setActiveProfiles("dev");
```



