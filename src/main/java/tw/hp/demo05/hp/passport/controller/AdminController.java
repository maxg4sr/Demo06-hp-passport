package tw.hp.demo05.hp.passport.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tw.hp.demo05.hp.passport.pojo.dto.AdminAddNewDTO;
import tw.hp.demo05.hp.passport.pojo.dto.AdminLoginDTO;
import tw.hp.demo05.hp.passport.pojo.vo.AdminListItemVO;
import tw.hp.demo05.hp.passport.security.LoginPrincipal;
import tw.hp.demo05.hp.passport.service.IAdminService;
import tw.hp.demo05.hp.passport.web.JsonResult;

import java.util.List;

/**
 * 管理員的控制器
 */
@Slf4j
@Api(tags = "1.管理員模塊")
@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation("添加管理員")
    @ApiOperationSupport(order = 100)
    @PreAuthorize("hasAuthority('/ams/admin/update')")
    @PostMapping("/add-new")
    public JsonResult addNew(@RequestBody AdminAddNewDTO adminAddNewDTO){
        adminService.addNew(adminAddNewDTO);
        return JsonResult.ok();
    }

    @ApiOperation("管理員列表")
    @ApiOperationSupport(order = 200)
    @PreAuthorize("hasAuthority('/ams/admin/read')")
    @GetMapping
    public JsonResult list(@AuthenticationPrincipal LoginPrincipal principal){

        log.debug("當前的認證信息中的當事人信息：{}",principal);
        Long id = principal.getId();
        String username = principal.getUsername();
        log.debug("從認證信息中獲取當前登錄的管理員的id：{}，與用戶名：{}",id,username);

        List<AdminListItemVO> admins = adminService.list();
        return JsonResult.ok(admins);
    }

    @ApiOperation("管理員登錄")
    @ApiOperationSupport(order = 300)
    @PostMapping("/login")
    public JsonResult login(@RequestBody AdminLoginDTO adminLoginDTO){

        // 需要調用service組件處理登錄
        String jwt = adminService.login(adminLoginDTO);

        return JsonResult.ok(jwt);
    }

    @ApiOperation("刪除管理員")
    @ApiOperationSupport(order = 400)
    @PreAuthorize("hasAuthority('/ams/admin/delete')")
    @DeleteMapping("/{id}/delete")
    public JsonResult deleteById(@PathVariable Long id){
        adminService.deleteById(id);
        return JsonResult.ok();
    }



}