package com.defers.mypastebin.util;

import com.defers.mypastebin.dto.ApiResponse;
import com.defers.mypastebin.enums.ApiResponseStatus;

public class HttpUtils {

    private HttpUtils() {

    }

    public static <T> ApiResponse createApiResponse(T data, ApiResponseStatus status) {
        return ApiResponse.builder()
                .status(status)
                .responseData(data)
                .build();
    }
}
