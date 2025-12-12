package com.example.expensetracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JWTFlowTest {
    @Autowired MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jwt_protects_endpoints_and_allows_valid_token() throws Exception {
        // 1) register -> token
        String registerJson = """
          {
            "email":"it.jwt@example.com",
            "password":"Password123!",
            "userName":"Integration Test"
          }
        """;

        String registerResponse = mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = objectMapper.readTree(registerResponse).get("token").asText();

        // 2) protected endpoint without token -> 401
        mvc.perform(get("/api/transactions")
                        .param("start", "2025-12-01T00:00:00")
                        .param("end", "2025-12-31T23:59:59"))
                .andExpect(status().isUnauthorized());

        // 3) protected endpoint with token -> 200
        mvc.perform(get("/api/transactions")
                        .param("start", "2025-12-01T00:00:00")
                        .param("end", "2025-12-31T23:59:59")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
