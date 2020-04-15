# Spring Cloud

## 1.Spring Cloud Overview
- micro service tech stack
- Eureka，Zuul in maintenance mode，Nacos including service registration and config
***

## 2. Project Structure
```groovy
subprojects {
    apply plugin: 'java'
    apply plugin: 'org.springframework.boot'
    apply plugin:'io.spring.dependency-management'

    sourceCompatibility = 1.8
    targetCompatibility = 1.8

    [compileJava,compileTestJava,javadoc]*.options*.encoding = 'UTF-8'
    repositories {
        mavenLocal()
        mavenCentral()
    }
    ext {
            springBootVersion = '2.2.6.RELEASE'
            springCloudAlibabaVersion = '2.1.0.RELEASE'
            springCloudVersion = 'Honton.SR1'
    }

    dependencies {
        implementation "org.springframework.boot:spring-boot-starter-web:$springBootVersion"
        implementation "org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion"
        implementation "org.springframework.cloud:spring-cloud-alibaba-dependencies:$springCloudAlibabaVersion"
        annotationProcessor "org.springframework.boot:spring-boot-configuration-processor"
        implementation "org.mybatis.spring.boot:mybatis-spring-boot-starter:2.0.0"
        implementation "com.alibaba:druid:1.1.16"
        runtime "mysql:mysql-connector-java"
        implementation "log4j:log4j:1.2.17"
        testCompile "junit:junit:4.12"
        testCompile "org.springframework.boot:spring-boot-starter-test:$springBootVersion"
        compileOnly 'org.projectlombok:lombok'
        annotationProcessor 'org.projectlombok:lombok'
    }
}
```
***

## 3. RestTemplate
- spring wrapper http client to invoke remote restful
- passing url, parameter and return type
- configure RestTemplate
```java
@Configuration
public class ApplicationContextConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
```
- call RestTemplate for http call
```java
public class OrderController {
    public static final String PAYMENT_URL = "http://localhost:8001";
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }
}
```
***


## 4. Common 
- abstract common code
- enable jar and maven
- reuse maven local repo to install to local
```groovy
plugins {
    id 'java'
    id 'maven'
}
jar.enabled = true

repositories {
    mavenLocal()
    mavenCentral()
}
```
- other project import module
```groovy
dependencies {
    implementation "com.bp:cloud-api-common:1.0"
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```


## 5. Service Registration
#### Eureka
- client-server architecture with heart beat
- service provider cluster register to eureka server cluster
- service consumer get registry from eureka server cluster
- eureka server: provide registration service
- eureka client: register service, heart beat period is 30s, de-register client if no heart beat
- server
    + configuration in buidl.gradle and application.yml
    + enable in main application
```groovy
implementation "org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:2.2.1.RELEASE"
```
```yaml
eureka:
  instance:
    hostname: localhost
  client:
    # do not register self
    register-with-eureka: false
    # declare as register
    fetch-registry: false
    # url to qurey to lookup service
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka
```
```java
@EnableEurekaServer
public class EurekaMain7001 { }
```
- client
    + configuration, spring.application.name is service in registry
    + enable in main application
```groovy
implementation "org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:2.2.1.RELEASE"
```
```yaml
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:7001/eureka
```
```java
@EnableEurekaClient
public class PaymentMain8001 { }
```
- cluster
    + eureka server register with each other
    + eureka client register to multiple eureka server
        + enable @LoadBalanced for RestTemplate
        + change the hostname to service name
```yaml
eureka:
  instance:
    hostname: eureka7001.com
  client:
    service-url:
      defaultZone: http://eureka7002.com:7002/eureka
```
```yaml
eureka:
  instance:
    hostname: eureka7002.com
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka
```
```yaml
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
```
```java
@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }
}
```
- configure host and ip
```yaml
eureka:
    instance:
        instance-id: payment8001
        prefer-ip-address: true
```
- enable discovery client and discovery client get service info from eureka server
```java
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> serviceList = discoveryClient.getServices();
        serviceList.forEach(s -> log.info("service: " + s));

        List<ServiceInstance> instanceList = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        instanceList.forEach(i -> log.info(i.getServiceId() + "\t" + i.getHost() + "\t" + i.getPort() + "\t" + i.getUri()));
        return this.discoveryClient;
    }
}

@EnableDiscoveryClient
public class PaymentMain8001 { }
```
- self protection
    + if some service is unavailable, eureka will will keep in info, AP
    + configure in server and client
```yaml
eureka:
  server:
    # disable self-preservation
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 2000
```
```yaml
eureka:
  instance:
    instance-id: payment8001
    prefer-ip-address: true
    # heat beat period (default is 30)
    lease-renewal-interval-in-seconds: 5
    # expiration after last success heart beat
    lease-expiration-duration-in-seconds: 10
```