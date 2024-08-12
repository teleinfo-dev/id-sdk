package cn.teleinfo.idhub.manage.doip.server.dto.meta;

import lombok.Data;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2022/12/13
 * @description
 */
@Data
public class MetaItemSchemaCreateApiDTO {

    /**
     * 数据类型;1：字符型，2：数值型，3：日期型，4：文件型，5：引用类型;6、音频型；7、视频型；8、图片型
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
     * 数值范围
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
}
