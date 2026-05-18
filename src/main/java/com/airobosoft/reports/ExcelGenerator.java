package com.airobosoft.reports;

import com.airobosoft.dto.TrainDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class ExcelGenerator {

    public static ByteArrayInputStream generateExcel(
            List<TrainDTO> trains) {

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out =
                        new ByteArrayOutputStream()
        ) {

            Sheet sheet = workbook.createSheet("Trains");

            // Header Row
            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Train No");
            headerRow.createCell(2).setCellValue("Name");
            headerRow.createCell(3).setCellValue("Coaches");

            int rowIdx = 1;

            for (TrainDTO train : trains) {

                Row row = sheet.createRow(rowIdx++);

                row.createCell(0)
                        .setCellValue(train.getId());

                row.createCell(1)
                        .setCellValue(train.getTrainNo());

                row.createCell(2)
                        .setCellValue(train.getName());

                row.createCell(3)
                        .setCellValue(train.getCoaches());
            }

            // Auto size columns
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);

            return new ByteArrayInputStream(
                    out.toByteArray()
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to export Excel file",
                    e
            );
        }
    }
}
