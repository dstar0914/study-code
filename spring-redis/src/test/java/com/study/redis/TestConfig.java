package com.study.redis;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest
public abstract class TestConfig {

}
