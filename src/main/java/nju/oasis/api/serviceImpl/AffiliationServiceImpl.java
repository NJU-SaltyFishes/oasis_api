package nju.oasis.api.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.api.dao.ArticleDAO;
import nju.oasis.api.service.AffiliationService;
import nju.oasis.api.service.ArticleServiceForBl;
import nju.oasis.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AffiliationServiceImpl implements AffiliationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ArticleServiceForBl articleServiceForBl;

    @Override
    public ResponseVO findById(String id){
        try{
            ResponseVO responseVO = restTemplate.getForObject("http://OASIS-SERV/affiliation/info/"+id,
                    ResponseVO.class);
            if (responseVO.getStatus() != 0) {
                log.warn("[findById] affiliation: " + id + " doesn't exist");
                return ResponseVO.output(ResultCode.PARAM_ERROR,null);
            }
            Map<String, Object> affiliationInfo = (Map<String, Object>) responseVO.getData();
            Map<String,Object>result = new HashMap<>();
            affiliationInfo.forEach((key,value)->{
                switch (key){
                    case "newestArticleId":
                        String articleId = String.valueOf(value);
                        ResponseVO responseVO1 = articleServiceForBl.findById(articleId);
                        if(responseVO1.getStatus()!=0){
                            log.warn("[findById] affiliation: " + id + " doesn't have newestArticle");
                            result.put("newestArticle",null);
                        }
                        else{
                            ArticleESVO articleESVO = (ArticleESVO)responseVO1.getData();
                            SimArticleVO simArticleVO = new SimArticleVO(articleESVO);
                            result.put("newestArticle",simArticleVO);
                        }
                        break;
                    default:
                        result.put(key,value);
                        break;
                }
            });
            return ResponseVO.output(ResultCode.SUCCESS,result);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("[findById] error: " + ex.getMessage());
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }

    }

    private Map<String,Object>transformKeyword2Direction(Map<String,Object>keyword){

        Map<String,Object>direction = new HashMap<>();
        keyword.forEach((key,value)->{
            switch (key){
                case "keywordId":
                    direction.put("directionId",value);
                    break;
                case "keywordDesc":
                    direction.put("name",value);
                    break;
                case "keywordAppearNum":
                    direction.put("appearNum",value);
                    break;
            }
        });
        return direction;
    }
}
