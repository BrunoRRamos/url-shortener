package com.br.shortener_url.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "shortUrl")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "auto_increment", allocationSize = 1)
    @JsonProperty("id")
    private Long id;

    @Column(name = "longUrl", nullable = false, length = 2048)
    @JsonProperty("longUrl")
    private String longUrl;

    @Column(name = "shortUrl", nullable = false)
    @JsonProperty("shortUrl")
    private String shortUrl;

    @Column(name = "createdAt", nullable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
}
