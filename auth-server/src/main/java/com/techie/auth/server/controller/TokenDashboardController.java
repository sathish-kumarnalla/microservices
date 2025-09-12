package com.techie.auth.server.controller;

import com.techie.auth.server.dto.TokenInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TokenDashboardController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/dashboard/tokens")
    public List<TokenInfoDTO> getTokens(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {

        String sql = """
                    SELECT registered_client_id, principal_name, authorization_grant_type,
                           access_token_issued_at, access_token_expires_at,
                           access_token_type, access_token_value
                    FROM oauth2_authorization
                    WHERE access_token_expires_at > CURRENT_TIMESTAMP
                    ORDER BY access_token_expires_at DESC
                    LIMIT ? OFFSET ?
                """;

        return jdbcTemplate.query(sql, new Object[]{size, page * size}, (rs, rowNum) -> mapToken(rs));
    }

    private TokenInfoDTO mapToken(ResultSet rs) throws SQLException {
        return new TokenInfoDTO(
                rs.getString("registered_client_id"),
                rs.getString("principal_name"),
                rs.getString("authorization_grant_type"),
                rs.getTimestamp("access_token_issued_at").toInstant(),
                rs.getTimestamp("access_token_expires_at").toInstant(),
                rs.getString("access_token_type"),
                rs.getString("access_token_value")
        );
    }
}