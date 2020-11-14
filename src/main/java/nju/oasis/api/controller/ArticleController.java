package nju.oasis.api.controller;

import nju.oasis.api.service.ArticleService;
import nju.oasis.api.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseVO findById(@PathVariable("id")String id){
        return articleService.findById(id);
    }

    @RequestMapping(value = "/search",  method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseVO search(@RequestParam(defaultValue = "") String allField,
                             @RequestParam(defaultValue = "article") String content,
                             @RequestParam(defaultValue = "0") Integer startPage,
                             @RequestParam(defaultValue = "7") Integer limit,
                             Integer startYear, Integer endYear){
        return articleService.search(allField, content, startPage, limit, startYear, endYear);
    }
}
