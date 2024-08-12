package cn.teleinfo.idhub.manage.doip.server.vo.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <Description>
 *
 * @author 梅冬屹
 * @date 2022/12/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaItemVO {

//    private Long id;

    private Integer itemIndex;

    private String itemCode;

    private String englishName;

    private String chineseName;

    private Integer itemState;

    private String definition;


    private Integer inputNecessary;

    private Integer listItemNecessary;

    private Integer required;

    private Integer uniqueField;

    private String comment;

    private MetaItemSchemaVO itemSchemaVO;

    private MetaItemReferenceVO itemReferenceVO;

//    private String ACL;
}
