package com.br.shortener_url.controller;

import com.br.shortener_url.dto.InputData;
import com.br.shortener_url.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RestController
@RequestMapping(value = "/shortener", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody InputData data) {
        String shortUrl = shortUrlService.createShortUrl(data.longUrl());

        return ResponseEntity.ok("localhost:8080/" + shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable("shortUrl") String data) {
        System.out.println("redirect to shortUrl: " + data);
        String longUrl = shortUrlService.getLongUrl(data);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }
}
