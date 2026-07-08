package br.org.edu.ifrn.LojaCarro.integration;

import br.org.edu.ifrn.LojaCarro.controllers.AuthController;
import br.org.edu.ifrn.LojaCarro.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CarroControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        token = jwtUtil.generateToken("testuser");
    }

    @Test
    void acessarEndpointProtegidoSemTokenDeveRetornar401() throws Exception {
        mockMvc.perform(get("/carro")).andExpect(status().isUnauthorized());
    }

    @Test
    void acessarEndpointProtegidoComTokenDeveRetornar200() throws Exception {
        mockMvc.perform(get("/carro").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
