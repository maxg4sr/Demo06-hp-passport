package tw.hp.demo05.hp.passport.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tw.hp.demo05.hp.passport.pojo.entity.Admin;
import tw.hp.demo05.hp.passport.pojo.vo.AdminListItemVO;
import tw.hp.demo05.hp.passport.pojo.vo.AdminLoginVO;

import java.util.List;

@Slf4j
@SpringBootTest
public class AdminMapperTests {

    @Autowired
    AdminMapper adminMapper;


    @Test
    void testInsert() {
        Admin admin = new Admin();
        admin.setUsername("test_admin001");
        admin.setPassword("123456");
        int rows = adminMapper.insert(admin);
        log.debug("受影響的行數: {}", rows);
    }

    @Test
    void testCountByUserName() {
        String username = "test_admin001";
        int count = adminMapper.countByUsername(username);
        log.debug("查詢結果: {}", count);
    }

    @Test
    void testList() {
        List<AdminListItemVO> list = adminMapper.list();
        log.debug("查詢完畢,列表數量: {}", list.size());
        for (AdminListItemVO vo : list) {
            log.debug("{}", vo);
        }
    }

    @Test
    void testFetByUsername() {
        String username = "root";
        AdminLoginVO adminLoginVO = adminMapper.getByUsername(username);
        log.debug("{}", adminLoginVO);
    }

    @Test
    public void testDeleteById() {
        Long id = 16L;
        int rows = adminMapper.deleteById(id);
        System.out.println("刪除的行數:" + rows);
    }



}
