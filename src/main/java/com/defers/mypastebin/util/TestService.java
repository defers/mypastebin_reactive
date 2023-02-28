package com.defers.mypastebin.util;

import com.defers.mypastebin.domain.User;

public interface TestService {
    User findByUsername(String username);
}
