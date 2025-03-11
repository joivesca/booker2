package com.onndoo.booker.payment.services;

import java.util.logging.Logger;

import com.onndoo.booker.events.OrderEvent;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/pay")
public class PaymentService {

    private static final Logger logger = Logger.getLogger("PaymentService");
    
    public PaymentService() { }
    
    @POST
    @Consumes("application/xml")
    public Response processPayment(OrderEvent order) {
        logger.info("Amount: "+order.getAmount());
        if (order.getAmount() < 1000) {
            return Response.ok().build();
        } else {
            return Response.status(401).build();
        }
    }
    
    @GET
    @Produces("text/html")
    public String getHtml() {
        return "PaymentService";
    }
}
