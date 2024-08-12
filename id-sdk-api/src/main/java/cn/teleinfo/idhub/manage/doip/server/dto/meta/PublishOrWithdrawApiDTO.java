package cn.teleinfo.idhub.manage.doip.server.dto.meta;

import lombok.Data;

import java.util.List;

/**
 * @author 谢珍
 * @version 1.0
 * @date Created in 2024/4/26
 * @description 元数据发布撤回DTO
 */
@Data
public class PublishOrWithdrawApiDTO {
    /**
     * 批量操作的元数据标识列表
     */
    private List<String> metaHandleList;
    /**
     * 操作类型，枚举值：publish、withdraw
     */
    private String opType;
}
