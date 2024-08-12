package cn.teleinfo.idhub.manage.doip.server.dto.instance;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2024/4/22
 * @description 标识注册 && 标识修改 属性值
 */ 
@Data
public class HandleAttributesDTO {
    
    /**
     * 属性与属性值集合
     */
    private Map<String,Object> content;

}
