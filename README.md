Spring Study
========================

### swagger + jersey2
* dependencies
  - compile('org.springframework.boot:spring-boot-starter-jersey')
  - compile('io.swagger:swagger-jersey2-jaxrs:1.5.10')

* localhost:8080/static/swagger/index.html
1. https://github.com/swagger-api/swagger-ui 에서 dist폴더이 있는 파일을 받아 resource/static/swagger 복사
2. config/JerseyConfig.java 참조
3. resourceHandler 등록
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

4. controller에 annotation을 지정

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

### cache
- TODO

### gradle
- TODO

### logback
- TODO

### spring pid file
- TODO

### spring JPA
- TODO

