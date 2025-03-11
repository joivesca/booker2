package com.onndoo.booker.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.onndoo.booker.events.OrderEvent;
import com.onndoo.booker.qualifiers.New;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;


@Named("EventDisptacherBean")
@Stateless
public class EventDispatcherBean {

     private static final Logger logger = Logger.getLogger(EventDispatcherBean.class.getCanonicalName());

    
    @Inject @New
    Event<OrderEvent> eventManager;

    @Asynchronous
    public void publish(OrderEvent event) {
        logger.log(Level.FINEST, "{0} Sending event from EJB", Thread.currentThread().getName());
        eventManager.fire(event);
    }
}