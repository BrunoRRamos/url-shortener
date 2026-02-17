package com.br.shortener_url.controller;

import com.br.shortener_url.dto.InputData;
import com.br.shortener_url.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Validated
@RestController
@RequestMapping(value = "/api/shorten", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @Value("${BASE_URL:localhost:8080}")
    private String baseUrl;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody InputData data) {
        String shortUrl = shortUrlService.createShortUrl(data.longUrl());
        String fullUrl = UriComponentsBuilder.fromUriString(this.baseUrl)
                .path("/{shortUrl}")
                .buildAndExpand(shortUrl)
                .toUriString();

        return ResponseEntity.ok(fullUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable("shortUrl") String data) {
        String longUrl = shortUrlService.getLongUrl(data);

        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .location(URI.create(longUrl))
                .build();
    }
}
