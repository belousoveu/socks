package com.github.belousovea.sockwarehouse;

import com.github.belousovea.sockwarehouse.model.dto.SocksDto;
import com.github.belousovea.sockwarehouse.model.entity.Socks;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TestData {


    public static MockMultipartFile getMockExcelFile(byte[] bytes) {

        return new MockMultipartFile("file", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", bytes);


    }

    public static byte[] getCorrectExcelData() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Socks");

        Row row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("Red");
        row1.createCell(1).setCellValue(50);
        row1.createCell(2).setCellValue(10);

        Row row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("Blue");
        row2.createCell(1).setCellValue(70);
        row2.createCell(2).setCellValue(15);

        Row row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("Red");
        row3.createCell(1).setCellValue(50);
        row3.createCell(2).setCellValue(15);

        Row row4 = sheet.createRow(3);
        row4.createCell(0).setCellValue("Red");
        row4.createCell(1).setCellValue(80);
        row4.createCell(2).setCellValue(20);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getIncorrectExcelData() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Socks");

        Row row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("Red");
        row1.createCell(1).setCellValue("50"); //Incorrect
        row1.createCell(2).setCellValue(10);

        Row row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("Blue");
        row2.createCell(1).setCellValue(70);
        row2.createCell(2).setCellValue(15);

        Row row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("Red");
        row3.createCell(1).setCellValue(50);
        row3.createCell(2).setCellValue(15);

        Row row4 = sheet.createRow(3);
        row4.createCell(0).setCellValue(100); //Incorrect
        row4.createCell(1).setCellValue(80);
        row4.createCell(2).setCellValue(20);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SocksDto createTestSocksDto(String color, int cottonContent, int Quantity) {
        SocksDto socksDto = new SocksDto();
        socksDto.setColor(color);
        socksDto.setCottonContent(cottonContent);
        socksDto.setQuantity(Quantity);
        return socksDto;
    }

    public static Socks createTestSocks(String color, int cottonContent, int Quantity) {
        Socks socks = new Socks();
        socks.setColor(color);
        socks.setCottonContent(cottonContent);
        socks.setQuantity(Quantity);
        return socks;
    }
}
