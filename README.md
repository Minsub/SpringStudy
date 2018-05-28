Spring Study
========================

### swagger + jersey2
* dependencies
  - compile('org.springframework.boot:spring-boot-starter-jersey')
  - compile('io.swagger:swagger-jersey2-jaxrs:1.5.10')

1. 세팅
* localhost:8080/static/swagger/index.html
 - https://github.com/swagger-api/swagger-ui 에서 dist폴더이 있는 파일을 받아 resource/static/swagger 복사
 - config/JerseyConfig.java 참조
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



### spring JPA + hikari pool
* dependencies
 - compile('org.springframework.boot:spring-boot-starter-jdbc')
 - compile('org.springframework.boot:spring-boot-starter-data-jpa')
 - compile('com.zaxxer:HikariCP:2.6.0')
 - compile('mysql:mysql-connector-java')


### cache
- TODO

### gradle
- TODO

### logback
- TODO

### spring pid file
- TODO



