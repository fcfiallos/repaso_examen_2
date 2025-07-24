package uce.edu.web.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.service.IVentaService;
import uce.edu.web.service.to.ValidacionStockTo;
import uce.edu.web.service.to.VentaProdTo;

@Path("/ventas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VentaController {
    
    @Inject
    private IVentaService ventaService;

    /**
     * Endpoint para validar stock de un producto antes de agregarlo a la venta
     * GET /ventas/validar-stock?codigoBarras=123&cantidad=5
     */
    @GET
    @Path("/validar-stock")
    public Response validarStock(@QueryParam("codigoBarras") String codigoBarras, 
                                @QueryParam("cantidad") Integer cantidad) {
        try {
            if (codigoBarras == null || cantidad == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Código de barras y cantidad son requeridos\"}")
                    .build();
            }

            ValidacionStockTo validacion = ventaService.validarStock(codigoBarras, cantidad);
            
            if (validacion.isStockDisponible()) {
                return Response.ok(validacion).build();
            } else {
                return Response.status(Response.Status.CONFLICT)
                    .entity(validacion)
                    .build();
            }
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\": \"" + e.getMessage() + "\"}")
                .build();
        }
    }

    /**
     * Endpoint para crear una venta completa
     * POST /ventas
     */
    @POST
    public Response crearVenta(VentaProdTo ventaProdTo) {
        try {
            if (ventaProdTo == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Datos de venta son requeridos\"}")
                    .build();
            }

            if (ventaProdTo.getNumeroVenta() == null || ventaProdTo.getCedulaCliente() == null || 
                ventaProdTo.getProductos() == null || ventaProdTo.getProductos().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Número de venta, cédula del cliente y productos son requeridos\"}")
                    .build();
            }

            ventaService.crearVenta(ventaProdTo);
            
            return Response.status(Response.Status.CREATED)
                .entity("{\"mensaje\": \"Venta creada exitosamente\", \"numeroVenta\": \"" + ventaProdTo.getNumeroVenta() + "\"}")
                .build();
                
        } catch (RuntimeException e) {
            return Response.status(Response.Status.CONFLICT)
                .entity("{\"error\": \"" + e.getMessage() + "\"}")
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\": \"Error interno del servidor: " + e.getMessage() + "\"}")
                .build();
        }
    }
}
