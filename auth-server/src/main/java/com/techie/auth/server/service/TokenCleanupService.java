package com.techie.auth.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenCleanupService {

    private final JdbcTemplate jdbcTemplate;

    public TokenCleanupService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(cron = "0 0 * * * *") // Every hour
    public void purgeExpiredTokens() {
        int deleted = jdbcTemplate.update("""
            DELETE FROM oauth2_authorization
            WHERE access_token_expires_at < CURRENT_TIMESTAMP
               OR refresh_token_expires_at < CURRENT_TIMESTAMP
        """);
        log.info("Purged {} expired tokens", deleted);
    }
}