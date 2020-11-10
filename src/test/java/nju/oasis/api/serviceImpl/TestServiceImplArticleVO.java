package nju.oasis.api.serviceImpl;

import nju.oasis.api.OasisApiApplicationTests;
import nju.oasis.api.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TestServiceImplArticleVO extends OasisApiApplicationTests {

    @Autowired
    ArticleService articleService;

    @Test
    void findById() {
        articleService.findById("1000308668");
    }

    @Test
    void test(){
        Integer year = 2020;
        System.out.println(year.toString() + "-01-01");
    }
}