package com.example.expensetracker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransactionsIntegrationTest {
    private static final String AUTH_REGISTER = "/api/auth/register";
    private static final String TRANSACTIONS = "/api/transactions";

    @Autowired MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ------------------------
    // Helpers
    // ------------------------

    private String uniqueEmail() {
        return "it+" + UUID.randomUUID() + "@example.com";
    }

    private String registerAndGetToken(String email) throws Exception {
        // Your RegisterRequest uses "userName" (not fullName), per your logs
        String registerJson = """
            {
              "email": "%s",
              "password": "Password123!",
              "userName": "Integration Test"
            }
            """.formatted(email);

        String body = mvc.perform(post(AUTH_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token", not(emptyString())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(body);
        return node.get("token").asText();
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }

    private String txJson(String type, BigDecimal amount, String category, String description, LocalDateTime dateTime) {
        // Ensure dateTime uses ISO-8601 format like "2025-12-01T10:00:00"
        return """
            {
              "type": "%s",
              "amount": %s,
              "category": "%s",
              "description": "%s",
              "dateTime": "%s"
            }
            """.formatted(
                type,
                amount.toPlainString(),
                category,
                description == null ? "" : description.replace("\"", "\\\""),
                dateTime.toString()
        );
    }

    // ------------------------
    // Tests: Auth guard behavior
    // ------------------------

    @Test
    void listTransactions_withoutToken_returns401() throws Exception {
        mvc.perform(get(TRANSACTIONS)
                        .param("start", "2025-12-01T00:00:00")
                        .param("end", "2025-12-31T23:59:59"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createTransaction_withoutToken_returns401() throws Exception {
        String payload = txJson("EXPENSE", new BigDecimal("12.34"), "GROCERIES", "no auth", LocalDateTime.parse("2025-12-10T10:00:00"));

        mvc.perform(post(TRANSACTIONS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listTransactions_withValidToken_returns200_andJsonArray() throws Exception {
        String token = registerAndGetToken(uniqueEmail());

        mvc.perform(get(TRANSACTIONS)
                        .header("Authorization", bearer(token))
                        .param("start", "2025-12-01T00:00:00")
                        .param("end", "2025-12-31T23:59:59"))
                .andExpect(status().isOk())
                // could be [] initially
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(java.util.List.class)));
    }

    @Test
    void createExpense_thenListInRange_containsCreatedTransaction() throws Exception {
        String token = registerAndGetToken(uniqueEmail());

        String payload = txJson(
                "EXPENSE",
                new BigDecimal("45.50"),
                "GROCERIES",
                "Weekly groceries",
                LocalDateTime.parse("2025-12-05T10:00:00")
        );

        // Create
        mvc.perform(post(TRANSACTIONS)
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.transaction.id", notNullValue()))
                .andExpect(jsonPath("$.transaction.type", is("EXPENSE")))
                .andExpect(jsonPath("$.transaction.amount", is(45.50)))
                .andExpect(jsonPath("$.transaction.category", is("GROCERIES")))
                .andExpect(jsonPath("$.transaction.description", is("Weekly groceries")))
                .andExpect(jsonPath("$.transaction.dateTime", is("2025-12-05T10:00:00")));

        // List and verify it appears
        mvc.perform(get(TRANSACTIONS)
                        .header("Authorization", bearer(token))
                        .param("start", "2025-12-05T00:00:00")
                        .param("end", "2025-12-06T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                // at least one item matches
                .andExpect(jsonPath("$[*].category", hasItem("GROCERIES")))
                .andExpect(jsonPath("$[*].amount", hasItem(45.50)));
    }

    @Test
    void createIncome_thenSummaryListInRange_containsIncomeTransaction() throws Exception {
        String token = registerAndGetToken(uniqueEmail());

        String payload = txJson(
                "INCOME",
                new BigDecimal("1000.00"),
                "SALARY",
                "Paycheck",
                LocalDateTime.parse("2025-12-03T09:00:00")
        );

        mvc.perform(post(TRANSACTIONS)
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction.type", is("INCOME")))
                .andExpect(jsonPath("$.transaction.amount", is(1000.00)))
                .andExpect(jsonPath("$.transaction.category", is("SALARY")));

        mvc.perform(get(TRANSACTIONS)
                        .header("Authorization", bearer(token))
                        .param("start", "2025-12-01T00:00:00")
                        .param("end", "2025-12-31T23:59:59"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].type", hasItem("INCOME")))
                .andExpect(jsonPath("$[*].category", hasItem("SALARY")));
    }

    // ------------------------
    // Tests: Validation errors (expects 400)
    // ------------------------

    @Test
    void createTransaction_missingRequiredFields_returns400() throws Exception {
        String token = registerAndGetToken(uniqueEmail());

        // missing: type, amount, category, dateTime
        String badPayload = """
            { "description": "bad request" }
            """;

        mvc.perform(post(TRANSACTIONS)
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTransaction_amountTooSmall_returns400() throws Exception {
        String token = registerAndGetToken(uniqueEmail());

        String payload = txJson(
                "EXPENSE",
                new BigDecimal("0.00"),
                "GROCERIES",
                "Invalid amount",
                LocalDateTime.parse("2025-12-05T10:00:00")
        );

        mvc.perform(post(TRANSACTIONS)
                        .header("Authorization", bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listTransactions_missingDateParams_returns400() throws Exception {
        String token = registerAndGetToken(uniqueEmail());

        mvc.perform(get(TRANSACTIONS)
                        .header("Authorization", bearer(token)))
                .andExpect(status().isBadRequest());
    }

    // ------------------------
    // Tests: Authorization / ownership (optional but recommended)
    // ------------------------
    // This assumes your service always uses the authenticated user id
    // (not a userId passed from client). If so, user A should never see user B's data.

    @Test
    void userA_cannotSee_userB_transactions() throws Exception {
        String tokenA = registerAndGetToken(uniqueEmail());
        String tokenB = registerAndGetToken(uniqueEmail());

        // User B creates a transaction
        String payloadB = txJson(
                "EXPENSE",
                new BigDecimal("9.99"),
                "COFFEE",
                "User B coffee",
                LocalDateTime.parse("2025-12-02T08:00:00")
        );

        mvc.perform(post(TRANSACTIONS)
                        .header("Authorization", bearer(tokenB))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadB))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction.category", is("COFFEE")));

        // User A lists same range and should not see B
        mvc.perform(get(TRANSACTIONS)
                        .header("Authorization", bearer(tokenA))
                        .param("start", "2025-12-01T00:00:00")
                        .param("end", "2025-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].transaction.category", not(hasItem("COFFEE"))));
    }
}
