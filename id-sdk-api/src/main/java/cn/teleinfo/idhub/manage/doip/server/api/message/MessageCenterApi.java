package cn.teleinfo.idhub.manage.doip.server.api.message;

import cn.teleinfo.idhub.manage.doip.server.domain.DoipReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface MessageCenterApi {

    @GetMapping("/api/v1/open/message/appMessage")
    DoipReturn appMessage(@RequestParam("page") Integer page, @RequestParam("size")Integer size);

}
