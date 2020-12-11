package nju.oasis.api.controller;

import nju.oasis.api.service.AuthorService;
import nju.oasis.api.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/author")
@CrossOrigin
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseVO findById(@PathVariable("id")String id){
        return authorService.findById(id);
    }

    @RequestMapping(value = "/{id}/relations",
            method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseVO findRelationsById(
            @PathVariable("id")long id,
            @RequestParam(defaultValue = "1")Integer minLevel,
            @RequestParam(defaultValue = "2")Integer maxLevel,
            @RequestParam(defaultValue = "15")Integer numOfEachLayer){
        return authorService.findRelationsById(id,minLevel,maxLevel,numOfEachLayer);
    }

    @RequestMapping(value = "/{id}/predictions",
            method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseVO findPredictionsById(@PathVariable("id")long id,
                                          @RequestParam(defaultValue = "0")Double minDistance,
                                          @RequestParam(defaultValue = "1")Double maxDistance,
                                          @RequestParam(defaultValue = "15")Integer maxNum){
        return authorService.findPredictionsById(id,minDistance,maxDistance,maxNum);
    }
}
