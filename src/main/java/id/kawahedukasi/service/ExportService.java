package id.kawahedukasi.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVWriter;

import id.kawahedukasi.model.Item;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ApplicationScoped
public class ExportService {

  public byte[] exportJasperItems() throws JRException {
    File file = new File("src/main/resources/TemplateItem.jrxml");
    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Item.listAll());

    JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

    return JasperExportManager.exportReportToPdf(jasperPrint);
  }

  public byte[] exportExcelItems() throws IOException {
    List<Item> items = Item.listAll();

    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      XSSFSheet sheet = workbook.createSheet("item-list");

      Row row = sheet.createRow(0);
      row.createCell(0).setCellValue("Id");
      row.createCell(1).setCellValue("Name");
      row.createCell(2).setCellValue("Count");
      row.createCell(3).setCellValue("Price");
      row.createCell(4).setCellValue("Type");
      row.createCell(5).setCellValue("Description");
      row.createCell(6).setCellValue("Created At");
      row.createCell(7).setCellValue("Updated At");

      for (int i = 0; i < items.size(); i++) {
        Item item = items.get(i);
        row = sheet.createRow(i + 1);
        row.createCell(0).setCellValue(item.id);
        row.createCell(1).setCellValue(item.name);
        row.createCell(2).setCellValue(item.count);
        row.createCell(3).setCellValue(item.price);
        row.createCell(4).setCellValue(item.type);
        row.createCell(5).setCellValue(item.description);
        row.createCell(6).setCellValue(item.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        row.createCell(7).setCellValue(item.updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
      }

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);

      return outputStream.toByteArray();
    }
  }

  public File exportCsvItems() throws IOException {
    List<Item> items = Item.listAll();

    File file = File.createTempFile("temp", null);
    FileWriter fileWriter = new FileWriter(file);

    try (CSVWriter csvWriter = new CSVWriter(fileWriter)) {
      String[] headers = { "Id", "Name", "Count", "Price", "Type", "Description", "Created At", "Updated At" };
      csvWriter.writeNext(headers);

      for (int i = 0; i < items.size(); i++) {
        Item item = items.get(i);
        String[] row = { item.id.toString(), item.name, item.count.toString(), item.price.toString(), item.type,
            item.description, item.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")),
            item.updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")) };
        csvWriter.writeNext(row);
      }
    }

    return file;
  }
}
