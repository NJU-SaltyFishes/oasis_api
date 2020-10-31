package nju.oasis.api.controller;

import nju.oasis.api.service.ArticleService;
import nju.oasis.api.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    ArticleService articleService;


    @RequestMapping(value = "/{id}",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseVO findById(@PathVariable("id")String id){
        return articleService.findById(id);
    }
}
