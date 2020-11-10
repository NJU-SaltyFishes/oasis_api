package nju.oasis.api.serviceImpl;

import nju.oasis.api.dao.AuthorDAO;
import nju.oasis.api.domain.AuthorES;
import nju.oasis.api.service.AuthorService;
import nju.oasis.api.vo.AuthorESVO;
import nju.oasis.api.vo.ResponseVO;
import nju.oasis.api.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorDAO authorDAO;

    public ResponseVO findById(String id){
        Optional<AuthorES>authorESOptional = authorDAO.findById(id);
        if(authorESOptional.isPresent()){
            AuthorES authorES = authorESOptional.get();
            return ResponseVO.output(ResultCode.SUCCESS,new AuthorESVO(authorES));
        }
        else{
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
    }
}
