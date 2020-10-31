package nju.oasis.api.serviceImpl;

import nju.oasis.api.config.Model;
import nju.oasis.api.dao.ArticleDAO;
import nju.oasis.api.domain.Article;
import nju.oasis.api.service.ArticleService;

import nju.oasis.api.vo.ArticleVO;
import nju.oasis.api.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static nju.oasis.api.config.Model.noArticleException;

@Service

public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDAO articleDAO;


    @Override
    public List<String> keywordSearch(String keyword){
        return null;
    }

    public ResponseVO findById(String id){
        Optional<Article> articleOptional = articleDAO.findById(id);
        if(articleOptional.isPresent()){
            Article article = articleOptional.get();
            return ResponseVO.buildSuccess(new ArticleVO(article));
        }
        else{
            return ResponseVO.buildFailure(noArticleException);
        }
    }

}
