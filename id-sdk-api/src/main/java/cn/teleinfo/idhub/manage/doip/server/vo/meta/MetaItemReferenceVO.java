package cn.teleinfo.idhub.manage.doip.server.vo.meta;

import lombok.Data;

/**
 * <Description>
 *
 * @author 梅冬屹
 * @date 2022/12/14
 */
@Data
public class MetaItemReferenceVO {

    /**
     * 元数据元素Id
     */
    private Long metaItemId;
    /**
     * 引用元数据标识
     */
    private String referenceMetaHandle;

    /**
     * 引用元数据版本
     */
    private Long referenceMetaVersion;
}
