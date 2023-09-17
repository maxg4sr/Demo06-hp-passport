package tw.hp.demo05.hp.passport.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tw.hp.demo05.hp.passport.filter.JwtAuthorizationFilter;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//開啟全局認證參數
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        log.debug("創建密碼編碼器組件...BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); // 禁用防止跨域訪問。未配置，白名單路徑的異步訪問會出現403錯誤

        // 允許通過客戶端的OPTIONS類型的請求 preflight
        http.cors();

        // 白名單，不需要登錄就可以訪問
        String[] urls = {
                "/websocket/{nickname}",
                "/submitResume",
                "/reg",
                "/submitForm",
                "/admins/login",
                "/doc.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources",
                "/v2/api-docs",
                "favicon.ico"
        };

        // 使用 /* 匹配1層級路徑：/brands,/categories,不可以匹配多層級路徑:/brands/list
        // 使用 /** 匹配若干層級路徑
        http.authorizeRequests() // 請求需要被授權才可以訪問
                .antMatchers(urls) // 匹配某些路徑
                .permitAll()      // 允許直接訪問
                .anyRequest()       // 其他請求
                .authenticated();   // 通過認證才能訪問

        // 添加處理JWT的過濾器，必須執行在處理用戶名、密碼過了執行之前，表示驗證成功後無需登錄
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }



}