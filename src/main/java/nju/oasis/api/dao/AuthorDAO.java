package nju.oasis.api.dao;


import nju.oasis.api.domain.AuthorES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AuthorDAO extends ElasticsearchRepository<AuthorES,String> {
}
