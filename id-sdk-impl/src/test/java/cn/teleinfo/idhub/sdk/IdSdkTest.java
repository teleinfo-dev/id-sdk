package cn.teleinfo.idhub.sdk;

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

    private static String url = "http://127.0.0.1:3000/";
    private static String handle = "88.608.5288/App_dong-app-01";
    private static String privateKeyPem = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHACn3odzLoCPqHoE28WxbtK9D\n" +
            "TvNy08PMjnoL/GuRuV64DY6ZC4ArSPa5fmhupu2niARiyx4PiUbIKZrou1VXyLIYbWUHZXQV61KH\n" +
            "hw/hkuj2Di3ltfA5nOlgPZb/iiC2nq/0HyqPqbNTuoRl3B6slEt8mzWANHr0+e3bceAUB3df+8t9\n" +
            "PX5M//yk+/0wsseNwR3dEd+b1v+Y0Bnr2FW+mzLGkAgXsKLTLtgtHl6+Lr4qPNDeexlirhgDlrHe\n" +
            "6H+BbFI07PGqVTcALd9MlgHxVU/T0vZlBJzbiJSVQiFI4OU4VPOqH/BRuUMFScinPB7MscKNmpNR\n" +
            "3hLg/OoypCwdAgMBAAECggEAGZHZYRLiXBsX0lc2yC5QBMqjS49fowcjFdKCoVSNF0vR+fBvauN3\n" +
            "wjsOqDGeHB838jxcAE5SgkTRCEaBFcWts3PK77+AnDcGFsS8m/jj9Ci8QSaMYO7l8jObLGRd/kau\n" +
            "TYie8REaIZ3V3mz8eMKJeMyZw58uMfs+srnH6IGYHJLKsZCILEJTShuuO1VcLQHA4iumPzTEPkho\n" +
            "KHHfjBAG49xAkxs+V+FhUHNiH1whYrK8Cj50nnnYJR2buSnBttKLumBrky61jVnrx3HnqBByroZq\n" +
            "RNxpfUggTxU349VNX27n0kwYra2F0WacFjLNPDtig3iEf0vt+g6lEC52aXZcQQKBgQDNCzm9JxT4\n" +
            "UBH5dCniZ26OBvRzgpKbFcYE6vNRNaFvgTxSQYYxfi8ofC3dXGHaNYelxnv33lbk5PGTIcE5w3Gg\n" +
            "qzx8uDBQFVW9GVg/pGXF72/b9oX+dKk5Y2rUA0M78fqxFRS8zpcYhUuppRT+JDvNE4fMdCTltFiN\n" +
            "sLc7XMWG7QKBgQCojNQrPASbQzci8175OWClbjSJoH4/cwRznl+es/Rjse6SqI5Qi0f+/nPclikL\n" +
            "RVsKgq2rq+J8UuLCKM9oi5GLxEMOWsOWN5rUo0H0/3HIACQsS4pBI13vBj4nAaB/ZxqFNGQEasLI\n" +
            "7jimAFmteRwauSnMCADcXoD7CPk4ILzj8QKBgCgABZ6J7kRW0nrYl4csaFvarXuPQGxyvfOdpiP0\n" +
            "k26+RndBgx1KoYaDWysw54H7KWBErVB4lnkG1L3AOwK6xwLyBNXL1Nj6oIRIWnfn6VbI1AiUOiWe\n" +
            "upiQdXmwvUAHf8Fl/Gqs3rsQ3ebVZpgBl4z1P2u6SKxrF09k5wn0mjAtAoGAWsCREMJdHJhBXME7\n" +
            "dueNASXohd3zfAha8kHo7FOql+9bLH4zOmz0E/k/uxri+J2cWnloN1HyPcyHKdG9c2YKtP20uUrn\n" +
            "mr9Pz4Qj5F2SDwT8dRkrVM1NK4DqACihGVgTcHt4CuTNAlE6ES+JGZ1nI4BlX+VDSWRdAE2hmrYN\n" +
            "3pECgYEAgojpq5IrOQnW/LUGyic0uZCTQmrXKSlPdHt3A6yaW/0Dw7yINQYhtB/tuSNfWuFt2MIq\n" +
            "NOqRjqqkw56Y3GSJKTQ2hnlKb298kv8wl1A70j/gOu1ZrozcU8OLAqnLVx26SHtMf2wmAqn9UCBI\n" +
            "kxwF8YnEhbDox7cR5J7zrvgMAsU=\n" +
            "-----END PRIVATE KEY-----";


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
     * 0.DOIP/Op.Create
     */
    // @Test
    // void createTest() throws Exception {
    //     OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);
    //     // 构建请求参数
    //     DoipInputDTO doipInputDTO = new DoipInputDTO();
    //     doipInputDTO.setAppHandle("88.608.999/App_9");
    //     doipInputDTO.setType("88.608.999/META_qifx3");
    //     // 构建标识属性
    //     DoipAttributesDTO doipAttributesDTO = new DoipAttributesDTO();
    //     doipAttributesDTO.getContent().put("1", "11");
    //     doipAttributesDTO.getContent().put("2", "22");
    //     doipAttributesDTO.getContent().put("3", "33");
    //     doipAttributesDTO.getContent().put("4", Arrays.asList("bb86f3b8-876a-4434-acbb-cf60004f21da.jpg"));
    //     doipInputDTO.setAttributes(doipAttributesDTO);
    //
    //     DoipReturn response = openApiClient.getIntanceApi().post("88.608.999/Test_Doip6", DoipOp.CREATE.getName(), doipInputDTO);
    //     log.info(objectMapper.writeValueAsString(response));
    // }
    //
    // /**
    //  * 0.DOIP/Op.ListOperations
    //  */
    // @Test
    // void listOpsTest() throws Exception {
    //     OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);
    //     DoipReturn<List<String>> response = openApiClient.getIntanceApi().get("88.608.999/DOIPServiceInfo", DoipOp.LIST_OPS.getName());
    //     log.info(objectMapper.writeValueAsString(response));
    // }
    //
    // /**
    //  * 0.DOIP/Op.Retrieve
    //  */
    // @Test
    // void retrieveTest() throws Exception {
    //     OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);
    //     DoipReturn<DoipVo> response = openApiClient.getIntanceApi().get("88.608.999/qifx3_qifx3", DoipOp.RETRIEVE.getName());
    //     log.info(objectMapper.writeValueAsString(response));
    // }

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
//        createApiDTO.setReferenceMetaHandle("");
//        createApiDTO.setMetaState(0);
//        createApiDTO.setIsQuote(0);

        List<MetaItemCreateApiDTO> metaItemCreateDTOS = new ArrayList<>();

//        //字符型
//        MetaItemCreateApiDTO metaItemCreateApiDTO = new MetaItemCreateApiDTO();
//        metaItemCreateApiDTO.setItemCode("code1");
//        metaItemCreateApiDTO.setEnglishName("en1");
//        metaItemCreateApiDTO.setChineseName("zh1");
//        metaItemCreateApiDTO.setInputNecessary(1);
//        metaItemCreateApiDTO.setRequired(0);
//        metaItemCreateApiDTO.setUniqueField(0);
//        MetaItemSchemaCreateApiDTO metaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
//        metaItemSchemaCreateApiDTO.setDataType(1);
//        metaItemSchemaCreateApiDTO.setMinLength(3);
//        metaItemSchemaCreateApiDTO.setMaxLength(5);
//        metaItemCreateApiDTO.setItemSchemaCreateDTO(metaItemSchemaCreateApiDTO);
//
//        metaItemCreateDTOS.add(metaItemCreateApiDTO);
//
//
//        //数值型
//        MetaItemCreateApiDTO numberMetaItemCreateApiDTO = new MetaItemCreateApiDTO();
//        numberMetaItemCreateApiDTO.setItemCode("code2");
//        numberMetaItemCreateApiDTO.setEnglishName("en2");
//        numberMetaItemCreateApiDTO.setChineseName("zh2");
//        numberMetaItemCreateApiDTO.setInputNecessary(1);
//        numberMetaItemCreateApiDTO.setRequired(0);
//        numberMetaItemCreateApiDTO.setUniqueField(0);
//        MetaItemSchemaCreateApiDTO numberMetaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
//        numberMetaItemSchemaCreateApiDTO.setDataType(2);
//        numberMetaItemCreateApiDTO.setItemSchemaCreateDTO(numberMetaItemSchemaCreateApiDTO);
//
//        metaItemCreateDTOS.add(numberMetaItemCreateApiDTO);
//
//        //时间型
//        MetaItemCreateApiDTO timeMetaItemCreateApiDTO = new MetaItemCreateApiDTO();
//        timeMetaItemCreateApiDTO.setItemCode("code3");
//        timeMetaItemCreateApiDTO.setEnglishName("en3");
//        timeMetaItemCreateApiDTO.setChineseName("zh3");
//        timeMetaItemCreateApiDTO.setInputNecessary(1);
//        timeMetaItemCreateApiDTO.setRequired(0);
//        timeMetaItemCreateApiDTO.setUniqueField(0);
//        MetaItemSchemaCreateApiDTO timeMetaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
//        timeMetaItemSchemaCreateApiDTO.setDataType(3);
//        timeMetaItemSchemaCreateApiDTO.setDateFormat("YYYY-MM-DD");
//        timeMetaItemCreateApiDTO.setItemSchemaCreateDTO(timeMetaItemSchemaCreateApiDTO);
//
//        metaItemCreateDTOS.add(timeMetaItemCreateApiDTO);
//
//
//        //文件型
//        MetaItemCreateApiDTO fileMetaItemCreateApiDTO = new MetaItemCreateApiDTO();
//        fileMetaItemCreateApiDTO.setItemCode("code4");
//        fileMetaItemCreateApiDTO.setEnglishName("en4");
//        fileMetaItemCreateApiDTO.setChineseName("zh4");
//        fileMetaItemCreateApiDTO.setInputNecessary(1);
//        fileMetaItemCreateApiDTO.setRequired(0);
//        fileMetaItemCreateApiDTO.setUniqueField(0);
//        MetaItemSchemaCreateApiDTO fileMetaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
//        fileMetaItemSchemaCreateApiDTO.setDataType(4);
//        fileMetaItemSchemaCreateApiDTO.setFileType("2");
//        fileMetaItemSchemaCreateApiDTO.setFileRange(2);
//        fileMetaItemSchemaCreateApiDTO.setMaxFileCount(1);
//        fileMetaItemCreateApiDTO.setItemSchemaCreateDTO(fileMetaItemSchemaCreateApiDTO);
//
//        metaItemCreateDTOS.add(fileMetaItemCreateApiDTO);
//        createApiDTO.setMetaItemCreateDTOS(metaItemCreateDTOS);


        //引用类型
        MetaItemCreateApiDTO quoteMetaItemCreateApiDTO = new MetaItemCreateApiDTO();
        quoteMetaItemCreateApiDTO.setItemCode("code5");
        quoteMetaItemCreateApiDTO.setEnglishName("en5");
        quoteMetaItemCreateApiDTO.setChineseName("zh5");
        quoteMetaItemCreateApiDTO.setInputNecessary(1);
        quoteMetaItemCreateApiDTO.setRequired(0);
        quoteMetaItemCreateApiDTO.setUniqueField(0);
        quoteMetaItemCreateApiDTO.setComment("备注");
        MetaItemSchemaCreateApiDTO quoteMetaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
        quoteMetaItemSchemaCreateApiDTO.setDataType(5);
        quoteMetaItemSchemaCreateApiDTO.setReferenceType(1);
        quoteMetaItemCreateApiDTO.setItemSchemaCreateDTO(quoteMetaItemSchemaCreateApiDTO);

        MetaItemReferenceApiDTO itemReferenceDTO = new MetaItemReferenceApiDTO();
        itemReferenceDTO.setReferenceMetaHandle("88.608.5288/META_07_02");
        quoteMetaItemCreateApiDTO.setItemReferenceDTO(itemReferenceDTO);

        metaItemCreateDTOS.add(quoteMetaItemCreateApiDTO);


        //音频型
        MetaItemCreateApiDTO musicMetaItemCreateApiDTO = new MetaItemCreateApiDTO();
        musicMetaItemCreateApiDTO.setItemCode("code6");
        musicMetaItemCreateApiDTO.setEnglishName("en6");
        musicMetaItemCreateApiDTO.setChineseName("zh6");
        musicMetaItemCreateApiDTO.setInputNecessary(1);
        musicMetaItemCreateApiDTO.setRequired(0);
        musicMetaItemCreateApiDTO.setUniqueField(0);
        MetaItemSchemaCreateApiDTO musicMetaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
        musicMetaItemSchemaCreateApiDTO.setDataType(6);
        musicMetaItemSchemaCreateApiDTO.setMaxFileCount(2);
        musicMetaItemCreateApiDTO.setItemSchemaCreateDTO(musicMetaItemSchemaCreateApiDTO);

        metaItemCreateDTOS.add(musicMetaItemCreateApiDTO);


        //视频型
        MetaItemCreateApiDTO videoMetaItemCreateApiDTO = new MetaItemCreateApiDTO();
        videoMetaItemCreateApiDTO.setItemCode("code7");
        videoMetaItemCreateApiDTO.setEnglishName("en7");
        videoMetaItemCreateApiDTO.setChineseName("zh7");
        videoMetaItemCreateApiDTO.setInputNecessary(1);
        videoMetaItemCreateApiDTO.setRequired(0);
        videoMetaItemCreateApiDTO.setUniqueField(0);
        MetaItemSchemaCreateApiDTO videoMetaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
        videoMetaItemSchemaCreateApiDTO.setDataType(7);
        videoMetaItemSchemaCreateApiDTO.setMaxFileCount(1);
        videoMetaItemCreateApiDTO.setItemSchemaCreateDTO(videoMetaItemSchemaCreateApiDTO);

        metaItemCreateDTOS.add(videoMetaItemCreateApiDTO);

        //图片型
        MetaItemCreateApiDTO imageMetaItemCreateApiDTO = new MetaItemCreateApiDTO();
        imageMetaItemCreateApiDTO.setItemCode("code8");
        imageMetaItemCreateApiDTO.setEnglishName("en8");
        imageMetaItemCreateApiDTO.setChineseName("zh8");
        imageMetaItemCreateApiDTO.setInputNecessary(1);
        imageMetaItemCreateApiDTO.setRequired(0);
        imageMetaItemCreateApiDTO.setUniqueField(0);
        MetaItemSchemaCreateApiDTO imageMetaItemSchemaCreateApiDTO = new MetaItemSchemaCreateApiDTO();
        imageMetaItemSchemaCreateApiDTO.setDataType(8);
        imageMetaItemSchemaCreateApiDTO.setMaxFileCount(1);
        imageMetaItemCreateApiDTO.setItemSchemaCreateDTO(imageMetaItemSchemaCreateApiDTO);
        metaItemCreateDTOS.add(imageMetaItemCreateApiDTO);



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
     * 同类数据授权-公开/撤销公开
     */
    @Test
    void batchPublicMetaApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);


        ClassGrantPublicDTO classGrantPublicDTO = new ClassGrantPublicDTO();
        classGrantPublicDTO.setMetaHandle("88.608.5288/META_07_01");
        classGrantPublicDTO.setScope(1);

        List<String> items = new ArrayList<>();
        items.add("en1");

        classGrantPublicDTO.setItems(items);


        System.out.println(JSONUtil.toJsonPrettyStr(classGrantPublicDTO));
        DoipReturn doipReturn = openApiClient.getDataAuthorizationApi().batchPublic(classGrantPublicDTO);

        log.info("同类数据授权-公开/撤销公开返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }


    /**
     * 同类数据授权
     */
    @Test
    void metaItemAuthorizationApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        ClassGrantDTO classGrantDTO = new ClassGrantDTO();
        classGrantDTO.setMetaHandle("88.608.5288/META_07_01");
        List<ItemAccessListDTO> accessList = new ArrayList<>();
        ItemAccessListDTO itemAccessListDTO = new ItemAccessListDTO();
        itemAccessListDTO.setItem("en1");
        itemAccessListDTO.setAuthType(1);
        itemAccessListDTO.setScope(2);


        itemAccessListDTO.setHandleUsers(Arrays.asList("88.608.5288/App_xz2_app2"));
        itemAccessListDTO.setRemoveHandleUsers(Arrays.asList("88.608.5288/App_xz"));
        accessList.add(itemAccessListDTO);


        classGrantDTO.setAccessList(accessList);
        System.out.println(JSONUtil.toJsonPrettyStr(classGrantDTO));
        DoipReturn doipReturn = openApiClient.getDataAuthorizationApi().metaItemAuthorization(classGrantDTO);

        log.info("同类数据授权返回结果：{}", JSONUtil.toJsonStr(doipReturn));
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
     * 查看应用身份的消息列表
     */
    @Test
    void appMessageGrantApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        DoipReturn doipReturn = openApiClient.getMessageApi().appMessage(0,10);

        log.info("查看应用身份的消息列表返回结果：{}", JSONUtil.toJsonStr(doipReturn));
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

//        //字符
//        content.put("en1","hello");
//
//        //数值
//        content.put("en2",123);
//
//        //日期
//
//        content.put("en3",DateUtil.format(new Date(),"yyyy-MM-dd"));
//        //文件
//        content.put("en4",Arrays.asList("bb86f3b8-876a-4434-acbb-cf60004f21da.jpg"));

        //引用
        content.put("en5","88.608.5288/handle_07_02");
        //音频
        content.put("en6",Arrays.asList("968cf946-955a-4a52-b723-97f154898241.mp3"));
        //视频
        content.put("en7",Arrays.asList("b641eb7e-9888-4479-8f0e-30311983d55f.mp4"));
        //图片
        content.put("en8",Arrays.asList("e3ea523a-f90f-4e06-b362-fe67c17046b7.jpeg"));


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



    /**
     * 上传文件
     */
    @Test
    void uploadApiTest() throws IOException {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        File file = new File("/Users/dzh/Downloads/chrome/接入存证平台接口 (1).docx");
        FileInputStream fileInputStream = new FileInputStream(file);

        MultipartFile multipartFile = new MockMultipartFile(file.getName(),file.getName(), "application/octet-stream",fileInputStream);
        System.out.println(multipartFile.getName());

        DoipReturn doipReturn = openApiClient.getFileApi().upload(null,"88.608.5288/META_70c4d73b6c8e494","name",multipartFile);

        log.info("上传文件返回结果：{}", JSONUtil.toJsonStr(doipReturn));
    }


    /**
     * 下载文件
     */
    @Test
    void downloadApiTest() {
        OpenApiClient openApiClient = new OpenApiClient(url, handle, privateKeyPem);

        Response download = openApiClient.getFileApi().download("S3_f99d537d-ed6e-439b-ad2a-0b2824bf53c6.p1");

        log.info("下载文件返回结果：{}", download);
    }






}
