package cn.teleinfo.idhub.manage.doip.server.dto.meta;

import lombok.Data;

import java.util.List;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2022/12/15
 * @description
 */
@Data
public class MetaItemUpdateApiDTO {

    private String metaHandle;

    /**
     * 元数据元素
     */
    List<MetaItemCreateApiDTO> metaItemDTOS;

}
