package org.weinze.configs;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyStore;

@Configuration
public class RestTemplateConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateConfig.class);

    private static final String KEYSTORE_TYPE = "JKS";

    @Autowired
    private VisaProperties visaProperties;

    @Bean
    public RestTemplate getRestTemplate() {
        try {
            final CloseableHttpClient httpClient = HttpClients
                    .custom()
                    .setSSLSocketFactory(buildSocketFactory())
                    .setMaxConnTotal(1)
                    .setMaxConnPerRoute(5)
                    .build();

            final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setReadTimeout(5000);
            requestFactory.setConnectTimeout(5000);

            return new RestTemplateBuilder()
                    .requestFactory(() -> requestFactory)
                    .rootUri(visaProperties.getRootUri())
                    .basicAuthentication(visaProperties.getProject().getUserId(), visaProperties.getProject().getPassword())
                    .build();


        } catch (Exception e) {
            LOGGER.error("Cannot create rest template", e);
            throw new RuntimeException("Exception creating restTemplate with SSL", e);
        }
    }

    private SSLConnectionSocketFactory buildSocketFactory() throws Exception {
        final KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
        final Resource resource = new ClassPathResource(visaProperties.getKeystore().getFile());
        keyStore.load(resource.getInputStream(), visaProperties.getKeystore().getPassword().toCharArray());

        return new SSLConnectionSocketFactory(
                new SSLContextBuilder()
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .loadKeyMaterial(keyStore, visaProperties.getKeystore().getPassword().toCharArray())
                        .build()
        );
    }

}
