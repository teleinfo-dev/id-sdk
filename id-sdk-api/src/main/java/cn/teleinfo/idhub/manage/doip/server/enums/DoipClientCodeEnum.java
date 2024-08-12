package cn.teleinfo.idhub.manage.doip.server.enums;

/**
 * @author wsc
 * @version 1.0
 * @date Created in 2023/11/23
 * @description
 */
public enum DoipClientCodeEnum {

    SUCCESS(001,"0.DOIP/Status.001"),
    INVALID(101,"0.DOIP/Status.101"),
    UN_AUTH_CLIENT(102,"0.DOIP/Status.102"),
    UN_AUTH_OP(103,"0.DOIP/Status.103"),

    DO_NOT_FOUND(104,"0.DOIP/Status.104"),
    DO_ALREADY_EXIST(105,"0.DOIP/Status.105"),
    API_PARAMS_ERROR(106,"0.DOIP/Status.106"),

    DECLINED(200,"0.DOIP/Status.200"),
    AUTH_FAILED(401,"0.DOIP.Extend/Status.401"),
    NO_PERMISSION_VIEW_DATA(403,"0.DOIP.Extend/Status.403"),
    OP_NOT_FOUND(404,"0.DOIP.Extend/Status.404"),
    UN_SUPPORTED_OPERATION(405,"0.DOIP.Extend/Status.405"),

    NO_PERMISSION_MODIFY_DATA(406,"0.DOIP.Extend/Status.406"),

    UN_KNOWN_ERROR(500,"0.DOIP/Status.500"),
    AUTH_WITH_MISTAKE_IDENTITY(501,"0.DOIP/Status.501"),

    // ------------ meta error 600 + -----------------,
    META_UN_PUBLISHED(600,"0.DOIP.Extend/Status.600"),
    META_ITEM_INPUT_ERROR(601,"0.DOIP.Extend/Status.601"),
    META_ITEM_DISABLED(602,"0.DOIP.Extend/Status.602"),
    ITEM_NOT_BELONG_TO_META(603,"0.DOIP.Extend/Status.603"),
    META_NOT_FOUND(604,"0.DOIP.Extend/Status.604"),
    NO_PERMISSION_META_MANAGE(605,"0.DOIP.Extend/Status.605"),
    META_DELETE_ERROR(606,"0.DOIP.Extend/Status.606"),
    META_COPY_ERROR(607,"0.DOIP.Extend/Status.607"),
    META_UPDATE_ERROR(606,"0.DOIP.Extend/Status.609"),
    META_EN_ERROR(807,"0.DOIP.Extend/Status.807"),
    META_CODE_ERROR(808,"0.DOIP.Extend/Status.808"),
    META_ITEM_ASLIST_ONE(809,"0.DOIP.Extend/Status.809"),
    META_NOT_EXISTS(810,"0.DOIP.Extend/Status.810"),
    META_CLASSIFY_NOT_FOUND(811,"0.DOIP.Extend/Status.811"),

    // ------------ instance error 700 + -----------------,
    NO_PERMISSION_DELETE_DO(700,"0.DOIP.Extend/Status.700"),
    NOT_OWN_HANDLE(701,"0.DOIP.Extend/Status.701"),
    HANDLE_VERSION_NOT_MATCH(702,"0.DOIP.Extend/Status.702"),
    NO_PERMISSION_HANDLE_MANAGE(703,"0.DOIP.Extend/Status.703"),

    // ------------ Other error 800 + -----------------,
    FILE_ACQUIRE_ERROR(800,"0.DOIP.Extend/Status.800"),
    LICENSE_ERROR(801,"0.DOIP.Extend/Status.801"),

    
    // 授权类型错误
    AUTH_TYPE_ERROR(900,"0.DOIP.Extend/Status.900"),
    NOT_ALL_APP_HANDLES(901,"0.DOIP.Extend/Status.901"),
    HANDLE_ALREADY_CLASSES_GRANT(902,"0.DOIP.Extend/Status.902"),
    READER_SCOPE_IS_NUMBER(903,"0.DOIP.Extend/Status.903"),

    ;
    
    

    private Integer code;
    
    private String name;


    DoipClientCodeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }
    
    public String getName(){
        return this.name;
    }

    public static DoipClientCodeEnum getByCode(Integer code){
        for(DoipClientCodeEnum codeMessage : DoipClientCodeEnum.values()){
            if(codeMessage.getCode().equals(code)){
                return codeMessage;
            }
        }
        return UN_KNOWN_ERROR;
    }
}
