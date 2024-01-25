package org.weinze.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weinze.clients.VisaClient;
import org.weinze.clients.dto.ResponseDTO;

@RestController
@RequestMapping
public class HelloWorldController {

    @Autowired
    private VisaClient visaClient;

    @GetMapping("/helloworld")
    public ResponseEntity<ResponseDTO> helloworld() {
        return ResponseEntity.ok(visaClient.helloworld());
    }
}
