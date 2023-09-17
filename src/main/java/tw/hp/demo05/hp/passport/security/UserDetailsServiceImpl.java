package tw.hp.demo05.hp.passport.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tw.hp.demo05.hp.passport.mapper.AdminMapper;
import tw.hp.demo05.hp.passport.pojo.vo.AdminLoginVO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Spring Security自動根據用戶名【{}】查詢用戶詳情",username);

        AdminLoginVO adminLoginVO = adminMapper.getByUsername(username);

        // 從數據庫中已經讀取相關數據
        if(adminLoginVO != null){

            List<String> permissions = adminLoginVO.getPermissions();
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for(String permission : permissions){
                authorities.add(new SimpleGrantedAuthority(permission));
            }

            AdminDetails adminDetails = new AdminDetails(
                    adminLoginVO.getUsername(),
                    adminLoginVO.getPassword(),
                    adminLoginVO.getEnable() == 1,
                    authorities
            );
            adminDetails.setId(adminLoginVO.getId());

            return adminDetails;
        }

        return null;
    }

}