package cn.teleinfo.idhub.manage.doip.server.vo.handleuser;

import lombok.Data;

/**
 * @author 谢珍
 * @version 1.0
 * @date Created in 2024/5/8
 * @description 获取标识用户/用户组列表接口 VO
 */
@Data
public class HandleUserGroupVO {
    public String name;
    public String userHandle;
    public Integer type;
    public String belongCompany;
}
