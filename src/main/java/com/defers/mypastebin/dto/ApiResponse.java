package com.defers.mypastebin.dto;

import com.defers.mypastebin.enums.ApiResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private ApiResponseStatus status;
    private T responseData;
    private String messageError;
}
