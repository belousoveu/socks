package com.github.belousovea.sockwarehouse.service;

import com.github.belousovea.sockwarehouse.model.entity.Socks;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelService {


    public List<Socks> getSocksList(MultipartFile file) {
        List<Socks> socksList = new ArrayList<>();

        try (
                Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Socks socks = new Socks();
                socks.setColor(row.getCell(0).getStringCellValue());
                if (socks.getColor() == null) {
                    break;
                }
                socks.setCottonContent((int) row.getCell(1).getNumericCellValue());
                socks.setQuantity((int) row.getCell(2).getNumericCellValue());
                socksList.add(socks);
            }
            return socksList;


        } catch (IOException e) {
            throw new RuntimeException(e); // TODO handle exception
        }
    }
}
