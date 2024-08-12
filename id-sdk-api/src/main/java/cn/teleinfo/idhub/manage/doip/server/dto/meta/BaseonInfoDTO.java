package cn.teleinfo.idhub.manage.doip.server.dto.meta;

import lombok.Data;

/**
 * @author 谢珍
 * @version 1.0
 * @date Created in 2024/5/21
 * @description 创建备份元数据baseInfo DTO
 */
@Data
public class BaseonInfoDTO {
    /**
     * 元数据标识
     */
    private String metaHandle;

    /**
     * 元数据编码
     */
    private String metaCode;

    /**
     * 本企业分类
     */
    private String classifyCode;
}
