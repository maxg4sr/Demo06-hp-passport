package tw.hp.demo05.hp.passport.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tw.hp.demo05.hp.passport.pojo.entity.Admin;
import tw.hp.demo05.hp.passport.pojo.vo.AdminListItemVO;
import tw.hp.demo05.hp.passport.pojo.vo.AdminLoginVO;

import java.util.List;

/**
 * 管理員接口
 */
@Repository
public interface AdminMapper {

    /**
     * 插入管理員數據
     *
     * @param admin 管理員數據
     * @return 受影響的行數
     */
    int insert(Admin admin);

    /**
     * 根據管理用戶名統計此用戶名對應的管理員數據的數量
     *
     * @param username 管理員名稱
     * @return 此名稱對應的管理員數據的數量
     */
    int countByUsername(String username);

    /**
     * 查詢管理員列表
     *
     * @return 管理員列表
     */
    List<AdminListItemVO> list();

    /**
     * 根據管理員用戶名查詢管理員的登錄相關信息
     *
     * @param username 管理員用戶名
     * @return 管理員的登錄相關信息，沒有匹配的數據返回null
     */
    AdminLoginVO getByUsername(String username);

    int deleteById(Long id);

    /**
     * 根據管理員ID查詢管理員
     *
     * @param id 管理員ID
     * @return 管理員信息，沒有匹配的數據返回null
     */
    AdminListItemVO getById(Long id);

    int update(Admin admin);

    int updateEnableState(Admin admin);
}
