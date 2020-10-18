package nju.oasis.api.controller;

import nju.oasis.api.vo.ResponseVO;
import nju.oasis.api.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@CrossOrigin
public class controller {

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseVO ping(){
        return ResponseVO.output(ResultCode.SUCCESS, null);
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseVO test(@RequestParam String name){
        String message = restTemplate.getForObject("http://OASIS-SERV/test?name="+name, String.class);
        return ResponseVO.output(ResultCode.SUCCESS, message);
    }
}
