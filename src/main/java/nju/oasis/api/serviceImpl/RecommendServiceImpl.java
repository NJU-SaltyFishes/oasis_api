package nju.oasis.api.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.api.service.RecommendService;
import nju.oasis.api.vo.ResponseVO;
import nju.oasis.api.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseVO recommendDirection(String prefix){
        if(prefix==null||prefix.length()<1){
            log.error("[recommendDirection] recommendDirection must exist but was null or length < 1!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        ResponseVO responseVO = restTemplate.getForObject("http://OASIS-SERV/recommend/direction?prefix="+prefix,
                ResponseVO.class);
        if (responseVO.getStatus() != 0) {
            log.warn("[recommendDirection] recommendDirection: " + prefix + " wrong!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        return responseVO;
    }

    @Override
    public ResponseVO recommendPublication(String prefix){
        if(prefix==null||prefix.length()<1){
            log.error("[recommendPublication] recommendPublication must exist but was null or length < 1!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        ResponseVO responseVO = restTemplate.getForObject("http://OASIS-SERV/recommend/publication?prefix="+prefix,
                ResponseVO.class);
        if (responseVO.getStatus() != 0) {
            log.warn("[recommendPublication] recommendPublication: " + prefix + " wrong!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        return responseVO;
    }
}
