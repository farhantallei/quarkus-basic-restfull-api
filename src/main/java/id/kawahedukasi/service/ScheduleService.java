package id.kawahedukasi.service;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class ScheduleService {

  Logger logger = LoggerFactory.getLogger(ScheduleService.class);

  @Inject
  MailService mailService;

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
}
