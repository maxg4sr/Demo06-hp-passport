package tw.hp.demo05.hp.passport.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tw.hp.demo05.hp.passport.pojo.entity.AdminRole;

@Slf4j
@SpringBootTest
public class AdminRoleMapperTests {

    @Autowired
    AdminRoleMapper adminRoleMapper;

    @Test
    public void testInsert() {
        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(10L);
        adminRole.setRoleId(20L);

        int insert = adminRoleMapper.insert(adminRole);
        log.debug("測試: {}", insert);


    }
}
