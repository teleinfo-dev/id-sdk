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
public class ClassGrantDTO {
    
    /**
     * 元数据标识
     */
    private String metaHandle;
    
    private List<ItemAccessListDTO> accessList;
}
