package cn.teleinfo.idhub.manage.doip.server.dto.dataauth;

import lombok.Data;

import java.util.List;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2024/5/15
 * @description
 */
@Data
public class SingleHandleGrantDTO {

    /**
     * 标识
     */
    private String handle;

    private Integer grantType;

    private Integer readerScope;

    private List<String> handleUserReaders;

    /**
     * 编辑权限用户，可选
     */
    private List<String> handleUserWriters;

    private List<String> delHandleUserReaders;

    /**
     * 删除编辑权限用户，可选
     */
    private List<String> delHandleUserWriters;
}
