package priv.ln.api.version.spring.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 接口版本管理属性
 *
 * @author linnan
 * @version 2019/2/16
 */
@Data
@ConfigurationProperties(prefix = "api.version")
public class ApiVersionProperties {
    /**
     * 全局版本号前缀
     */
    private String prefix = "v";
}
