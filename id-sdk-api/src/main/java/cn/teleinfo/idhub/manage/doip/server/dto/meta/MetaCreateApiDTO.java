package cn.teleinfo.idhub.manage.doip.server.dto.meta;

import lombok.Data;

import java.util.List;

/**
 * @author mdy
 * @version 1.0
 * @date Created in 2024/04/25
 * @description 新建元数据DTO
 */
@Data
public class MetaCreateApiDTO {

    /**
     * 元数据分类Id
     */
    private String classifyCode;

    private Long metaClassifyId;
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
     * 元数据是否发布的状态，兼容接口创建的默认要发布
     */
    private Integer metaState;
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


    /**
     * 元数据元素
     */
    List<MetaItemCreateApiDTO> metaItemCreateDTOS;
    private Integer isQuote = 0;
    private String referenceMetaHandle;
}
