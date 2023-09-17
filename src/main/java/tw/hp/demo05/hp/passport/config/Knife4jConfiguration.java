package tw.hp.demo05.hp.passport.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


//api文檔設定
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    /**
     * 【重要】指定Controller包路徑
     */
    private String basePackage = "tw.hp.demo05.hp.passport.controller";
    /**
     * 分組名稱
     */
    private String groupName = "product";
    /**
     * 主機名
     */
    private String host = "http://java.tedu.cn";
    /**
     * 標題
     */
    private String title = "HP商城在線API文檔--商品管理";
    /**
     * 簡介
     */
    private String description = "HP商城在線API文檔--商品管理";
    /**
     * 服務條款URL
     */
    private String termsOfServiceUrl = "http://www.apache.org/licenses/LICENSE-2.0";
    /**
     * 聯繫人
     */
    private String contactName = "Java教學研發部";
    /**
     * 聯繫網址
     */
    private String contactUrl = "http://java.tedu.cn";
    /**
     * 聯繫郵箱
     */
    private String contactEmail = "java@tedu.cn";
    /**
     * 版本號
     */
    private String version = "1.0.0";

    @Autowired
    private OpenApiExtensionResolver openApiExtensionResolver;

    @Bean
    public Docket docket() {
        String groupName = "1.0.0";
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .version(version)
                .build();
    }

}