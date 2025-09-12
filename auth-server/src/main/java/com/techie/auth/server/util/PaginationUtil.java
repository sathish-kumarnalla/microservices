package com.techie.auth.server.util;


import com.techie.auth.server.dto.PaginatedResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class PaginationUtil {

    public static <T> PaginatedResult<T> paginate(
            JdbcTemplate jdbcTemplate,
            String countSql,
            String dataSql,
            Object[] params,
            RowMapper<T> mapper,
            int page,
            int size) {

        Integer total = jdbcTemplate.queryForObject(countSql, Integer.class);
        if (total == null) total = 0;

        List<T> data = jdbcTemplate.query(dataSql, params, mapper);

        return new PaginatedResult<>(data, page, size, total);
    }
}