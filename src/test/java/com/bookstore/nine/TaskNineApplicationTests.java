package com.bookstore.nine;

import com.bookstore.nine.test.config.TestRedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestRedisConfig.class)
@ActiveProfiles("test")
class TaskNineApplicationTests {

    @Test
    void contextLoads() {
    }

}
