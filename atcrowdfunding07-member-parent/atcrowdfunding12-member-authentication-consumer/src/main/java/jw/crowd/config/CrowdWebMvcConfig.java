package jw.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        // 浏览器访问的地址
        String urlPath = "/auth/member/to/reg/page.html";

        // 目标视图的名称，将来拼接“prefix: classpath:/templates/”、“suffix: .html”前后缀
        String viewName = "member-reg";

        //添加
        registry.addViewController(urlPath).setViewName(viewName);

    }

}