package nju.oasis.api.dao;

import nju.oasis.api.OasisApiApplicationTests;
import nju.oasis.api.domain.ArticleES;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ArticleESDAOTest extends OasisApiApplicationTests {

    String existedArticleId = "1000189582";

    String existedArticleName = "Session details: Panel";



    @Autowired
    ArticleDAO articleDAO;

    @Test
    void keywordSearch() {
    }

    @Test
    void fingById(){
        Optional<ArticleES>articleOptional = articleDAO.findById(existedArticleId);
        assertTrue(articleOptional.isPresent());
        ArticleES articleES = articleOptional.get();
        assertEquals(existedArticleName, articleES.getTitle());
    }
}