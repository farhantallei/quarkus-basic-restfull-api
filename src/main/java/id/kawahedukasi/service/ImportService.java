package id.kawahedukasi.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import id.kawahedukasi.dto.FileFormDTO;
import id.kawahedukasi.model.Item;

@ApplicationScoped
public class ImportService {

  @Transactional
  public Response importExcelItems(FileFormDTO request) throws IOException {
    ByteArrayInputStream inputStream = new ByteArrayInputStream(request.file);

    try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
      XSSFSheet sheet = workbook.getSheetAt(0);
      sheet.removeRow(sheet.getRow(0));

      List<Item> items = new ArrayList<>();

      for (Row row : sheet) {
        Item item = new Item();
        item.name = row.getCell(0).getStringCellValue();
        item.count = Double.valueOf(row.getCell(1).getNumericCellValue()).intValue();
        item.price = row.getCell(2).getNumericCellValue();
        item.type = row.getCell(3).getStringCellValue();
        item.description = row.getCell(4).getStringCellValue();
        items.add(item);
      }

      Item.persist(items);
    }
    return Response.status(Response.Status.CREATED).entity(Map.of("message", "SUCCESS")).build();
  }

  @Transactional
  public Response importCsvItems(FileFormDTO request) throws IOException, CsvValidationException {
    File file = File.createTempFile("temp", null);

    try (FileOutputStream outputStream = new FileOutputStream(file)) {
      outputStream.write(request.file);
    }

    CSVReader csvReader = new CSVReader(new FileReader(file));
    String[] nextLine;
    csvReader.skip(1);

    List<Item> items = new ArrayList<>();

    while ((nextLine = csvReader.readNext()) != null) {
      Item item = new Item();
      item.name = nextLine[0].trim();
      item.count = Integer.parseInt(nextLine[1].trim());
      item.price = Double.parseDouble(nextLine[2].trim());
      item.type = nextLine[3].trim();
      item.description = nextLine[4].trim();
      items.add(item);
    }

    Item.persist(items);
    return Response.status(Response.Status.CREATED).entity(Map.of("message", "SUCCESS")).build();
  }
}
