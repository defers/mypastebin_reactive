package com.defers.mypastebin.dto;

import com.defers.mypastebin.enums.ApiResponseStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private ApiResponseStatus status;
    private T responseData;
    private String messageError;
}
