package cn.teleinfo.idhub.manage.doip.server.dto.meta;

import lombok.Data;

/**
 * @author 谢珍
 * @version 1.0
 * @date Created in 2024/5/21
 * @description 创建元数据副本DTO
 */
@Data
public class BaseonCreateMetaApiDTO {

    /**
     * 要复制的元数据标识
     */
    private String baseonHandle;

    private BaseonInfoDTO baseInfo;
}
