package cn.teleinfo.idhub.manage.doip.server.dto.meta;

import lombok.Data;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2022/12/15
 * @description
 */
@Data
public class MetaBasicInfoDTO {

    /**
     * 本企业内的分类code
     */
    private String classifyCode;


    /**
     * 元数据标识
     */
    private String metaHandle;
    /**
     * 元数据名称
     */
    private String metaName;
    /**
     * 元数据编码
     */
    private String metaCode;
    /**
     * 行业门类
     */
    private String industryCategory;
    /**
     * 行业大类
     */
    private String industrySpecific;
    /**
     * 行业中类
     */
    private String industryTrade;
    /**
     * 行业小类
     */
    private String industrySubclass;

    /**
     * 依据标准
     */
    private String standard;

    private String metaDesc;
}
