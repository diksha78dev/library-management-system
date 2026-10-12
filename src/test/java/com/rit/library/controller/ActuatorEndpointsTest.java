package com.rit.library.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ActuatorEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /actuator returns discovery links for health, info, and metrics")
    void actuatorDiscoveryEndpointReturnsLinks() throws Exception {
        mockMvc.perform(get("/actuator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.health").exists())
                .andExpect(jsonPath("$._links.info").exists())
                .andExpect(jsonPath("$._links.metrics").exists());
    }

    @Test
    @DisplayName("GET /actuator/health returns status UP with component details")
    void actuatorHealthReturnsUp() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    @DisplayName("GET /actuator/info returns application metadata")
    void actuatorInfoReturnsAppDetails() throws Exception {
        mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.app.name").value("Library Management System"));
    }

    @Test
    @DisplayName("GET /actuator/metrics returns JVM and system meter names")
    void actuatorMetricsReturnsMeters() throws Exception {
        mockMvc.perform(get("/actuator/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.names").isArray());
    }

    @Test
    @DisplayName("GET /actuator/metrics/{metric} returns specific metric measurements")
    void actuatorMetricDetailReturnsData() throws Exception {
        mockMvc.perform(get("/actuator/metrics/jvm.memory.used"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("jvm.memory.used"))
                .andExpect(jsonPath("$.measurements").isArray());
    }
}
