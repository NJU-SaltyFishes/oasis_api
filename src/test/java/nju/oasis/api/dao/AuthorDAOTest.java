package nju.oasis.api.dao;

import nju.oasis.api.OasisApiApplicationTests;
import nju.oasis.api.domain.AuthorES;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDAOTest extends OasisApiApplicationTests {

    int existedAuthorId = 2;

    List<String> existedName = new ArrayList<>();

    String existedAvatar = "https://dl.acm.org/pb-assets/icons/DOs/default-profile-1543932446943.svg";

    @Autowired
    AuthorDAO authorDAO;

    @Test
    void findById(){
        Optional<AuthorES> authorESOptional = authorDAO.findById(String.valueOf(existedAuthorId));
        assertTrue(authorESOptional.isPresent());
        AuthorES authorES = authorESOptional.get();
        existedName.add("Yuanwei  Chua");
        assertEquals(existedName,authorES.getName());
        assertEquals(existedAvatar,authorES.getAvatar());
    }

}