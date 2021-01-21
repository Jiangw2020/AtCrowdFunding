package jw.crowd.mvc.config;

import jw.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
// 启用 Web 环境下权限控制功能
@EnableWebSecurity
// 启用全局方法权限控制功能，并且设置 prePostEnabled = true。保证@PreAuthority、 @PostAuthority、@PreFilter、@PostFilter 生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    CrowdUserDetailsService userDetailsService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        // 与 SpringSecurity 环境下用户登录相关
//        builder.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
        builder.userDetailsService(userDetailsService)
               .passwordEncoder(passwordEncoder);
        ;
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        // 与 SpringSecurity 环境下请求授权相关
        security
            .authorizeRequests()// 对请求进行授权
            .antMatchers("/admin/to/login/page.html")// 针对登录页进行设置
            .permitAll() // 无条件访问
            .antMatchers("/bootstrap/**") // 针对静态资源进行设置，无条件访问
            .permitAll() // 针对静态资源进行设置，无条件访问
            .antMatchers("/crowd/**") // 针对静态资源进行设置，无条件访问
            .permitAll() // 针对静态资源进行设置，无条件访问
            .antMatchers("/css/**") // 针对静态资源进行设置，无条件访问
            .permitAll() // 针对静态资源进行设置，无条件访问
            .antMatchers("/fonts/**") // 针对静态资源进行设置，无条件访问
            .permitAll()// 针对静态资源进行设置，无条件访问
            .antMatchers("/img/**") // 针对静态资源进行设置，无条件访问
            .permitAll() // 针对静态资源进行设置，无条件访问
            .antMatchers("/jquery/**") // 针对静态资源进行设置，无条件访问
            .permitAll() // 针对静态资源进行设置，无条件访问
            .antMatchers("/layer/**") // 针对静态资源进行设置，无条件访问
            .permitAll() // 针对静态资源进行设置，无条件访问
            .antMatchers("/script/**")// 针对静态资源进行设置，无条件访问
            .permitAll() // 针对静态资源进行设置，无条件访问
            .antMatchers("/ztree/**") // 针对静态资源进行设置，无条件访问
            .permitAll()
            .antMatchers("/admin/get/page.html")// 针对分页显示 Admin 数据设定访问控制
//            .hasRole("经理") // 要求具备经理角色
                .access("hasRole('经理') or hasAuthority('user:get')" )
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new AccessDeniedHandler() {
                @Override
                public void handle(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        AccessDeniedException accessDeniedException) throws IOException, ServletException {
                    request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                    request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);}
            })
            .and()
            .csrf() // 防跨站请求伪造功能
            .disable() // 禁用
            .formLogin() // 开启表单登录的功能
            .loginPage("/admin/to/login/page.html") // 指定登录页面
            .loginProcessingUrl("/security/do/login.html") // 指定处理登录请求的地址
            .defaultSuccessUrl("/admin/to/main/page.html",true)// 指定登录成功后前往的地址
            .usernameParameter("loginAcct") // 账号的请求参数名称
            .passwordParameter("userPswd") // 密码的请求参数名称
            .and()
            .logout() // 开启退出登录功能
            .logoutUrl("/seucrity/do/logout.html") // 指定退出登录地址
            .logoutSuccessUrl("/admin/to/login/page.html") // 指定退出成功以后前往的地址
        ;
    }
}
