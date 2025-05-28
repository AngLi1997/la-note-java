package com.liang.liangnote.controller;

import com.liang.liangnote.common.Resp;
import com.liang.liangnote.dto.UserSettingDTO;
import com.liang.liangnote.entity.UserSetting;
import com.liang.liangnote.service.UserSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户设置控制器
 * @author liang
 * @version 1.0.0
 * @date 2023/11/1
 */
@RestController
@RequestMapping("/api/user-settings")
@Api(tags = "用户设置接口")
public class UserSettingController {

    @Resource
    private UserSettingService userSettingService;

    /**
     * 获取用户设置信息
     *
     * @param userId 用户ID
     * @return 用户设置信息
     */
    @GetMapping("/{userId}")
    @ApiOperation(value = "获取用户设置信息", notes = "根据用户ID获取用户设置信息")
    public Resp<UserSettingDTO> getUserSettingInfo(@PathVariable String userId) {
        UserSettingDTO userSettingDTO = userSettingService.getUserSettingInfo(userId);
        return Resp.success(userSettingDTO);
    }

    /**
     * 保存或更新用户设置
     *
     * @param userSetting 用户设置信息
     * @return 操作结果
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存或更新用户设置", notes = "保存或更新用户设置信息")
    public Resp<Boolean> saveOrUpdateSetting(@RequestBody UserSetting userSetting) {
        boolean result = userSettingService.saveOrUpdateSetting(userSetting);
        return Resp.success(result);
    }
} 