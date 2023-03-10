package id.kawahedukasi.service;

import java.io.File;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

@ApplicationScoped
public class MailService {

  @Inject
  Mailer mailer;

  @Inject
  ExportService exportService;

  @Inject
  ScheduleService scheduleService;

  public void sendMail(String to) {
    mailer.send(Mail.withText(to, "Quarkus", "Hello, World!"));
  }

  public void sendExcelMail(String to) throws IOException {
    byte[] data = exportService.exportExcelItems();
    mailer.send(Mail.withText(to, "Quarkus with Attachment", "Excel File")
        .addAttachment("item_list.xlsx", data, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
  }

  public void sendCsvMail(String to) throws IOException {
    File file = exportService.exportCsvItems();
    mailer.send(Mail.withText(to, "Quarkus with Attachment", "CSV File")
        .addAttachment("item_list.csv", file, "text/csv"));
  }

  public boolean sendGeneratedPdfMail(String to) {
    byte[] pdf = scheduleService.getGeneratedPdf();
    if (pdf == null) {
      return false;
    } else {
      mailer.send(Mail.withText(to, "Quarkus with Attachment", "PDF File")
          .addAttachment("item_list.pdf", pdf, "application/pdf"));
      return true;
    }
  }
}
