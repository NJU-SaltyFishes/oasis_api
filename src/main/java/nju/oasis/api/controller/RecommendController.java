package nju.oasis.api.controller;


import nju.oasis.api.service.RecommendService;
import nju.oasis.api.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/recommend")
@CrossOrigin
public class RecommendController {

    @Autowired
    RecommendService recommendService;

    @RequestMapping(value = "/direction",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseVO recommendDirection(@RequestParam("prefix")String prefix){
        return recommendService.recommendDirection(prefix);
    }

    @RequestMapping(value = "/publication",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseVO recommendPublication(@RequestParam("prefix")String prefix){
        return recommendService.recommendPublication(prefix);
    }

    @RequestMapping(value = "/reader",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseVO recommendReader(@RequestParam("direction")String direction,
                                      @RequestParam("publication")String publication,
                                      @RequestParam(value = "limit",defaultValue = "10")int limit){
        return recommendService.recommendReader(direction,publication,limit);
    }

    @RequestMapping(value = "/article",  method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseVO recommendArticle(@RequestParam("direction")String direction,
                                       @RequestParam("publication")String publication,
                                       @RequestParam(defaultValue = "0") Integer startPage,
                                       @RequestParam(defaultValue = "7") Integer limit){
        return recommendService.recommendArticle(direction, publication, startPage, limit);
    }
}
