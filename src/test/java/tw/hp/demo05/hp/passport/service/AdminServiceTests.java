package tw.hp.demo05.hp.passport.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tw.hp.demo05.hp.passport.ex.ServiceException;
import tw.hp.demo05.hp.passport.pojo.dto.AdminAddNewDTO;
import tw.hp.demo05.hp.passport.pojo.vo.AdminListItemVO;

import java.util.List;

@Slf4j
@SpringBootTest
class AdminMapperTests {

    @Autowired
    IAdminService adminService;

    @Test
    void testAddNew() {
        AdminAddNewDTO adminAddNewDTO = new AdminAddNewDTO();
        adminAddNewDTO.setUsername("test_admin005");
        adminAddNewDTO.setPassword("123456");

        try {
            adminService.addNew(adminAddNewDTO);
            log.debug("添加成功!");
        } catch (ServiceException e) {
            log.debug("添加失敗,業務狀態碼: {}", e.getServiceCode());
            log.debug("添加失敗的原因 {}", e.getMessage());
        }
    }

    @Test
    void testList() {
        List<AdminListItemVO> list = adminService.list();
        log.debug("查詢完畢,列表數量: {}", list.size());
        for (AdminListItemVO vo : list) {
            log.debug("{}", vo);
        }

    }
}