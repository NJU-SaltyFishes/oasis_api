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

            if (!result.containsKey("articleId") || !result.containsKey("articleName")) {
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

            return ResponseVO.output(ResultCode.SUCCESS, authorESVO);
        }catch (Exception ex){
            ex.printStackTrace();
            log.warn("[findById] error: " + ex.getMessage());
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }

    }
}
