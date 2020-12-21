package nju.oasis.api.config;

import io.micrometer.core.instrument.util.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static nju.oasis.api.config.Model.*;

@Configuration
public class ElasticsearchConfig {
    @Bean
    RestHighLevelClient elasticsearchClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(ELASTIC_USERNAME, ELASTIC_PASSWORD));
        RestClientBuilder restClientBuilder = RestClient.builder(createHttpHost(URI.create(ELASTIC_URI)))
                .setHttpClientConfigCallback(requestConfig -> requestConfig.setKeepAliveStrategy(
                        (response, context) -> CLIENT_KEEP_ALIVE_TIME).setDefaultCredentialsProvider(credentialsProvider));
        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
        return client;
    }

    private static HttpHost createHttpHost(URI uri) {
        if (StringUtils.isEmpty(uri.getUserInfo())) {
            return HttpHost.create(uri.toString());
        }
        try {
            return HttpHost.create(new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), uri.getPath(),
                    uri.getQuery(), uri.getFragment()).toString());
        } catch (URISyntaxException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Bean
    ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}
