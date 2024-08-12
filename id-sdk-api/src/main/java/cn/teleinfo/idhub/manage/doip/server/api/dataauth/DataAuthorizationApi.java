package cn.teleinfo.idhub.manage.doip.server.api.dataauth;

import cn.teleinfo.idhub.manage.doip.server.domain.DoipReturn;
import cn.teleinfo.idhub.manage.doip.server.dto.dataauth.ClassGrantDTO;
import cn.teleinfo.idhub.manage.doip.server.dto.dataauth.ClassGrantPublicDTO;
import cn.teleinfo.idhub.manage.doip.server.dto.dataauth.SingleHandleGrantDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2024/5/15
 * @description
 */
public interface DataAuthorizationApi {

    /**
     * 数据授权-公开或撤销公开
     * 
     * @return
     */
    @PostMapping("/api/v1/open/classes-grant/item/batch-public")
    DoipReturn batchPublic(@RequestBody ClassGrantPublicDTO classGrantDTO);
    
    /**
     * 同类数据授权
     * 
     * @return
     */
    @PostMapping("/api/v1/open/classes-grant/authorization")
    DoipReturn metaItemAuthorization(@RequestBody ClassGrantDTO classGrantDTO);
    
    /**
     * 单一标识授权
     * 
     * @return
     */
    @PostMapping("/api/v1/open/single-handle/authorization")
    DoipReturn singleHandleGrant(@RequestBody SingleHandleGrantDTO singleHandleGrantDTO);
}