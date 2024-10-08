package cn.teleinfo.idhub.sdk;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.teleinfo.idhub.manage.doip.server.domain.DoipReturn;
import cn.teleinfo.idhub.manage.doip.server.dto.dataauth.ClassGrantDTO;
import cn.teleinfo.idhub.manage.doip.server.dto.dataauth.ClassGrantPublicDTO;
import cn.teleinfo.idhub.manage.doip.server.dto.dataauth.ItemAccessListDTO;
import cn.teleinfo.idhub.manage.doip.server.dto.dataauth.SingleHandleGrantDTO;
import cn.teleinfo.idhub.manage.doip.server.dto.instance.HandleAttributesDTO;
import cn.teleinfo.idhub.manage.doip.server.dto.instance.HandleInputDTO;
import cn.teleinfo.idhub.manage.doip.server.dto.meta.*;
import cn.teleinfo.idhub.manage.doip.server.enums.DoipOp;
import cn.teleinfo.idhub.sdk.client.OpenApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Skying
 * @version 1.0
 * @date Created in 2023/6/25
 * @description
 */
@Slf4j
public class IdSdkTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static String url = "http://127.0.0.1:3100/";
    private static String handle = "88.222.421/App_wussying";
    private static String privateKeyPem = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDDV16HyM5v61vCdTuOv4yDDTCS\n" +
            "rk5LrZjKzC7TmwxyjRtVG2ZYT3AAdh8Y/EkEh25Sqgp/st1VjK8tiDUASRTDL4oUrjfl1qbeNcP9\n" +
            "eQyB74gzWyGxXf6fI9rOmd6YkEN2wL2QoL+4ITdEJ1WKmVeWTgckjZuOQthe4t2i/s1By23VtvaS\n" +
            "88xtpleHMQe6dV7CmOgjh93PJ3wH5H6s5XY5SqjZWIHV/KjLl2kAIDT7Ax2Inwu/T7b5ny0Opxtc\n" +
            "So7gnuxcYvjEfJnbVSXNrIk16vppNgie97OGlMJNBEDVs+aX2xDlfgafoAA/Q2yFVxKgNyB1wC0t\n" +
            "7miBNlRkKlmfAgMBAAECggEAPJfYadQ/PPZL41kMHW6LfodT3twUy2LQ9284w0jgJpRguxihuZkC\n" +
            "jBIX+V8gozgDX78BPLVV9NiL6lrK3gjNWPJhKaMVEcVww5L39aNb3t5XaF7HZ8ZZWjH8CIQwSTDO\n" +
            "zIJjCv7iGZKsGwb8gN6qDaSskIkgjJNtnJlQ2OOi9dpCsa9PlO2F0OL8BxI7n4ha98EjWKNUqE/i\n" +
            "ByaL4l9jmRtu5eFWVvPscGZLBbR56Wt9wS2Uojb/QNhi1tsYN2yG8WQ5T+00rkC85YXpWj8/W5Vz\n" +
            "Hq65tuwaB9kbLCTAzXPUz3zkQx/NQgSGpbfl1BCMmJZz7O/6eXBeHu7R/auSgQKBgQDlye25q4kw\n" +
            "BiRzWuaW3d49YMl2owDyrDA28k5BvpgYdkk/Gc7DboWJQGEqTaH8Bb8oR2iJviy3ib7y0vpRgOYi\n" +
            "O83I8EimrDFqJSAzYz5bknh970Q3YZcdG2361f8m0zGP5M7b65MlNelrJeQNTHz4nBIJPCAg8XLE\n" +
            "Qyiw3RCeXwKBgQDZn4m0IUq3bGVbY4hGjXt7mImaJ5NBekckWuc8GctiHNR04FbnVwDO+H7507Tr\n" +
            "BbGtZDq6kvdu6S/07n6Ffxo/djI1MFsXNHihz++5wi6wq069JsvE9B1Kv69/291IoD05zrFXEJvn\n" +
            "7PvY6JS7yuYoQclIS2UYcjGGjqksi1mMwQKBgAeLoeq+3zpceUXwbjH2Bx4s13C12YoebVbgjgFK\n" +
            "qOPkvnL7/fYc4vWmz4n8Ep2b8rL35U7gl8dPFp4Sn3WdrfDh1dgLS9dtQBIkNfJ6B8op/RrliG/p\n" +
            "HHUwOWRLMp2rwazf7or6KVLHemqyS9LD+DaRqWX0O3nZc9NOAUANgkYxAoGBAMpSYVKfVPh8hSrW\n" +
            "uT5mkPPSV7OCsYvBY9yvyqT8kjJv/TF/1CuVnoHNUzdulJefnpVGRrdopTNvBg/fnq78AOoZOApt\n" +
            "H3LObIx7ZjM8De5gBX8jT3SXQ5hX55CmVu8LUoCo4gTAw0F2+JmreYOx2hJ5PunAdoeoutSPU0a/\n" +
            "cwzBAoGAJdvrEcGy4Lo+CU24EvwusYalb08hLq3VGDzLIhHNU9j4iEDjLkY2jOqcLMmKwlMQOORe\n" +
            "FTZKaXkGTVfm5eTI5fFg/Fwf1hYN9stmqIAocPpeN9dGlKBpILndb7wKI8RHYA1KklVYuc6UuqQ9\n" +
            "nsaMq3qsrYpfLxKKVK6nHzDmCns=\n" +
            "-----END PRIVATE KEY-----\n";


    /**
     * 挑战
     */
    @Test
    void challengeTest(){
        new OpenApiClient().challenge(handle,url);
    }


    /**
     * 挑战应答
     */
    @Test
    void challengeResponseTest(){
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);
    }

    /**
     * 0.DOIP/Op.Hello
     */
    @Test
    void helloTest() throws Exception {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);
        DoipReturn<String> response = openApiClient.getIntanceApi().get("88.608.999/DOIPServiceInfo", DoipOp.HELLO.getName());
        log.info(objectMapper.writeValueAsString(response));
    }

    /**
     * 解析测试
     */
    @Test
    void retrieveTest(){
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);
        // 解析实例标识
        // DoipReturn doipReturn = openApiClient.getIntanceApi().get("88.608.8889/87654321", DoipOp.RETRIEVE.getName());
        // 解析元数据标识
        DoipReturn doipReturn = openApiClient.getIntanceApi().get("88.608.8889/META_11122", DoipOp.RETRIEVE.getName());
        System.out.println(doipReturn);
    }

    /**
     * 新建元数据
     */
    @Test
    void metaCreateApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);


        MetaCreateApiDTO createApiDTO =new MetaCreateApiDTO();

        createApiDTO.setClassifyCode("NEW_CODE");
       // createApiDTO.setMetaHandle("88.608.5288/META_07_03_quote_music_video_image");
        createApiDTO.setMetaName("07_03_quote_music_video_image");
        createApiDTO.setMetaCode("07_03-quote_music_video_image");
        createApiDTO.setIndustryCategory("R");
        createApiDTO.setIndustrySpecific("86");
        createApiDTO.setIndustryTrade("861");
        createApiDTO.setIndustrySubclass("8610");
        createApiDTO.setStandard("");
        createApiDTO.setMetaDesc("");
//        createApiDTO.setMetaState(0);
//        createApiDTO.setIsQuote(0);

        List<MetaItemCreateApiDTO> metaItemCreateDTOS = new ArrayList<>();

       //字符型
       MetaItemCreateApiDTO metaItemCreateApiDTO = new MetaItemCreateApiDTO();
       metaItemCreateApiDTO.setItemCode("code1");
       metaItemCreateApiDTO.setEnglishName("en1");
       metaItemCreateApiDTO.setChineseName("zh1");
       metaItemCreateApiDTO.setInputNecessary(1);
       metaItemCreateApiDTO.setRequired(0);
       metaItemCreateApiDTO.setUniqueField(0);
       MetaItemSchemaCreateApiDTO metaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
       metaItemSchemaCreateApiDTO.setDataType(1);
       metaItemSchemaCreateApiDTO.setMinLength(3);
       metaItemSchemaCreateApiDTO.setMaxLength(5);
       metaItemCreateApiDTO.setItemSchemaCreateDTO(metaItemSchemaCreateApiDTO);

       metaItemCreateDTOS.add(metaItemCreateApiDTO);


       //数值型
       MetaItemCreateApiDTO numberMetaItemCreateApiDTO = new MetaItemCreateApiDTO();
       numberMetaItemCreateApiDTO.setItemCode("code2");
       numberMetaItemCreateApiDTO.setEnglishName("en2");
       numberMetaItemCreateApiDTO.setChineseName("zh2");
       numberMetaItemCreateApiDTO.setInputNecessary(1);
       numberMetaItemCreateApiDTO.setRequired(0);
       numberMetaItemCreateApiDTO.setUniqueField(0);
       MetaItemSchemaCreateApiDTO numberMetaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
       numberMetaItemSchemaCreateApiDTO.setDataType(2);
       numberMetaItemCreateApiDTO.setItemSchemaCreateDTO(numberMetaItemSchemaCreateApiDTO);

       metaItemCreateDTOS.add(numberMetaItemCreateApiDTO);

       //时间型
       MetaItemCreateApiDTO timeMetaItemCreateApiDTO = new MetaItemCreateApiDTO();
       timeMetaItemCreateApiDTO.setItemCode("code3");
       timeMetaItemCreateApiDTO.setEnglishName("en3");
       timeMetaItemCreateApiDTO.setChineseName("zh3");
       timeMetaItemCreateApiDTO.setInputNecessary(1);
       timeMetaItemCreateApiDTO.setRequired(0);
       timeMetaItemCreateApiDTO.setUniqueField(0);
       MetaItemSchemaCreateApiDTO timeMetaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
       timeMetaItemSchemaCreateApiDTO.setDataType(3);
       timeMetaItemSchemaCreateApiDTO.setDateFormat("YYYY-MM-DD");
       timeMetaItemCreateApiDTO.setItemSchemaCreateDTO(timeMetaItemSchemaCreateApiDTO);

       metaItemCreateDTOS.add(timeMetaItemCreateApiDTO);


        createApiDTO.setMetaItemCreateDTOS(metaItemCreateDTOS);
        System.out.println(JSONUtil.toJsonPrettyStr(createApiDTO));
        DoipReturn doipReturn = openApiClient.getMetaApi().createMetaInfo(createApiDTO);

        JSONObject jsonObject = JSONUtil.parseObj(doipReturn.getData());

        log.info("新建元数据返回结果：{}", doipReturn);
    }

    /**
     * 查看元数据
     */
    @Test
    void metaInfoApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        DoipReturn doipReturn = openApiClient.getMetaApi().metaInfo("88.608.5288/META_07_03_quote_music_video_image");

        log.info("查看元数据返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }

    /**
     * 编辑元数据
     */
    @Test
    void metaUpdateInfoApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        MetaBasicInfoDTO metaBasicInfoDTO = new MetaBasicInfoDTO();
        metaBasicInfoDTO.setMetaHandle("88.608.8889/META_xz_update_basic");
        metaBasicInfoDTO.setMetaName("xz更新元数据基本信息3333");
        metaBasicInfoDTO.setMetaCode("xz_update_basic");
        metaBasicInfoDTO.setStandard("依据标准：哈哈哈update");
        metaBasicInfoDTO.setMetaDesc("描述：嘿嘿嘿update");
        metaBasicInfoDTO.setIndustryCategory("A");
        metaBasicInfoDTO.setIndustrySpecific("01");
        metaBasicInfoDTO.setIndustryTrade("011");
        metaBasicInfoDTO.setIndustrySubclass("0111");
        metaBasicInfoDTO.setClassifyCode("hxh");


        System.out.println(JSONUtil.toJsonPrettyStr(metaBasicInfoDTO));
        DoipReturn doipReturn = openApiClient.getMetaApi().updateMetaBasicInfo(metaBasicInfoDTO);

        log.info("编辑元数据返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }

    /**
     * 编辑元数据实体列表
     */
    @Test
    void updateItemApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);


        MetaItemUpdateApiDTO metaItemUpdateApiDTO = new MetaItemUpdateApiDTO();
        metaItemUpdateApiDTO.setMetaHandle("88.608.5288/META_07_01");

        List<MetaItemCreateApiDTO> metaItemDTOS = new ArrayList<>();

        //修改属性值设置
        MetaItemCreateApiDTO metaItemCreateApiDTO = new MetaItemCreateApiDTO();
        metaItemCreateApiDTO.setItemCode("code1");
        metaItemCreateApiDTO.setEnglishName("en1");
        metaItemCreateApiDTO.setChineseName("zh1");
        metaItemCreateApiDTO.setInputNecessary(1);
        metaItemCreateApiDTO.setRequired(0);
        metaItemCreateApiDTO.setUniqueField(0);
        MetaItemSchemaCreateApiDTO metaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
        metaItemSchemaCreateApiDTO.setDataType(1);
        metaItemSchemaCreateApiDTO.setMaxLength(4000);
        metaItemSchemaCreateApiDTO.setMinLength(5);
        metaItemCreateApiDTO.setItemSchemaCreateDTO(metaItemSchemaCreateApiDTO);


        MetaItemReferenceApiDTO metaItemReferenceApiDTO = new MetaItemReferenceApiDTO();
        //数据类型 为5引用类型时 需要设置
        //metaItemReferenceApiDTO.setReferenceMetaHandle("");
        metaItemCreateApiDTO.setItemReferenceDTO(metaItemReferenceApiDTO);
        metaItemCreateApiDTO.setItemReferenceDTO(metaItemReferenceApiDTO);


        metaItemDTOS.add(metaItemCreateApiDTO);
        metaItemUpdateApiDTO.setMetaItemDTOS(metaItemDTOS);

        System.out.println(JSONUtil.toJsonPrettyStr(metaItemUpdateApiDTO));
        DoipReturn doipReturn = openApiClient.getMetaApi().updateItem(metaItemUpdateApiDTO);

        log.info("编辑元数据实体列表返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }


    /**
     * 删除元数据
     */
    @Test
    void deleteMetaApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        List<String> metaHandleList = new ArrayList<>();
        metaHandleList.add("88.608.5288/META_07_01");

        DoipReturn doipReturn = openApiClient.getMetaApi().deleteMeta(metaHandleList);

        log.info("删除元数据返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }

    /**
     * 元数据授权
     */
    @Test
    void addMetaGrantApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);


        MetaGrantApiDTO metaGrantApiDTO = new MetaGrantApiDTO();
        metaGrantApiDTO.setScope(2);
        metaGrantApiDTO.setMetaHandle("88.608.5288/META_07_01");

        List<String> handleUsers = new ArrayList<>();
        handleUsers.add("88.608.5288/App_rwer");
        metaGrantApiDTO.setHandleUsers(handleUsers);


        List<String> removeHandleUsers = new ArrayList<>();
        metaGrantApiDTO.setRemoveHandleUsers(removeHandleUsers);


        System.out.println(JSONUtil.toJsonPrettyStr(metaGrantApiDTO));
        DoipReturn doipReturn = openApiClient.getMetaApi().addMetaGrant(metaGrantApiDTO);

        log.info("元数据授权返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }



    /**
     * 发布/撤回元数据
     */
    @Test
    void publishOrWithdrawApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);


        PublishOrWithdrawApiDTO publishOrWithdrawApiDTO = new PublishOrWithdrawApiDTO();

        publishOrWithdrawApiDTO.setOpType("publish");
        publishOrWithdrawApiDTO.setMetaHandleList(Arrays.asList("88.608.5288/META_07_01","88.608.5288/META_07_02"));


        System.out.println(JSONUtil.toJsonPrettyStr(publishOrWithdrawApiDTO));
        DoipReturn doipReturn = openApiClient.getMetaApi().publishOrWithdraw(publishOrWithdrawApiDTO);

        log.info("发布/撤回元数据返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }


    /**
     * 创建元数据副本
     */
    @Test
    void baseonCreateMetaApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);


        BaseonCreateMetaApiDTO baseonCreateMetaApiDTO = new BaseonCreateMetaApiDTO();

        baseonCreateMetaApiDTO.setBaseonHandle("88.608.5288/META_07_01");
        BaseonInfoDTO baseInfo = new BaseonInfoDTO();
        baseInfo.setMetaHandle("88.608.5288/META_test_zyh_002_copy1");
        baseInfo.setMetaCode("xzcopy1");
        baseInfo.setClassifyCode("sp");

        baseonCreateMetaApiDTO.setBaseInfo(baseInfo);



        System.out.println(JSONUtil.toJsonPrettyStr(baseonCreateMetaApiDTO));
        DoipReturn doipReturn = openApiClient.getMetaApi().baseonCreateMeta(baseonCreateMetaApiDTO);

        log.info("创建元数据副本返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }


    /**
     * 应用身份用户列表查询
     */
    @Test
    void handleUserGroupListApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);
        DoipReturn doipReturn = openApiClient.getHandleUserApi().getHandleUserGroupList();

        log.info("应用身份用户列表查询返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }


    /**
     * 单一标识授权
     */
    @Test
    void singleHandleGrantApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);


        SingleHandleGrantDTO singleHandleGrantDTO = new SingleHandleGrantDTO();

        singleHandleGrantDTO.setReaderScope(1);
        singleHandleGrantDTO.setGrantType(1);
        singleHandleGrantDTO.setHandle("88.608.5288/handle_07_02");
        singleHandleGrantDTO.setHandleUserWriters(Arrays.asList("88.608.5288/App_xz2_app2"));
        singleHandleGrantDTO.setHandleUserReaders(Arrays.asList("88.608.5288/App_xz2_app2"));
        singleHandleGrantDTO.setDelHandleUserWriters(Arrays.asList("88.608.5288/App_zyy1_app"));
        singleHandleGrantDTO.setDelHandleUserReaders(Arrays.asList("88.608.5288/App_zyy1_app"));

        System.out.println(JSONUtil.toJsonPrettyStr(singleHandleGrantDTO));
        DoipReturn doipReturn = openApiClient.getDataAuthorizationApi().singleHandleGrant(singleHandleGrantDTO);

        log.info("单一标识授权返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }


    /**
     * 数据分类列表
     */
    @Test
    void claasifyQuery() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        DoipReturn doipReturn = openApiClient.getMetaApi().claasifyQuery();

        log.info("分类列表：{}", JSONUtil.toJsonStr(doipReturn));
    }

    /**
     * 元数据列表
     */
    @Test
    void metaList() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        DoipReturn doipReturn = openApiClient.getMetaApi().metaPage(null,null,"202406271011",null,null,0,1);

        log.info("元数据列表：{}", JSONUtil.toJsonStr(doipReturn));
    }



    /**
     * 标识数据列表
     */
    @Test
    void handlePage() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        DoipReturn doipReturn = openApiClient.getMetaApi().handlePage("88.608.5288/META_dong-ceshi-6-22",null,null,null,0,10);

        log.info("标识数据列表：{}", JSONUtil.toJsonStr(doipReturn));
    }


    /**
     * 标识注册
     */
    @Test
    void createHandleApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);


        HandleInputDTO handleInputDTO = new HandleInputDTO();
        handleInputDTO.setType("88.608.5288/META_07_03_quote_music_video_image");
        handleInputDTO.setRequestId(UUID.randomUUID().toString());
        handleInputDTO.setClientId(UUID.randomUUID().toString());

        HandleAttributesDTO handleAttributesDTO = new HandleAttributesDTO();
        Map<String,Object> content = new HashMap<>();

       //字符
       content.put("en1","hello");
       //数值
       content.put("en2",123);
       //日期
       content.put("en3", DateUtil.format(new Date(),"yyyy-MM-dd"));

        handleAttributesDTO.setContent(content);
        handleInputDTO.setAttributes(handleAttributesDTO);



        System.out.println(JSONUtil.toJsonPrettyStr(handleInputDTO));
        DoipReturn doipReturn = openApiClient.getIntanceApi().post("88.608.5288/META_07_03_quote_music_video_image_1",DoipOp.CREATE.getName(),handleInputDTO);

        log.info("标识注册返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }

    /**
     * 标识修改
     */
    @Test
    void updateHandleApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        HandleInputDTO handleInputDTO = new HandleInputDTO();
        handleInputDTO.setType("88.608.5288/META_07_01");

        HandleAttributesDTO attributes = new HandleAttributesDTO();
        Map<String,Object> content = new HashMap<>();
        content.put("en1","update handle");

        attributes.setContent(content);

        handleInputDTO.setAttributes(attributes);
        System.out.println(JSONUtil.toJsonPrettyStr(handleInputDTO));
        DoipReturn doipReturn = openApiClient.getIntanceApi().post("88.608.5288/handle_07_02",DoipOp.UPDATE.getName(), handleInputDTO);

        log.info("标识修改返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }


    /**
     * 标识删除
     */
    @Test
    void deleteHandleApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        DoipReturn doipReturn = openApiClient.getIntanceApi().post("88.608.5288/handle_07_02",DoipOp.DELETE.getName(),new HandleInputDTO());

        log.info("标识删除返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }


    /**
     * 标识查询
     */
    @Test
    void searchHandleApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        DoipReturn doipReturn = openApiClient.getIntanceApi().get("88.608.5288/META_07_01",DoipOp.RETRIEVE.getName());

        log.info("标识查询返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }
    
}
