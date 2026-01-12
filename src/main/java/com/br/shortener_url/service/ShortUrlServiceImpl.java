package com.br.shortener_url.service;

import com.br.shortener_url.model.ShortUrl;
import com.br.shortener_url.repository.ShortUrlRepository;
import com.br.shortener_url.util.HashEnconding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private HashEnconding hashEnconding;

    @Override
    public String createShortUrl(String longUrl) {
        String newShortData = hashEnconding.toBase62Hash(shortUrlRepository.getNextSequenceValeue());
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setLongUrl(longUrl);
        shortUrl.setShortUrl(newShortData);
        shortUrlRepository.save(shortUrl);
        return shortUrl.getShortUrl();
    }

    @Override
    public void deleteShortUrl(String shortUrl) {
        ShortUrl data = shortUrlRepository.findByShortUrl(shortUrl)
                .orElseThrow(RuntimeException::new);
        shortUrlRepository.delete(data);
    }

    @Override
    public String getLongUrl(String shortUrl) {
        ShortUrl data = shortUrlRepository.findByShortUrl(shortUrl)
                .orElseThrow(RuntimeException::new);
        return data.getLongUrl();
    }

}
