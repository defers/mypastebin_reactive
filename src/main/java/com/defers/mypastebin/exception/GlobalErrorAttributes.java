package com.defers.mypastebin.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(
                request, options);
        Throwable ex = this.getError(request);
        var status = getStatus(ex);
        map.put("status", status);
        map.put("message", ex.getMessage());
        return map;
    }

    private HttpStatus getStatus(Throwable ex) {
        var status = HttpStatus.BAD_REQUEST;
        if (ex instanceof UserNotFoundException) {
            return HttpStatus.NOT_FOUND;
        }
        return status;
    }
}
