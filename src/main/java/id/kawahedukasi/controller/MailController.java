package id.kawahedukasi.controller;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import id.kawahedukasi.service.MailService;

@Path("/mail")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MailController {

  @Inject
  MailService mailService;

  @POST
  public Response sendMail(Map<String, Object> request) {
    mailService.sendMail(request.get("email").toString());
    return Response.ok(Map.of("status", "SUCCESS")).build();
  }

  @POST
  @Path("/excel")
  public Response sendExcelMail(Map<String, Object> request) throws IOException {
    mailService.sendExcelMail(request.get("email").toString());
    return Response.ok(Map.of("status", "SUCCESS")).build();
  }

  @POST
  @Path("/csv")
  public Response sendCsvMail(Map<String, Object> request) throws IOException {
    mailService.sendCsvMail(request.get("email").toString());
    return Response.ok(Map.of("status", "SUCCESS")).build();
  }

  @POST
  @Path("/generated/pdf")
  public Response sendGeneratedPdfMail(Map<String, Object> request) throws IOException {
    boolean isSent = mailService.sendGeneratedPdfMail(request.get("email").toString());
    if (isSent == false)
      return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("status", "PDF NOT GENERATED YET")).build();
    else
      return Response.ok(Map.of("status", "SUCCESS")).build();
  }
}
