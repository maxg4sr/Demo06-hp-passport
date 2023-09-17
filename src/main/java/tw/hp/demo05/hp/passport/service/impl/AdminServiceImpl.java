package tw.hp.demo05.hp.passport.service.impl;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.hp.demo05.hp.passport.ex.ServiceException;
import tw.hp.demo05.hp.passport.mapper.AdminMapper;
import tw.hp.demo05.hp.passport.mapper.AdminRoleMapper;
import tw.hp.demo05.hp.passport.pojo.dto.AdminAddNewDTO;
import tw.hp.demo05.hp.passport.pojo.dto.AdminLoginDTO;
import tw.hp.demo05.hp.passport.pojo.entity.Admin;
import tw.hp.demo05.hp.passport.pojo.entity.AdminRole;
import tw.hp.demo05.hp.passport.pojo.vo.AdminListItemVO;
import tw.hp.demo05.hp.passport.security.AdminDetails;
import tw.hp.demo05.hp.passport.service.IAdminService;
import tw.hp.demo05.hp.passport.util.JwtUtils;
import tw.hp.demo05.hp.passport.web.ServiceCode;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//業務層代碼
@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public void addNew(AdminAddNewDTO adminAddNewDTO) {
        log.debug("開始處理添加管理員業務，參數:{}",adminAddNewDTO);

        // 用戶名是否被佔用
        int count = adminMapper.countByUsername(adminAddNewDTO.getUsername());
        if(count > 0){
            String message = "添加管理員失敗，用戶名:【"+adminAddNewDTO.getUsername()+"】被佔用！";
            // 拋出異常
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);
        }

        // 創建實體對象
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminAddNewDTO,admin);
        admin.setLoginCount(0);
        admin.setLastLoginIp(null);
        admin.setGmtLastLogin(null);

        // 將原密碼從Admin對像中取出，加密後在存入到Admin對像中
        String rawPassword = admin.getPassword();
        String encodePassword = passwordEncoder.encode(rawPassword);
        admin.setPassword(encodePassword);

        // 將管理員信息寫入到數據庫
        int rows = adminMapper.insert(admin);
        if(rows != 1){
            String message = "添加管理員失敗，服務器忙，請稍後重試！【錯誤碼：1】";
            // 拋出異常
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }

        // 插入管理員與角色的管理數據，使得以上添加管理員是被分配了角色(暫定2號角色)的
        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(admin.getId());
        adminRole.setRoleId(2L); // 暫定（鎖定2號角色）

        rows = adminRoleMapper.insert(adminRole);
        if(rows != 1){
            String message = "添加管理員失敗，服務器忙，請稍後重試！【錯誤碼：2】";
            // 拋出異常
            throw new ServiceException(ServiceCode.ERR_INSERT,message);
        }

    }

    @Override
    public List<AdminListItemVO> list() {
        log.debug("開始處理查詢管理員列表的業務");
        return adminMapper.list();
    }


    @Override
    public String login(AdminLoginDTO adminLoginDTO) {

        //調用 security 驗證機制
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(adminLoginDTO.getUsername(),adminLoginDTO.getPassword());

        Authentication loginResult = authenticationManager.authenticate(authentication);

        // 以上調用authenticate()方法實惠拋出異常，如果還能執行到此處，表示用戶名與密碼是匹配的
        // 查看返回結果相關信息，類名與數據
        log.debug("登錄成功！認證方法返回的{}>>>{}",loginResult.getClass().getName(),loginResult);

        // 從認證結果中Principal，本質是User類型，是UserDetailsService中loadUserByUsername()的返回結果
        log.debug("嘗試獲取Principal:{}>>>{}",loginResult.getPrincipal().getClass().getName(),loginResult.getPrincipal());

        // 獲取到登錄成功的用戶名
        AdminDetails adminDetails = (AdminDetails)loginResult.getPrincipal();

        Long id = adminDetails.getId();
        log.debug("登錄成功的管理員id：{}",id);

        String username = adminDetails.getUsername();
        log.debug("登錄成功的用戶名：{}",username);
        Collection<GrantedAuthority> authorities = adminDetails.getAuthorities();
        log.debug("登錄成功的管理員權限：{}",authorities);
        String authoritiesString = JSON.toJSONString(authorities);
        log.debug("權限轉換為JSON：{}",authoritiesString);

        // 需要封裝到JWT中的數據
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",id);
        claims.put("username",username);
        claims.put("authorities",authoritiesString);

        // 應該在此處生成JWT，向JWT中存入:id(暫無),username,權限(暫無)
        String jwt = JwtUtils.generate(claims);

        log.debug("生成的JWT數據:{}",jwt);
        return jwt;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("開始刪除管理員業務，id={}", id);

        // 根據id查詢數據
        AdminListItemVO admin = adminMapper.getById(id);
        if (admin == null) {
            String message = "刪除數據失敗，刪除的數據(id=" + id + ")不存在";
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        // 調用mapper層方法
        int rows = adminMapper.deleteById(id);
        if (rows != 1) {
            String message = "刪除數據失敗，服務器繁忙，請稍後重試";
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }


}

//    public void deleteById(Long id) {
//        log.debug("開始刪除品牌業務，id={}", id);
//
//        // 根據id查詢數據
//        AdminListItemVO admin = adminMapper.list(id);
//        if (admin == null) {
//            String message = "刪除數據失敗，刪除的數據(id=" + id + ")不存在";
//            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
//        }
//
//        // 調用mapper層方法
//        int rows = adminMapper.deleteById(id);
//        if (rows != 1) {
//            String message = "刪除數據失敗，服務器繁忙，請稍後重試";
//            throw new ServiceException(ServiceCode.ERR_DELETE, message);
//        }
//    }