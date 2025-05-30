package com.example.projektsale;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProjektSaleApplicationTests {

    @Test
    void contextLoads() {
        // Test sprawdza czy kontekst aplikacji ładuje się poprawnie z PostgreSQL
    }

}