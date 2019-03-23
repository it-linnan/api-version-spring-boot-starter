# 接口版本管理

## 自动配置

在pom中添加依赖

```xml
<dependency>
    <groupId>priv.ln</groupId>
    <artifactId>api-version-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 在controller上添加注解

```java
@ApiVersion("1")
@Controller
@RequestMapping("/xxx")
public class XxxController{
}
```

## 接口访问地址

/v1/xxx