package cn.teleinfo.idhub.manage.doip.server.api.meta;

import cn.teleinfo.idhub.manage.doip.server.domain.DoipReturn;
import cn.teleinfo.idhub.manage.doip.server.dto.meta.*;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

public interface MetaApi {

    @PostMapping("/api/v1/open/meta/create")
    DoipReturn createMetaInfo(@RequestBody MetaCreateApiDTO metaCreateDTO);

    @GetMapping("/api/v1/open/meta/info")
    DoipReturn metaInfo(@RequestParam(value = "metaHandle") String metaHandle);

    @PostMapping("/api/v1/open/meta/grant-info")
     DoipReturn addMetaGrant(@RequestBody MetaGrantApiDTO metaGrantApiDTO);
    @PutMapping("/api/v1/open/meta/update-basic-info")
    DoipReturn updateMetaBasicInfo(@RequestBody MetaBasicInfoDTO metaBasicInfoDTO);

    @PutMapping("/api/v1/open/meta/publish-or-withdraw")
    DoipReturn publishOrWithdraw(@RequestBody PublishOrWithdrawApiDTO paramsDTO);

    @PostMapping("/api/v1/open/meta/baseon-create")
     DoipReturn baseonCreateMeta(@RequestBody BaseonCreateMetaApiDTO metaCopyApiDTO);

    @PutMapping("/api/v1/open/meta/update-item")
    DoipReturn updateItem(@RequestBody MetaItemUpdateApiDTO metaItemUpdateApiDTO);

    @DeleteMapping("/api/v1/open/meta/delete")
    DoipReturn deleteMeta(@RequestParam(value = "metaHandleList", defaultValue = "") List<String> metaHandleList);

    @GetMapping("/api/v1/open/meta/classify-query")
    DoipReturn claasifyQuery();

    @GetMapping("/api/v1/open/meta/page")
    DoipReturn metaPage(@RequestParam(value = "metaHandle", required = false) String metaHandle,
                        @RequestParam(value = "metaState", required = false) Integer metaState,
                        @RequestParam(value = "classifyCode") String classifyCode,
                        @RequestParam(value = "startTime", required = false) String startTime,
                        @RequestParam(value = "endTime", required = false) String endTime,
                        @RequestParam("page") Integer page, @RequestParam("size")Integer size);


    @GetMapping("/api/v1/open/handle/page")
    DoipReturn handlePage(@RequestParam("metaHandle") String metaHandle,
                          @RequestParam(value = "handleName", required = false) String handleName,
                          @RequestParam(value = "startTime", required = false) String startTime,
                          @RequestParam(value = "endTime", required = false) String endTime,
                          @RequestParam("page") Integer page, @RequestParam("size")Integer size);
}


