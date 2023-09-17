package tw.hp.demo05.hp.passport.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 跨域的寫法 用於前後端的連接
     *
     * @param registry
     */
    // 跨域訪問
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 所有地址都可以訪問(也可以配置具體地址)
                .allowedOriginPatterns("*")       // 項目中的所有接口都支持跨域
                .allowedHeaders("*")              // 允許所有的請求頭
                .allowedMethods("*")              // 允許所有的請求方式：GET/POST/PUT/DELETE...
                .allowCredentials(true);          // 允許請求帶有驗證信息
    }

}