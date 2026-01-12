package com.br.shortener_url.util;

import org.springframework.stereotype.Component;

@Component
public class HashEnconding {
    private final int base = 62;
    private final String base62Encode = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String toBase62Hash(Long id) {
        StringBuilder stringBuilder = new StringBuilder(1);
        do {
            stringBuilder.insert(0, base62Encode.charAt((int) (id % base)));
            id /= base;
        } while (id > 0);
        return stringBuilder.toString();
    }
}

