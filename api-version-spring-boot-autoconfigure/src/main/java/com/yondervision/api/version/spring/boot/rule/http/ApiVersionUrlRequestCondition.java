package priv.ln.api.version.spring.boot.rule.http;

import priv.ln.api.version.spring.boot.config.ApiVersionProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口URL版本匹配条件
 *
 * @author linnan
 * @version 2019/2/16
 */
@NoArgsConstructor
public class ApiVersionUrlRequestCondition implements RequestCondition<ApiVersionUrlRequestCondition>, ApplicationContextAware {

    @Getter
    private String version;

    private static String regexFormat = "";

    public ApiVersionUrlRequestCondition(String version) {
        this.version = version;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (regexFormat == null || regexFormat.length() == 0) {
            ApiVersionProperties apiVersionProperties = applicationContext.getBean(ApiVersionProperties.class);
            ServerProperties serverProperties = applicationContext.getBean(ServerProperties.class);
            if (serverProperties.getServlet().getContextPath() != null) {
                regexFormat = serverProperties.getServlet().getContextPath();
            }
            regexFormat += "/" + apiVersionProperties.getPrefix() + "%s/**";
        }
    }

    @Override
    public ApiVersionUrlRequestCondition combine(ApiVersionUrlRequestCondition other) {
        return new ApiVersionUrlRequestCondition(other.getVersion());
    }

    @Override
    public ApiVersionUrlRequestCondition getMatchingCondition(HttpServletRequest request) {
        PathMatcher pathMatcher = new AntPathMatcher();
        boolean match = pathMatcher.match(String.format(regexFormat, version), request.getRequestURI());
        return match ? this : null;
    }

    @Override
    public int compareTo(ApiVersionUrlRequestCondition other, HttpServletRequest request) {
        return other.getVersion().compareTo(this.version);
    }
}
