package nju.oasis.api.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.api.dao.AuthorDAO;
import nju.oasis.api.domain.AuthorES;
import nju.oasis.api.service.AuthorService;
import nju.oasis.api.vo.AuthorESVO;
import nju.oasis.api.vo.ResponseVO;
import nju.oasis.api.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorDAO authorDAO;

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
        return ResponseVO.output(ResultCode.SUCCESS,new AuthorESVO(authorES));

    }
}
