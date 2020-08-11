# 属性赋值

## 1. @Value赋值

```JAVA
public class Person {

    @Value("张三")
    private String name;
  
    @Value("#{20-2}")
    private Integer age;
  
}    
```





## 2. 属性文件赋值

部署属性文件

```properties
person.nickName=小李四
```

导入属性文件并赋值

```JAVA
@PropertySource(value = {"classpath:/person.properties"})
@Configuration
public class PropertyConfig {

    @Bean
    public Person person() {
        return new Person();
    }
}

public class Person {
   @Value("${person.nickName}")
    private String nickName;
}  
```

