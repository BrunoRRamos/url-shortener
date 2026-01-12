package com.br.shortener_url.repository;

import com.br.shortener_url.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {
    Optional<ShortUrl> findByShortUrl(String shortUrl);

    @Query(value = "SELECT NEXTVAL('auto_increment')", nativeQuery = true)
    Long getNextSequenceValeue();
}
