package com.student.student_service.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.student.student_service.collection.Student;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FileProcessingHeaderList {

	// Sheet Header Name
	public static String[] Header = { "sId", "firstName", "lastName", "middleName", "motherName", "email", "mobileNo",
			"address", "city", "state", "country", "std", "file" };

	// SheetName
	public static String sheetName = "Student_Data";

	public ByteArrayInputStream dataToExcel(List<Student> list) throws IOException {

		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			// SheetName
			Sheet sheet = workbook.createSheet(sheetName);

			// Create Row : Sheet Header
			Row row = sheet.createRow(0);
			for (int i = 0; i < Header.length; i++) {

				Cell cell = row.createCell(i);
				cell.setCellValue(Header[i]);
			}

			// Value Rows
			int rowIndex = 1;
			for (Student student : list) {
				Row dataRow = sheet.createRow(rowIndex);
				rowIndex++;
				dataRow.createCell(0).setCellValue(student.getSId());
				dataRow.createCell(1).setCellValue(student.getFirstName());
				dataRow.createCell(2).setCellValue(student.getLastName());
				dataRow.createCell(3).setCellValue(student.getMiddleName());
				dataRow.createCell(4).setCellValue(student.getMotherName());
				dataRow.createCell(5).setCellValue(student.getEmail());
				dataRow.createCell(6).setCellValue(student.getMobileNo());
				dataRow.createCell(7).setCellValue(student.getAddress());
				dataRow.createCell(8).setCellValue(student.getCity());
				dataRow.createCell(9).setCellValue(student.getState());
				dataRow.createCell(10).setCellValue(student.getCountry());
				dataRow.createCell(11).setCellValue(student.getStd());
				dataRow.createCell(12).setCellValue(student.getFile());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			workbook.close();
			out.close();
		}
	}
}
