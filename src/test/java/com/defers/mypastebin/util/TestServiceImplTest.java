package com.defers.mypastebin.util;

import com.defers.mypastebin.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private TestRepo testRepo;

    @InjectMocks
    private TestServiceImpl testService;

    @Test
    void findByUsername() {
        User userMocked = User.builder()
                .username("test mock")
                .password("test mock")
                .email("email1@mail.com")
                .createdDate(LocalDateTime.of(2023, 1, 10, 15, 10, 0))
                .updatedDate(LocalDateTime.of(2023, 1, 12, 14, 12, 0))
                .build();
        Mockito.when(testRepo.findByUsername(Mockito.anyString()))
                .thenReturn(userMocked);
        User user = testService.findByUsername("test mock");
    }
}