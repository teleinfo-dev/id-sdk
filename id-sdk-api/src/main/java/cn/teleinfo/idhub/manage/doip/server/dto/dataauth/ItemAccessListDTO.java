package cn.teleinfo.idhub.manage.doip.server.dto.dataauth;

import lombok.Data;

import java.util.List;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2024/5/15
 * @description
 */
@Data
public class ItemAccessListDTO {

    /**
     * 元数据属性集合
     */
    private String item;

    /**
     * 授权类型 1查看2编辑
     */
    private Integer authType;

    /**
     * 授权范围，1 - 公开；2 - 指定范围；当authType为2时，scope只能为2
     */
    private Integer scope;

    /**
     * 要授权的标识用户，当scope为1时，该值可以不填
     */
    private List<String> handleUsers;

    private List<String> removeHandleUsers;
}
