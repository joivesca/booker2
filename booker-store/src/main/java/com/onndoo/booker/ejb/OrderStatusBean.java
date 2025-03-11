package com.onndoo.booker.ejb;

import java.io.Serializable;

import com.onndoo.booker.entities.OrderStatus;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


@Stateless
public class OrderStatusBean extends AbstractFacade<OrderStatus> implements Serializable {
    
    private static final long serialVersionUID = 5199196331433553237L;
    @PersistenceContext(unitName="primary")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderStatusBean() {
        super(OrderStatus.class);
    }

    public OrderStatus getStatusByName(String status) {
        Query createNamedQuery = getEntityManager().createNamedQuery("OrderStatus.findByStatus");

        //SELECT o FROM OrderStatus o WHERE o.status = :status
        createNamedQuery.setParameter("status", status);

        return (OrderStatus) createNamedQuery.getSingleResult();
}
    }