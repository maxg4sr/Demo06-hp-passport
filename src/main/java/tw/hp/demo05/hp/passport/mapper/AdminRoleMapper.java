package tw.hp.demo05.hp.passport.mapper;

import org.springframework.stereotype.Repository;
import tw.hp.demo05.hp.passport.pojo.entity.AdminRole;

@Repository
public interface AdminRoleMapper {
    /**
     * 插入管理員與角色關聯數據
     * @param adminRole  管理員與角色關聯數據
     * @return 受影響的行數 成功插入數據是，將返回1
     */
    int insert(AdminRole adminRole);
}