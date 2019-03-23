package priv.ln.api.version.spring.boot.support.http;

import priv.ln.api.version.spring.boot.rule.http.ApiVersionUrlRequestMappingHandlerMapping;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 注册接口URL版本路由管理类
 *
 * @author linnan
 * @version 2019/2/16
 */
public class ApiVersionUrlWebConfig implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionUrlRequestMappingHandlerMapping();
    }
}
