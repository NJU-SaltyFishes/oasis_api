package nju.oasis.api.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVO implements Serializable {

    private Integer status;
    private String message;
    private Object data;

    public static ResponseVO output(ResultCode resultCode, Object data){
        ResponseVO response = new ResponseVO();
        response.status = resultCode.code();
        response.message = resultCode.message();
        response.data = data;
        return response;
    }
}

