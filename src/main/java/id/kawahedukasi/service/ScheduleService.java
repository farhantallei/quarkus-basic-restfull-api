package id.kawahedukasi.service;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.scheduler.Scheduled;
import net.sf.jasperreports.engine.JRException;

@ApplicationScoped
public class ScheduleService {

  private byte[] generatedPdf;

  Logger logger = LoggerFactory.getLogger(ScheduleService.class);

  @Inject
  MailService mailService;

  @Inject
  ExportService exportService;

  @Scheduled(every = "5s")
  public void generateKawahEdukasi() {
    logger.info("KawahEdukasi_{}", LocalDateTime.now());
  }

  @Scheduled(cron = "0 5 15 ? * * *")
  public void scheduleSendExcelMail() throws IOException {
    mailService.sendExcelMail("farhan.pradana55@gmail.com");
    logger.info("SEND EXCEL EMAIL SUCCESS");
  }

  @Scheduled(cron = "0 5 15 ? * * *")
  public void scheduleSendCsvMail() throws IOException {
    mailService.sendCsvMail("farhan.pradana55@gmail.com");
    logger.info("SEND CSV EMAIL SUCCESS");
  }

  @Scheduled(cron = "15 40 16 ? * * *")
  public void scheduleCreatePdf() throws JRException {
    byte[] pdf = exportService.exportJasperItems();
    this.generatedPdf = pdf;
    logger.info("GENERATE PDF SUCCESS");
  }

  @Scheduled(cron = "30 40 16 ? * * *")
  public void scheduleSendPdfMail() {
    boolean isSent = mailService.sendGeneratedPdfMail("farhan.pradana55@gmail.com");
    if (isSent == false)
      logger.info("SEND GENERATED PDF EMAIL FAILED");
    else
      logger.info("SEND GENERATED PDF EMAIL SUCCESS");
  }

  public byte[] getGeneratedPdf() {
    return generatedPdf;
  }
}
