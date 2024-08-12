package cn.teleinfo.idhub.manage.doip.server.dto.instance;

import lombok.Data;


/**
 * @author wsc
 * @version 1.0
 * @date Created in 2024/4/22
 * @description 标识注册 && 标识修改模型
 */
@Data
public class HandleInputDTO {

    /**
     * 请求唯一Id
     */
    private String requestId;

    /**
     * 客户端Id
     */
    private String clientId;

    /**
     * 操作的标识
     */
    private String targetId;

    /**
     * 操作类型
     */
    private String operationId;

    /**
     * 所属应用，若不添加该参数，则该注册标识无法进行应用分类统计
     */
    private String appHandle;

    /**
     * 元数据标识
     */
    private String type;

    /**
     * 属性值集合
     */
    private HandleAttributesDTO attributes;
    
}
