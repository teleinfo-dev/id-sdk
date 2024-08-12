package cn.teleinfo.idhub.manage.doip.server.api.file;

import cn.teleinfo.idhub.manage.doip.server.domain.DoipReturn;
import feign.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2024/5/15
 * @description
 */
public interface FileApi {

    /**
     * 文件上传
     * 
     * @param handleName
     * @param metaHandle
     * @param fileField
     * @param file
     * @return
     */
    @PostMapping(value = "/api/v1/doip/file-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    DoipReturn upload(@RequestParam(value = "handleName", required = false) String handleName,
                      @RequestParam(value = "metaHandle") String metaHandle,
                      @RequestParam(value = "fileField") String fileField,
                      @RequestPart(value = "file") MultipartFile file);

    /**
     * 文件下载
     * 
     * @param filePath
     * @return
     */
    @GetMapping("/api/v1/doip/file-acquire")
    Response download(@RequestParam(value = "filePath") String filePath);
}
