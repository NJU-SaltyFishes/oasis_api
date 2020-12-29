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
}
