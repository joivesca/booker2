package com.onndoo.booker.shipment.session;

import com.onndoo.booker.entities.Customer;
import com.onndoo.booker.entities.Person;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 *
 * @author markito
 */
@Stateless
public class UserBean extends AbstractFacade<Customer> {
    
    @PersistenceContext(unitName="primary")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Person getUserByEmail(String email) {
        Query createNamedQuery = getEntityManager().createNamedQuery("Person.findByEmail");
        
        createNamedQuery.setParameter("email", email);
        
        return (Person) createNamedQuery.getSingleResult();
    }
    
    public UserBean() {
        super(Customer.class);
    }

}