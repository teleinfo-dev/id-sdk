package cn.teleinfo.idhub.sdk.client;

import cn.teleinfo.idhub.manage.doip.server.api.auth.ChallengeResponseApi;
import cn.teleinfo.idhub.manage.doip.server.api.dataauth.DataAuthorizationApi;
import cn.teleinfo.idhub.manage.doip.server.api.dataauth.HandleUserApi;
import cn.teleinfo.idhub.manage.doip.server.api.file.FileApi;
import cn.teleinfo.idhub.manage.doip.server.api.instance.InstanceApi;
import cn.teleinfo.idhub.manage.doip.server.api.message.MessageCenterApi;
import cn.teleinfo.idhub.manage.doip.server.api.meta.MetaApi;
import cn.teleinfo.idhub.manage.doip.server.domain.DoipReturn;
import cn.teleinfo.idhub.manage.doip.server.dto.auth.VerifyResponseDTO;
import cn.teleinfo.idhub.manage.doip.server.enums.DoipClientCodeEnum;
import cn.teleinfo.idhub.sdk.interceptor.AuthInterceptor;
import cn.teleinfo.idhub.sdk.utils.EncryptionUtils;
import cn.teleinfo.idhub.sdk.WebMvcConfigHelper;
import cn.teleinfo.idhub.sdk.utils.KeyConverter;
import feign.Feign;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class OpenApiClient {
    private JacksonDecoder decoder;
    private ResponseEntityDecoder responseEntityDecoder;
    private WebMvcConfigHelper webMvcConfigHelper;
    private ObjectFactory<HttpMessageConverters> objectFactory;
    private SpringEncoder encoder;
    private SpringMvcContract contract;
    
    private ChallengeResponseApi challengeResponseApi;
    
    private InstanceApi instanceApi;
    
    private FileApi fileApi;
    
    private MetaApi metaApi;
    
    private DataAuthorizationApi dataAuthorizationApi;
    
    private HandleUserApi handleUserApi;

    private MessageCenterApi messageApi;
    
    
    public OpenApiClient() {
        this.decoder = new JacksonDecoder();
        this.responseEntityDecoder = new ResponseEntityDecoder(decoder);
        this.webMvcConfigHelper = new WebMvcConfigHelper();
        this.objectFactory = new ObjectFactory<HttpMessageConverters>() {
            private HttpMessageConverters httpMessageConverters = new HttpMessageConverters(webMvcConfigHelper.getMessageConverters());

            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return httpMessageConverters;
            }
        };
        this.encoder = new SpringEncoder(objectFactory);
        this.contract = new SpringMvcContract();
    }

    /**
     *
     * @param url
     * @param handle
     * @param privateKeyPem
     */
    public OpenApiClient(String url, String handle, String privateKeyPem) {
        this();
        log.info("{} 开始挑战", handle);
        // 构建挑战应答API
        this.challengeResponseApi = Feign.builder()
                .encoder(encoder)
                .decoder(responseEntityDecoder)
                .contract(contract)
                .retryer(Retryer.NEVER_RETRY)
                .target(ChallengeResponseApi.class, url);
        // 认证
        String token = authenticate(handle, privateKeyPem);
        
        // 构建Doip操作API
        this.instanceApi = Feign.builder()
                .encoder(encoder)
                .decoder(responseEntityDecoder)
                .contract(contract)
                .retryer(Retryer.NEVER_RETRY)
                .requestInterceptor(new AuthInterceptor(token))
                .target(InstanceApi.class, url);
        
        // 构建操作文件的API
        this.fileApi = Feign.builder()
                .encoder(encoder)
                .decoder(responseEntityDecoder)
                .contract(contract)
                .retryer(Retryer.NEVER_RETRY)
                .requestInterceptor(new AuthInterceptor(token))
                .target(FileApi.class, url);
        
        // 元数据操作API
        this.metaApi = Feign.builder()
                .encoder(encoder)
                .decoder(responseEntityDecoder)
                .contract(contract)
                .retryer(Retryer.NEVER_RETRY)
                .requestInterceptor(new AuthInterceptor(token))
                .target(MetaApi.class, url);
        
        // 数据授权API
        this.dataAuthorizationApi = Feign.builder()
                .encoder(encoder)
                .decoder(responseEntityDecoder)
                .contract(contract)
                .retryer(Retryer.NEVER_RETRY)
                .requestInterceptor(new AuthInterceptor(token))
                .target(DataAuthorizationApi.class, url);
        
        // 用户API
        this.handleUserApi = Feign.builder()
                .encoder(encoder)
                .decoder(responseEntityDecoder)
                .contract(contract)
                .retryer(Retryer.NEVER_RETRY)
                .requestInterceptor(new AuthInterceptor(token))
                .target(HandleUserApi.class, url);

        // 应用身份消息API
        this.messageApi = Feign.builder()
                .encoder(encoder)
                .decoder(responseEntityDecoder)
                .contract(contract)
                .retryer(Retryer.NEVER_RETRY)
                .requestInterceptor(new AuthInterceptor(token))
                .target(MessageCenterApi.class, url);
        
    }

    /**
     * 鉴权获取token
     * @return
     */
    private String authenticate(String handle, String privateKeyPem) {
        try {
            // 挑战
            DoipReturn<String> challengeResponse = challengeResponseApi.challenge(handle);
            String random = challengeResponse.getData();
            // 签名
            PrivateKey privateKey = KeyConverter.fromPkcs8Pem(privateKeyPem);
            String sign = EncryptionUtils.sign(random.getBytes(StandardCharsets.UTF_8), privateKey);
            // 应答
            VerifyResponseDTO verifyResponseDTO = new VerifyResponseDTO();
            verifyResponseDTO.setHandle(handle);
            verifyResponseDTO.setSignature(sign);
            DoipReturn<Map<String,Object>> verifyResponse = challengeResponseApi.verifyResponse(verifyResponseDTO);
            if (!Objects.equals(verifyResponse.getCode(), DoipClientCodeEnum.SUCCESS.getCode())){
                throw new RuntimeException("挑战失败");
            }
            log.info("{} 挑战应答成功！", handle);
            return verifyResponse.getData().get("token").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String challenge(String handle,String url) {

        // 构建挑战应答API
        this.challengeResponseApi = Feign.builder()
                .encoder(encoder)
                .decoder(responseEntityDecoder)
                .contract(contract)
                .retryer(Retryer.NEVER_RETRY)
                .target(ChallengeResponseApi.class, url);
            // 挑战
            DoipReturn<String> challengeResponse = challengeResponseApi.challenge(handle);
            String random = challengeResponse.getData();
            log.info("{} 挑战成功！返回结果{}", handle,challengeResponse);
            return random;

    }


    public InstanceApi getIntanceApi() {
        return instanceApi;
    }
    
    public FileApi getFileApi() {
        return fileApi;
    }
    
    
    public MetaApi getMetaApi() {
        return metaApi;
    }
    
    public DataAuthorizationApi getDataAuthorizationApi() {
        return dataAuthorizationApi;
    }
    
    public HandleUserApi getHandleUserApi() {
        return handleUserApi;
    }

    public MessageCenterApi getMessageApi() {
        return messageApi;
    }


}
