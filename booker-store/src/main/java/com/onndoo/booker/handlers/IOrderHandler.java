package com.onndoo.booker.handlers;

import com.onndoo.booker.events.OrderEvent;

public interface IOrderHandler  {
    
    public void onNewOrder(OrderEvent event);
    
}