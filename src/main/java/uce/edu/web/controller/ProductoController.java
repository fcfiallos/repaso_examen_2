package uce.edu.web.controller;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.service.IProductoService;
import uce.edu.web.service.to.ProductoTo;

@Path("/productos")
public class ProductoController {
    @Inject
    private IProductoService productoService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public Response crearProducto(@RequestBody ProductoTo productoTo) {
        this.productoService.guardar(productoTo);
        return Response.ok().build();
    }
}
