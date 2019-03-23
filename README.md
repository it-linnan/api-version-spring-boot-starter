# 接口版本管理

## 自动配置

在pom中添加依赖

```xml
<dependency>
    <groupId>priv.ln</groupId>
    <artifactId>api-version-spring-boot-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

## 在controller上添加注解

```java
@ApiVersion("1")
@Controller
public class XxxController{
}
```

## 接口访问地址

/v1/xxx