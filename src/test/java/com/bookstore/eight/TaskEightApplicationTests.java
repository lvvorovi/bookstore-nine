package com.bookstore.eight;

import com.bookstore.eight.test.config.TestRedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestRedisConfig.class)
@ActiveProfiles("test")
class TaskEightApplicationTests {

    @Test
    void contextLoads() {
    }

}
