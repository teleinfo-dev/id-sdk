package cn.teleinfo.idhub.manage.doip.server.dto.meta;

import lombok.Data;
import java.util.List;

/**
 * <Description>
 *
 * @author meidongyi
 * @date 2024/04/26
 */
@Data
public class MetaGrantApiDTO {

    private List<String> handleUsers;
    /**
     * 元数据标识
     */
    private String metaHandle;
    /**
     * 授权范围1公开2指定范围
     */
    private Integer scope;


    private List<String> removeHandleUsers;
}
