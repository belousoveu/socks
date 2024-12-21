package com.github.belousovea.sockwarehouse.controller;

import com.github.belousovea.sockwarehouse.TestData;
import com.github.belousovea.sockwarehouse.model.dto.SocksDto;
import com.github.belousovea.sockwarehouse.model.dto.SocksFilterDto;
import com.github.belousovea.sockwarehouse.service.GoodsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SocksController.class)
class SocksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GoodsService<SocksDto, SocksFilterDto> service;

    private SocksDto socksDto, invalideSocksDto;

    @BeforeEach
    void setUp() {
        socksDto = TestData.createTestSocksDto("Red", 75, 50);
        invalideSocksDto = TestData.createTestSocksDto(null, 150, -10);
    }

    @Test
    void test_income_success() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(socksDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/income")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).income(socksDto);
    }

    @Test
    void test_income_validationError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(invalideSocksDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/income")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[*].fieldName", containsInAnyOrder("cottonContent", "quantity", "color")))
                .andExpect(jsonPath("$.violations[*].message", containsInAnyOrder(
                        "Процент содержания хлопка не может быть больше 100",
                        "Количество должно быть больше нуля",
                        "Название не может быть пустым"
                )));

        verify(service, times(0)).income(invalideSocksDto);
    }


    @Test
    void test_outcome_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(socksDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/outcome")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).outcome(socksDto);
    }

    @Test
    void test_outcome_validationError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(invalideSocksDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/socks/outcome")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[*].fieldName", containsInAnyOrder("cottonContent", "quantity", "color")))
                .andExpect(jsonPath("$.violations[*].message", containsInAnyOrder(
                        "Процент содержания хлопка не может быть больше 100",
                        "Количество должно быть больше нуля",
                        "Название не может быть пустым"
                )));

        verify(service, times(0)).outcome(invalideSocksDto);
    }

    @Test
    void test_sumFilteredSocks_success() throws Exception {
        when(service.countFilteredGoods(any(SocksFilterDto.class))).thenReturn(100L);
        String color = "red";
        int cottonContent = 75;
        String operator = "equals";


        mockMvc.perform(MockMvcRequestBuilders.get("/api/socks")
                        .param("color", color)
                        .param("cottonContent", String.valueOf(cottonContent))
                        .param("operator", operator)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));
        verify(service).countFilteredGoods(argThat(dto ->
                dto.getColor().equals(color) &&
                        dto.getCottonContent().equals(cottonContent) &&
                        dto.getOperator().equals(operator)
        ));

    }

    @Test
    void test_sumFilteredSocks_incorrectParameter() throws Exception {
        String color = "red";
        int cottonContent = 75;
        String operator = "INVALID";


        mockMvc.perform(MockMvcRequestBuilders.get("/api/socks")
                        .param("color", color)
                        .param("cottonContent", String.valueOf(cottonContent))
                        .param("operator", operator)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }


    @Test
    void test_update_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(socksDto);
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.put("/api/socks/{id}", id)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).update(id, socksDto);

    }

    @Test
    void test_update_validationError() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(invalideSocksDto);
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.put("/api/socks/{id}", id)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[*].fieldName", containsInAnyOrder("cottonContent", "quantity", "color")))
                .andExpect(jsonPath("$.violations[*].message", containsInAnyOrder(
                        "Процент содержания хлопка не может быть больше 100",
                        "Количество должно быть больше нуля",
                        "Название не может быть пустым"
                )));

        verify(service, times(0)).update(id, invalideSocksDto);
    }


    @Test
    void test_batchInsert() throws Exception {

        MultipartFile file = TestData.getMockExcelFile(TestData.getCorrectExcelData());
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                file.getOriginalFilename(),
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                file.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/socks/batch")
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).batchInsert(any(MultipartFile.class));
    }
}