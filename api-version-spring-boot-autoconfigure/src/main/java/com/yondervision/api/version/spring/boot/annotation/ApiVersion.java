package priv.ln.api.version.spring.boot.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * api版本号注解，用于接口版本管理
 *
 * @author linnan
 * @version 2019/2/16
 * @see
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    /**
     * 版本号
     *
     * @return 版本号
     */
    @AliasFor("value")
    String version() default "";

    /**
     * 版本号
     *
     * @return 版本号
     */
    @AliasFor("version")
    String value() default "";
}
