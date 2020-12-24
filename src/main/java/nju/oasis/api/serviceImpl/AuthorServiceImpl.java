package nju.oasis.api.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.api.dao.AuthorDAO;
import nju.oasis.api.domain.AuthorES;
import nju.oasis.api.domain.SimArticle;
import nju.oasis.api.service.AuthorService;
import nju.oasis.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorDAO authorDAO;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseVO findById(String id){
        if(id == null){
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        Optional<AuthorES>authorESOptional = authorDAO.findById(id);
        if(!authorESOptional.isPresent()){
            log.warn("[findById] authorId = " + id + " is not present!" );
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }

        AuthorES authorES = authorESOptional.get();
        AuthorESVO authorESVO = new AuthorESVO(authorES);
        long authorId = authorES.getAuthorId();
        List<SimArticle> simArticleList = authorES.getAuthorArticle();
        List<Long>articleIds = new ArrayList<>();
        for(SimArticle simArticle:simArticleList){
            articleIds.add(simArticle.getArticleId());
        }
        AuthorRequestForm authorRequestForm = new AuthorRequestForm();
        authorRequestForm.setAuthorId(authorId);
        authorRequestForm.setArticleIds(articleIds);
        try {
            ResponseVO responseVO = restTemplate.postForObject("http://OASIS-SERV/author/articles",
                    authorRequestForm, ResponseVO.class);

            if (responseVO.getStatus() != 0) {
                log.warn("[findById] authorId: " + id + " no mostFrequentCoAuthor or mostCitedArticle found!");
                return ResponseVO.output(ResultCode.SUCCESS, authorESVO);
            }
            Map<String, Object> result = (Map<String, Object>) responseVO.getData();
            if(!result.containsKey("predictDirection")){
                log.warn("[findById] authorId: " + id + " no predictDirection found!");
            }
            else{
                String predictDirection = (String) result.get("predictDirection");
                authorESVO.setDirectionPredicted(new DirectionVO(predictDirection));
            }
            if (!result.containsKey("articleId")) {
                log.warn("[findById] authorId: " + id + " no mostCitedArticle found!");
            }
            else{
                long articleId = Long.valueOf((int)result.get("articleId"));
                String articleName = (String) result.get("articleName");
                authorESVO.setMostCitedArticle(articleId,articleName);
            }

            if (!result.containsKey("coAuthorId")) {
                log.warn("[findById] authorId: " + id + " no mostFrequentCoAuthor found!");
            }
            else{
                long coAuthorId = Long.valueOf((int)result.get("coAuthorId"));
                authorESVO.setMostFrequentCoauthor(coAuthorId);
            }
            if(!result.containsKey("directionYear")){
                log.warn("[findById] authorId: " + id + "no directionYear found!");
            }
            else {
                List<Object>directionYears = (List) result.get("directionYear");
                List<YDirectionVO>yDirectionVOS = new ArrayList<>();
                directionYears.forEach(direction->{
                    Map<String,Object>directionYear = (Map)direction;
                    int year = (int)directionYear.get("year");
                    List<Object>formatDirections = (List)directionYear.get("formatDirections");
                    List<DirectionVO>directionVOS = new ArrayList<>();
                    formatDirections.forEach(formatDirection->{
                        Map<String,Object>directionMap = (Map)formatDirection;
                        int keywordId = (int)directionMap.get("directionId");
                        String keywordDesc = (String)directionMap.get("name");
                        DirectionVO directionVO = new DirectionVO();
                        directionVO.setDirectionId(keywordId);
                        directionVO.setName(keywordDesc);
                        directionVOS.add(directionVO);
                    });
                    YDirectionVO yDirectionVO = new YDirectionVO();
                    yDirectionVO.setYear(year);
                    yDirectionVO.setDirections(directionVOS);
                    yDirectionVOS.add(yDirectionVO);
                });
                authorESVO.setDirectionYear(yDirectionVOS);
            }

            return ResponseVO.output(ResultCode.SUCCESS, authorESVO);
        }catch (Exception ex){
            log.error("[findById] error: " + ex.getMessage());
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }

    }

    @Override
    public ResponseVO findRelationsById(long id,int minLevel,int maxLevel,int numOfEachLayer){
        try{
            ResponseVO responseVO = restTemplate.getForObject("http://OASIS-SERV/author/relations/"+id
                    +"?minLevel="+minLevel+"&maxLevel="+maxLevel+"&numOfEachLayer="+numOfEachLayer,
                    ResponseVO.class);
            if(responseVO.getStatus()!=0){
                log.warn("[findRelationsById] authorId:"+id+" error!");
                return ResponseVO.output(ResultCode.PARAM_ERROR,null);
            }
            Map<String,Object>result = new HashMap<>();
            result.put("relations",responseVO.getData());
            return ResponseVO.output(ResultCode.SUCCESS,result);

        }catch (Exception ex){
            log.error("[findRelationsById] error: " + ex.getMessage());
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
    }

    @Override
    public ResponseVO findPredictionsById(long id,Double minDistance,Double maxDistance,Integer maxNum){
        try{
            ResponseVO responseVO = restTemplate.getForObject("http://OASIS-SERV/author/predictions/"+id
                            +"?minDistance="+minDistance+"&maxDistance="+maxDistance+"&maxNum="+maxNum,
                    ResponseVO.class);
            if(responseVO.getStatus()!=0){
                log.warn("[findPredictionsById] authorId:"+id+" error!");
                return ResponseVO.output(ResultCode.PARAM_ERROR,null);
            }
            Map<String,Object>result = new HashMap<>();
            result.put("predictions",responseVO.getData());
            return ResponseVO.output(ResultCode.SUCCESS,result);

        }catch (Exception ex){
            log.error("[findPredictionsById] error: " + ex.getMessage());
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
    }
}
