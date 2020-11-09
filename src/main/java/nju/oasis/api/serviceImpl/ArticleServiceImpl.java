package nju.oasis.api.serviceImpl;

import nju.oasis.api.dao.ArticleDAO;
import nju.oasis.api.domain.ArticleES;
import nju.oasis.api.service.ArticleService;

import nju.oasis.api.vo.ArticleESVO;
import nju.oasis.api.vo.ResponseVO;
import nju.oasis.api.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDAO articleDAO;


    @Override
    public List<String> keywordSearch(String keyword){
        return null;
    }

    public ResponseVO findById(String id){
        Optional<ArticleES> articleOptional = articleDAO.findById(id);
        if(articleOptional.isPresent()){
            ArticleES articleES = articleOptional.get();
            return ResponseVO.output(ResultCode.SUCCESS,new ArticleESVO(articleES));
        }
        else{
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
    }

}
