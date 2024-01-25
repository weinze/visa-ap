package org.weinze.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.weinze.clients.dto.ResponseDTO;

@Component
public class VisaClient {

    private static final String HELLOWORLD_PATH = "/vdp/helloworld";

    @Autowired
    private RestTemplate restTemplate;

    public ResponseDTO helloworld() {
        return restTemplate.getForObject(HELLOWORLD_PATH, ResponseDTO.class);
    }
}
