package cn.teleinfo.idhub.manage.doip.server.api.auth;

import cn.teleinfo.idhub.manage.doip.server.domain.DoipReturn;
import cn.teleinfo.idhub.manage.doip.server.dto.auth.VerifyResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface ChallengeResponseApi {

    @GetMapping("/api/v1/doip/public/challenge")
    DoipReturn<String> challenge(@RequestParam(value = "handle") String handle);

    @GetMapping("/api/v1/doip/public/verify-response")
    DoipReturn<Map<String,Object>> verifyResponse(@RequestBody VerifyResponseDTO verifyResponseDTO);

}
