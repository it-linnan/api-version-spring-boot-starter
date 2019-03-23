package priv.ln.api.version.spring.boot.autoconfigure;

import priv.ln.api.version.spring.boot.config.ApiVersionProperties;
import priv.ln.api.version.spring.boot.rule.http.ApiVersionUrlRequestCondition;
import priv.ln.api.version.spring.boot.support.http.ApiVersionUrlWebConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HTTP接口版本管理自动配置
 *
 * @author linnan
 * @version 2019/2/16
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnWebApplication
public class ApiVersionHttpConfiguration {
    @Bean
    public ApiVersionUrlWebConfig apiVersionUrlWebConfig() {
        return new ApiVersionUrlWebConfig();
    }

    @ConditionalOnMissingBean
    @Bean
    public ApiVersionProperties apiVersionProperties() {
        return new ApiVersionProperties();
    }

    @ConditionalOnMissingBean
    @Bean
    public ApiVersionUrlRequestCondition apiVersionUrlRequestCondition() {
        return new ApiVersionUrlRequestCondition();
    }
}
