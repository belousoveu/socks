package com.github.belousovea.sockwarehouse.service;

import com.github.belousovea.sockwarehouse.TestData;
import com.github.belousovea.sockwarehouse.exception.FileReadingException;
import com.github.belousovea.sockwarehouse.model.entity.Socks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelServiceTest {

    private ExcelService excelService;

    @BeforeEach
    void setUp() {
        excelService = new ExcelService();
    }

    @Test
    void test_getSocksList_withValidExcelFile() {
        byte[] data = TestData.getCorrectExcelData();
        MockMultipartFile file = TestData.getMockExcelFile(data);

        List<Socks> actual = excelService.getSocksList(file);

        assertNotNull(actual);
        assertEquals(4, actual.size());
        assertEquals("Red", actual.get(0).getColor());
        assertEquals(50, actual.get(0).getCottonContent());

    }

    @Test
    void test_getSocksList_withInvalidExcelFile() {
        MockMultipartFile file = TestData.getMockExcelFile("Invalid Data".getBytes());

        assertThrows(FileReadingException.class, () -> excelService.getSocksList(file));

    }

    @Test
    void test_getSocksList_withEmptyExcelFile() {
        byte[] data = new byte[0];
        MockMultipartFile file = TestData.getMockExcelFile(data);

        assertThrows(FileReadingException.class, () -> excelService.getSocksList(file));
    }

    @Test
    void test_getSocksList_withIncorrectData() {
        byte[] data = TestData.getIncorrectExcelData();
        MockMultipartFile file = TestData.getMockExcelFile(data);

        List<Socks> actual = excelService.getSocksList(file);

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals("Blue", actual.get(0).getColor());
        assertEquals(70, actual.get(0).getCottonContent());

    }


}