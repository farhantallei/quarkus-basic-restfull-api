package id.kawahedukasi.controller;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.opencsv.exceptions.CsvValidationException;

import id.kawahedukasi.dto.FileFormDTO;
import id.kawahedukasi.service.ExportService;
import id.kawahedukasi.service.ImportService;
import id.kawahedukasi.service.ItemService;
import net.sf.jasperreports.engine.JRException;

@Path("item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

  @Inject
  ItemService itemService;

  @Inject
  ExportService exportService;

  @Inject
  ImportService importService;

  @GET
  public Response getItems() {
    return itemService.getItems();
  }

  @GET
  @Path("/export/jasper")
  @Produces("application/pdf")
  public Response exportJasperItems() throws JRException {
    return Response.ok().type("application/pdf").entity(exportService.exportJasperItems()).build();
  }

  @GET
  @Path("/export/excel")
  @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
  public Response exportExcelItems() throws IOException {
    return Response.ok().type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        .header("Content-Disposition", "attachment; filename=\"item_list.xlsx\"")
        .entity(exportService.exportExcelItems()).build();
  }

  @GET
  @Path("/export/csv")
  @Produces("text/csv")
  public Response exportCsvItems() throws IOException {
    return Response.ok().type("text/csv")
        .header("Content-Disposition", "attachment; filename=\"item_list.csv\"")
        .entity(exportService.exportCsvItems()).build();
  }

  @GET
  @Path("/{id}")
  public Response getItem(@PathParam("id") Long id) {
    return itemService.getItem(id);
  }

  @POST
  public Response createItem(Map<String, Object> request) {
    return itemService.createItem(request);
  }

  @POST
  @Path("/import/excel")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response importExcelItems(@MultipartForm FileFormDTO request) throws IOException {
    return importService.importExcelItems(request);
  }

  @POST
  @Path("/import/csv")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response importCsvItems(@MultipartForm FileFormDTO request) throws IOException, CsvValidationException {
    return importService.importCsvItems(request);
  }

  @PUT
  @Path("/{id}")
  public Response updateItem(@PathParam("id") Long id, Map<String, Object> request) {
    return itemService.updateItem(id, request);
  }

  @DELETE
  @Path("/{id}")
  public Response deleteItem(@PathParam("id") Long id) {
    return itemService.deleteItem(id);
  }
}
