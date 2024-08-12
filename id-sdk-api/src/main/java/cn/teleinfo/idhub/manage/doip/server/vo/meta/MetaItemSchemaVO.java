package cn.teleinfo.idhub.manage.doip.server.vo.meta;

import lombok.Data;

/**
 * <Description>
 *
 * @author 梅冬屹
 * @date 2022/12/14
 */
@Data
public class MetaItemSchemaVO {
    /**
     * 数据类型;1：字符型，2：数值型，3：日期型，4：文件型，5：引用类型
     */
    private Integer dataType;
    /**
     * 最小长度
     */
    private Integer minLength;
    /**
     * 最大长度
     */
    private Integer maxLength;
    /**
     * 字符值域
     */
    private String charRange;
    /**
     * 数值型精度
     */
    private Integer accuracy;
    /**
     * 数值型精度
     */
    private String numberRange;
    /**
     * 日期格式
     */
    private String dateFormat;
    /**
     * 最大文件数量
     */
    private Integer maxFileCount;
    /**
     * 文件值域;1：any，2：assign
     */

    private Integer fileRange;
    /**
     * 文件类型
     */
    private String fileType;

    private String selfDefFileSuffix;
    /**
     * 引用类型;1：一对一，2：一对多
     */
    private Integer referenceType;

//    private Integer inputNecessary;
//
//    private Integer listItemNecessary;
//
//    private Integer required;
//
//    private Integer uniqueField;
}
