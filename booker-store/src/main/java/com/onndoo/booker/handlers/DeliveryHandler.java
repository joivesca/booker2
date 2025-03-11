package com.onndoo.booker.handlers;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.onndoo.booker.ejb.OrderBean;
import com.onndoo.booker.ejb.OrderJMSManager;
import com.onndoo.booker.entities.CustomerOrder;
import com.onndoo.booker.events.OrderEvent;
import com.onndoo.booker.qualifiers.Paid;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Observes;

@Stateless
public class DeliveryHandler implements IOrderHandler, Serializable {

    private static final Logger logger = Logger.getLogger(DeliveryHandler.class.getCanonicalName());
    private static final long serialVersionUID = 4346750267714932851L;
    
    @EJB 
    OrderBean orderBean;
    @EJB
    OrderJMSManager orderPublisher;
    
    @Override
    @Asynchronous
    public void onNewOrder(@Observes @Paid OrderEvent event) {
        
        logger.log(Level.FINEST, "{0} Event being processed by DeliveryHandler", Thread.currentThread().getName());
       
        try {           
            logger.log(Level.INFO, "Order #{0} has been paid in the amount of {1}. Order is now ready for delivery!", new Object[]{event.getOrderID(), event.getAmount()});
                                    
            orderBean.setOrderStatus(event.getOrderID(), String.valueOf(OrderBean.Status.READY_TO_SHIP.getStatus()));
            CustomerOrder order = orderBean.getOrderById(event.getOrderID());
            if (order != null) {
                orderPublisher.sendMessage(order);
               
            } else {
                throw new Exception("The order does not exist");
            }
        } catch (Exception jex) {
            logger.log(Level.SEVERE, null, jex);
        }
    }
}