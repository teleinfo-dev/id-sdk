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
public class ClassGrantPublicDTO {

    /**
     * 元数据属性集合
     */
    private List<String> items;
    
    /**
     * 授权范围，1 - 公开；2 - 指定范围
     */
    private Integer scope;

    /**
     * 元数据标识
     */
    private String metaHandle;
}
