package tw.hp.demo05.hp.passport.service;

import tw.hp.demo05.hp.passport.pojo.dto.AdminAddNewDTO;
import tw.hp.demo05.hp.passport.pojo.dto.AdminLoginDTO;
import tw.hp.demo05.hp.passport.pojo.vo.AdminListItemVO;

import java.util.List;

/**
 * 管理員接口業務
 */
public interface IAdminService {
    /**
     * 添加管理員
     *
     * @param adminAddNewDTO 管理員的數據
     */
    void addNew(AdminAddNewDTO adminAddNewDTO);

    /**
     * 查詢管理員列表
     *
     * @return 管理員列表
     */
    List<AdminListItemVO> list();

    /**
     * 管理員登錄
     *
     * @param adminLoginDTO 管理員的數據
     * @return JWT數據
     */
    String login(AdminLoginDTO adminLoginDTO);


    /**
     * 刪除管理員
     *
     * @param adminId 管理員的數據
     * @return JWT數據
     */
    void deleteById(Long adminId);



}