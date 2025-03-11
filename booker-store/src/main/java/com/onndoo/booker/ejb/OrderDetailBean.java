package com.onndoo.booker.ejb;

import java.util.List;

import com.onndoo.booker.entities.OrderDetail;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Stateless
public class OrderDetailBean extends AbstractFacade<OrderDetail> {
    @PersistenceContext(unitName="primary")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderDetailBean() {
        super(OrderDetail.class);
    }

    /**
     * Example of usage of NamedQuery
     * @param orderId
     * @return 
     */
    public List<OrderDetail> findOrderDetailByOrder(int orderId) {
        List<OrderDetail> details = getEntityManager().createNamedQuery("OrderDetail.findByOrderId").setParameter("orderId", orderId).getResultList();
        
        return details;
    }
}