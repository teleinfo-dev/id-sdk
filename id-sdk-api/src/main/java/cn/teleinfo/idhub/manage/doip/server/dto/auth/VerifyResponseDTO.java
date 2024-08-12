package cn.teleinfo.idhub.manage.doip.server.dto.auth;

import lombok.Data;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2023/11/22
 * @description
 */
@Data
public class VerifyResponseDTO {
    
    private String signature;
    
    private String handle;
}
