package nju.oasis.api.dao;

import nju.oasis.api.OasisApiApplicationTests;
import nju.oasis.api.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.OpenOption;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ArticleDAOTest extends OasisApiApplicationTests {

    String existedArticleId = "1000308668";

    String existedArticleName = "Research on Key Technologies and System Construction of Smart Mine";



    @Autowired
    ArticleDAO articleDAO;

    @Test
    void keywordSearch() {
    }

    @Test
    void fingById(){
        Optional<Article>articleOptional = articleDAO.findById(existedArticleId);
        assertTrue(articleOptional.isPresent());
        Article article = articleOptional.get();
        assertEquals(existedArticleName,article.getTitle());
    }
}