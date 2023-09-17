package tw.hp.demo05.hp.passport.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.hp.demo05.hp.passport.security.LoginPrincipal;
import tw.hp.demo05.hp.passport.util.JwtUtils;
import tw.hp.demo05.hp.passport.web.JsonResult;
import tw.hp.demo05.hp.passport.web.ServiceCode;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * OncePerRequestFilter 確保在一次請求只通過一次filter
 * 此方法是為了兼容不同的web container，也就是說並不是所有的container都期望的只過濾一次，
 * servlet版本不同，執行過程也不同，因此，為了兼容各種不同運行環境和版本，默認filter繼承OncePerRequestFilter是一個比較穩妥的選擇。
 * 服務器端檢查JWT
 */
@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private void handleJwtException(HttpServletResponse response, int errorCode, String errorMessage, Throwable e) throws IOException {
        log.debug("解析JWT失敗，{}，{}", e.getClass().getName(), e.getMessage());
        JsonResult jsonResult = JsonResult.fail(errorCode, errorMessage);
        String jsonResultString = JSON.toJSONString(jsonResult);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().println(jsonResultString);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("執行JwtAuthorizationFilter過濾器....");

        // 清除Security的上下文
        // 不清除，只要此前存入過信息，即使後續不攜帶JWT，上下文中的登錄信息依然存在
        SecurityContextHolder.clearContext();

        // 從請求頭中獲取JWT
        String jwt = request.getHeader("Authorization");
        log.debug("從請求頭中獲取jwt數據：{}",jwt);

        // 先判斷是否獲取到有效的JWT數據，無JWT數據，直接放行(去驗證登錄等)
        // 不為null &&不為empty && 包含文本
        if(!StringUtils.hasText(jwt)){
            log.debug("請求頭中的JWT數據是無效的，直接放行");
            filterChain.doFilter(request,response);
            return;
        }

        // 如果獲取到了有效的JWT，則嘗試進行解析
        Claims claims = null;
        try{
            claims = JwtUtils.parse(jwt);
        }catch (ExpiredJwtException e){
//            log.debug("解析JWT失敗，JWT過期，{},{}",e.getClass().getName(),e.getMessage());
//            String errorMessage = "登錄信息已過期，請重新登錄！";
//            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_EXPIRED,errorMessage);
//            String jsonResultString = JSON.toJSONString(jsonResult);
//            response.setContentType("application/json; charset=utf-8");
//            response.getWriter().println(jsonResultString);
            handleJwtException(response, ServiceCode.ERR_JWT_EXPIRED, "登錄信息已過期，請重新登錄！", e);
            return;
        }catch (SignatureException e){
//            log.debug("解析JWT失敗，簽名錯誤，{},{}",e.getClass().getName(),e.getMessage());
//            String errorMessage = "獲取登錄信息失敗，請重新登錄！";
//            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_INVALID,errorMessage);
//            String jsonResultString = JSON.toJSONString(jsonResult);
//            response.setContentType("application/json; charset=utf-8");
//            response.getWriter().println(jsonResultString);
            handleJwtException(response, ServiceCode.ERR_JWT_INVALID, "獲取登錄信息失敗，請重新登錄！", e);
            return;
        }catch (MalformedJwtException e){
//            log.debug("解析JWT失敗，JWT數據有誤，{},{}",e.getClass().getName(),e.getMessage());
//            String errorMessage = "獲取登錄信息失敗，請重新登錄！";
//            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_INVALID,errorMessage);
//            String jsonResultString = JSON.toJSONString(jsonResult);
//            response.setContentType("application/json; charset=utf-8");
//            response.getWriter().println(jsonResultString);
            handleJwtException(response, ServiceCode.ERR_JWT_INVALID, "獲取登錄信息失敗，請重新登錄！", e);
            return;
        }catch (Throwable e){
//            log.debug("解析JWT失敗，錯誤詳情，{},{}",e.getClass().getName(),e.getMessage());
//            String errorMessage = "獲取登錄信息失敗，請重新登錄！";
//            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_JWT_INVALID,errorMessage);
//            String jsonResultString = JSON.toJSONString(jsonResult);
//            response.setContentType("application/json; charset=utf-8");
//            response.getWriter().println(jsonResultString);
            handleJwtException(response, ServiceCode.ERR_JWT_INVALID, "獲取登錄信息失敗，請重新登錄！", e);
            return;
        }

        Object id = claims.get("id");
        log.debug("從JWT中解析得到id：{}",id);
        Object username = claims.get("username");
        log.debug("從JWT中解析得到用戶名：{}",username);

        LoginPrincipal loginPrincipal = new LoginPrincipal();
        loginPrincipal.setId(Long.parseLong(id.toString()));
        loginPrincipal.setUsername(username.toString());

        // 用戶權限
        Object authoritiesString = claims.get("authorities");
        List<SimpleGrantedAuthority> authorities =
                JSON.parseArray(authoritiesString.toString(),SimpleGrantedAuthority.class);

        // 當解析成功後，應該講相關數據存入到 Spring Security 的上下文中
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginPrincipal,null,authorities);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // "放行"
        filterChain.doFilter(request,response);
    }
}