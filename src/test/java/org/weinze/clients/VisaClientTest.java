package org.weinze.clients;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.weinze.clients.dto.ResponseDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VisaClientTest {

    @Autowired
    private VisaClient visaClient;

    @Test
    public void helloworld() {
        final ResponseDTO response = visaClient.helloworld();

        assertNotNull(response);
        assertEquals("helloworld", response.getMessage());
    }

}
