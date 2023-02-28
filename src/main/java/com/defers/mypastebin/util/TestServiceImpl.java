package com.defers.mypastebin.util;

import com.defers.mypastebin.domain.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestRepo testRepo;

    @Override
    public User findByUsername(String username) {
        return testRepo.findByUsername(username);
    }
}
