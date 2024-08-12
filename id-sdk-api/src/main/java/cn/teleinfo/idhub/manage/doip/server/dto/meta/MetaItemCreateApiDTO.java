package cn.teleinfo.idhub.manage.doip.server.dto.meta;

import lombok.Data;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2022/12/13
 * @description
 */
@Data
public class MetaItemCreateApiDTO {

    /**
     * 版本Id
     */

    private Integer itemIndex;
    /**
     * 元素编码
     */
    private String itemCode;
    /**
     * 英文名称
     */
    private String englishName;
    /**
     * 中文名称
     */
    private String chineseName;
    /**
     * 元素状态;0：启用，1：禁用（逻辑删除），2：未生效
     */
    private Integer itemState;
    /**
     * 元素定义
     */
    private String definition;
    /**
     * 输入项;0：否，1：是，默认为1
     */
    private Integer inputNecessary;
    /**
     * 列表展示项;0：否，1：是，默认为1
     */
    private Integer listItemNecessary = 1;
    /**
     * 是否必填;0：否，1：是，默认为1
     */
    private Integer required;
    /**
     * 是否唯一;0：否，1：是，默认为1c
     */
    private Integer uniqueField;

    private String ACL;

    /**
     * 备注
     */
    private String comment;

    private MetaItemSchemaCreateApiDTO itemSchemaCreateDTO;

    private MetaItemReferenceApiDTO itemReferenceDTO;


}
