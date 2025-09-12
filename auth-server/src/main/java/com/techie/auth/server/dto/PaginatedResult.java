package com.techie.auth.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResult<T> {
    private List<T> content;
    private int page;
    private int size;
    private int total;
}
