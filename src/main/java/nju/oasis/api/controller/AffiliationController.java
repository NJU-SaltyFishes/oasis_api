package nju.oasis.api.controller;

import nju.oasis.api.service.AffiliationService;
import nju.oasis.api.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/affiliation")
@CrossOrigin
public class AffiliationController {

    @Autowired
    private AffiliationService affiliationService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseVO findById(@PathVariable("id")String id){
        return affiliationService.findById(id);
    }
}
