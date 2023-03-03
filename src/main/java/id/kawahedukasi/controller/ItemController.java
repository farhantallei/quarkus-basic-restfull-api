package id.kawahedukasi.controller;

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

import id.kawahedukasi.service.ItemService;

@Path("item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

  @Inject
  ItemService itemService;

  @GET
  public Response getItems() {
    return itemService.getItems();
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
