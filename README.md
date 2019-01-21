Spring Study
========================

### swagger + jersey2
* dependencies
  - compile('org.springframework.boot:spring-boot-starter-jersey')
  - compile('io.swagger:swagger-jersey2-jaxrs:1.5.10')

1. 세팅
* localhost:8080/static/swagger/index.html
 - https://github.com/swagger-api/swagger-ui 에서 dist폴더이 있는 파일을 받아 resource/static/swagger 복사
 - [config/JerseyConfig.java](https://github.com/Minsub/SpringStudy/blob/develop/src/main/java/com/kakao/minsub/spring/config/JerseyConfig.java) 참조
 - resourceHandler 등록
```java
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
```

 - controller에 annotation을 지정

```java
@Path("/user")
@Api("User")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    @GET
    @Path("/{username}")
    @ApiOperation("유저 정보 확인")
    public User show(@PathParam("username") final String username) {
        ...
    }
}
```

2. 오류 response 핸들링
 - ExceptionMapper로 exception 에 대해 정해진 response를 만들 수 있음
 - ExceptionMapper를 상속받아 toResponse를 구현한다. 이때 각 Custom Exception 마다 mapper를 따로 지정해서 제어할 수 있음
```java
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        ErrorResponse body = new ErrorResponse();
        body.code = Optional.ofNullable(status).orElse(Status.INTERNAL_SERVER_ERROR).getStatusCode();
        body.message = ExceptionUtils.getStackTrace(e);
        return Response.status(status).entity(body).build();
    }
}
```

 - JerseyConfig에 만들어놓은 ExceptionMapper를 register한다
```java
register(DefaultExceptionMapper.class);
```

3. http request / response filter 설정
 - ContainerRequestFilter, ContainerResponseFilter 를 상속받는 클래스를 만듬
 - Jersey.java (ResourceConfig)에서 register를 함
 - custom Annotation을 만들어 request들어오는 Controller에서 선언한 후 filter에서 해당 annotation인지 체크해서 filtering 함
 - [config/jersey/filter](https://github.com/Minsub/SpringStudy/tree/master/src/main/java/com/kakao/minsub/spring/config/jersey/filter) 참조




### spring JPA + hikari pool
* dependencies
 - compile('org.springframework.boot:spring-boot-starter-jdbc')
 - compile('org.springframework.boot:spring-boot-starter-data-jpa')
 - compile('com.zaxxer:HikariCP:2.6.0')
 - compile('mysql:mysql-connector-java')
 - compile('org.hibernate:hibernate-validator:5.2.4.Final')

1. 세팅
 - [config/db/LocalMysqlConfig.java](https://github.com/Minsub/SpringStudy/blob/develop/src/main/java/com/kakao/minsub/spring/config/db/LocalMysqlConfig.java) 참조
 - model 클래스에 @Entity, @table(name="{table_name}")을 명시
 - Repository를 상속받은 repository 인터페이스를 만든다. (CrudRepository 를 상속받으면 기본적은 메시드가 명시되어있음)

2. Hibernate Validator
 - entity의 validator 설정을 쉽게 할 수 있음
 - entity의 컬럼에 @NotNull, @Length, @Email 등의 annotation을 통해 validation을 명시할 수 있음
```java
// ...
public class Profile {
@NotNull(message = "필수 입력 항목입니다.")
@NotBlank(message = "{validator.constraints.Profile.name.blank}", groups = First.class)
@Length(min = 5, max = 255, message = "{validator.constraints.Profile.name.length}", groups = Second.class)
@Email(groups = Third.class)
private String name;
// ...
```

 - validator와 messageSource의 Bean을 생성
 - messageSource는 에러 메세지에 대한 내용을 설정할 수 있는데 resource 하위에 설정할 수 있고 {messageSourceName}_{locale}구조로 request의 언어 설정에 따라 각각 설정이 가능함
```java
@Configuration
public class MessageSourceConfig {
    @Bean
    @Primary
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}

@Configuration
public class ValidationConfig {
    @Autowired
    private MessageSource messageSource;

    @Bean
    public MessageInterpolator messageInterpolator(){
        return new LocaleContextMessageInterpolator(new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(messageSource)));
    }
    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(messageInterpolator());
        return validator;
    }
}
```

 - validator로 체크할 로직
```java
Set<ConstraintViolation<Profile>> violations = validator.validate(profile, FullValidation.class);
if (!violations.isEmpty()) {
    logger.error(violations.toString());
    throw new ConstraintViolationException(violations);
}
```

 - ConstraintViolationException 을 Jersey의 ExceptionMapper로 받아서 처리하면 validatior의 메시지를 reponse로 쉽게 전달 가능


3. JPA + redis cache
 - 간단한 설정은 application.yml을 통해 할 수 있다
```yaml
  cache:
    type: redis
  redis:
    host: jiminsub.asuscomm.com
    port: 6379
    password: wlalstjq1
```
 - 하지만 디테일할 설정이 불가능하기 때문에 java파일로 설정을 한다. [config/CacheConfig.java](https://github.com/Minsub/SpringStudy/blob/develop/src/main/java/com/kakao/minsub/spring/config/CacheConfig.java) 참조
 - CacheManager을 application.yml에서 기본적으로 만들어주는 구조다.
 - repository에서 annotation을 통해 cacheName과 key를 지정하면 알아서 cache가 있으면 redis, 없다면 DB에서 읽게 된다.
```java
@CacheConfig(cacheNames="post")
public interface PostRepository extends CrudRepository<Post, Integer> {
    @Cacheable(key="#p0")
    Post findOne(Integer id);

    @CacheEvict(key="#p0")
    default void evictCache(Integer id) {};
}
```

### gradle
- TODO

### logback
- TODO

### spring pid file
- TODO


### Spring cloud
- config-server를 단독으로 띄어야함
- @EnableConfigServer를 추가하고 application.yml에 git을 추가
```yml
server:
  port: 8888

spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/Minsub/SpringStudy-config.git
```

PR_TEST COMMIT LOG
TEST