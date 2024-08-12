package cn.teleinfo.idhub.manage.doip.server.api.dataauth;

import cn.teleinfo.idhub.manage.doip.server.domain.DoipReturn;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 谢珍
 * @version 1.0
 * @date Created in 2024/5/15
 * @description 标识用户/用户组列表
 */
public interface HandleUserApi {
    @GetMapping("/api/v1/open/handle-user-group/list")
     DoipReturn getHandleUserGroupList();
}
