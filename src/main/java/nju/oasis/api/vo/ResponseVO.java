package nju.oasis.api.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVO implements Serializable {

    private int status;
    private String message;
    private Object data;

    public static ResponseVO buildSuccess(Object data){
        ResponseVO response=new ResponseVO();
        response.setData(data);
        response.setMessage("success");
        response.setStatus(0);
        return response;
    }

    public static ResponseVO buildFailure(String message){
        ResponseVO response=new ResponseVO();
        response.setStatus(-1);
        response.setMessage(message);
        return response;
    }
}

