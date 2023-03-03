package id.kawahedukasi.service;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import id.kawahedukasi.model.Item;

@ApplicationScoped
public class ItemService {

  public Response getItems() {
    return Response.status(Response.Status.OK).entity(Item.findAll().list()).build();
  }

  @Transactional
  public Response getItem(Long id) {
    Item item = Item.findById(id);

    if (item == null)
      return Response.status(Response.Status.NOT_FOUND).entity(Map.of("message", "ITEM_NOT_FOUND")).build();

    return Response.status(Response.Status.OK).entity(item).build();
  }

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

  @Transactional
  public Response updateItem(Long id, Map<String, Object> request) {
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

  @Transactional
  public Response deleteItem(Long id) {
    Item item = Item.findById(id);

    if (item == null)
      return Response.status(Response.Status.NOT_FOUND).entity(Map.of("message", "ITEM_NOT_FOUND")).build();

    item.delete();

    return Response.status(Response.Status.OK).entity(item).build();
  }
}
