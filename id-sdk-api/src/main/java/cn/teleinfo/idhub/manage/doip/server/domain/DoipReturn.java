package cn.teleinfo.idhub.manage.doip.server.domain;

import cn.teleinfo.idhub.manage.doip.server.enums.DoipClientCodeEnum;
import lombok.Data;

/**
 * @author ：魏思晨
 * @date ：Created in 2022/2/17
 * @description ：
 * @version: 1.0
 */
@Data
public class DoipReturn<T> {

    private Integer code;

    private String message;

    private T data;

    public DoipReturn() {
    }

    public DoipReturn(Integer status, String message, T data) {
        this.code = status;
        this.message = message;
        this.data = data;
    }

    public static <T> DoipReturn<T> success(){
        return new DoipReturn<T>(DoipClientCodeEnum.SUCCESS.getCode(), "成功", null);
    }

    public static <T> DoipReturn<T> success(T data){
        return new DoipReturn<>(DoipClientCodeEnum.SUCCESS.getCode(), "成功", data);
    }

}
