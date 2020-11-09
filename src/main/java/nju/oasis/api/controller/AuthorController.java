package nju.oasis.api.controller;

import nju.oasis.api.service.AuthorService;
import nju.oasis.api.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
