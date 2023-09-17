package tw.hp.demo05.hp.passport.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

//此配置類為交由MyBatis統一管理mapper的類
@Configuration
@MapperScan("tw.hp.demo05.hp.passport.mapper") //掃瞄並管理mapper的註解,就不用每個註解加上@mapper
public class MyBatisConfiguration {
}
