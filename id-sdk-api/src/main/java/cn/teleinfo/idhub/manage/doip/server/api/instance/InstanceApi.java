package cn.teleinfo.idhub.manage.doip.server.api.instance;

import cn.teleinfo.idhub.manage.doip.server.domain.DoipReturn;
import cn.teleinfo.idhub.manage.doip.server.dto.instance.HandleInputDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2024/5/15
 * @description
 */
public interface InstanceApi {

    /**
     * 标识解析查询
     * 
     * @param targetId
     * @param operationId
     * @return
     */
    @GetMapping("/api/v1/doip")
    DoipReturn get(@RequestParam(value = "targetId") String targetId,
                   @RequestParam(value = "operationId") String operationId);

    /**
     * 标识新增/删除/修改
     * 
     * @param targetId
     * @param operationId
     * @param handleInputDTO
     * @return
     */
    @PostMapping("/api/v1/doip")
    DoipReturn<Map<String,Object>> post(@RequestParam(value = "targetId") String targetId,
                                        @RequestParam(value = "operationId") String operationId,
                                        @RequestBody HandleInputDTO handleInputDTO);
    
}
