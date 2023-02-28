package com.defers.mypastebin.util;

import com.defers.mypastebin.domain.User;

public interface TestRepo {
    User findByUsername(String username);
}
