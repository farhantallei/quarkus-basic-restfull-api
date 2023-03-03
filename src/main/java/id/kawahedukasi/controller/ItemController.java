package id.kawahedukasi.controller;

import java.util.Map;

import javax.transaction.Transactional;
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

import id.kawahedukasi.model.Item;

@Path("item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

  @GET
  public Response getItems() {
    return Response.status(Response.Status.OK).entity(Item.findAll().list()).build();
  }

  @GET
  @Path("/{id}")
  @Transactional
  public Response getItem(@PathParam("id") Long id) {
    Item item = Item.findById(id);

    if (item == null)
      return Response.status(Response.Status.NOT_FOUND).entity(Map.of("message", "ITEM_NOT_FOUND")).build();

    return Response.status(Response.Status.OK).entity(item).build();
  }

  @POST
  @Transactional
  public Response createItem(Map<String, Object> request) {
    Item item = new Item();

    item.name = request.get("name").toString();
    item.count = Integer.parseInt(request.get("count").toString());
    item.price = Double.parseDouble(request.get("price").toString());
    item.type = request.get("type").toString();
    item.description = request.get("description").toString();

    item.persist();

    return Response.status(Response.Status.CREATED).entity(item).build();
  }

  @PUT
  @Path("/{id}")
  @Transactional
  public Response updateItem(@PathParam("id") Long id, Map<String, Object> request) {
    Item item = Item.findById(id);

    if (item == null)
      return Response.status(Response.Status.NOT_FOUND).entity(Map.of("message", "ITEM_NOT_FOUND")).build();

    item.name = request.get("name").toString();
    item.count = Integer.parseInt(request.get("count").toString());
    item.price = Double.parseDouble(request.get("price").toString());
    item.type = request.get("type").toString();
    item.description = request.get("description").toString();

    item.persist();

    return Response.status(Response.Status.OK).entity(item).build();
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public Response deleteItem(@PathParam("id") Long id) {
    Item item = Item.findById(id);

    if (item == null)
      return Response.status(Response.Status.NOT_FOUND).entity(Map.of("message", "ITEM_NOT_FOUND")).build();

    item.delete();

    return Response.status(Response.Status.OK).entity(item).build();
  }
}
