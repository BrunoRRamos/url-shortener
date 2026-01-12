package com.br.shortener_url.service;

import com.br.shortener_url.model.ShortUrl;

import java.util.UUID;

public interface ShortUrlService {
    String createShortUrl(String longUrl);
    void deleteShortUrl(String shortUrl);
    String getLongUrl(String shortUrl);
}
