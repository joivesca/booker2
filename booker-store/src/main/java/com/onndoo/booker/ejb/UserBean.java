package com.onndoo.booker.ejb;


import com.onndoo.booker.entities.Customer;
import com.onndoo.booker.entities.Person;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;


@Stateless
public class UserBean extends AbstractFacade<Customer> {

    @PersistenceContext(unitName="primary")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Create a new user verifying if the user already exists
     * TODO: Create custom exceptions ?
     * @param customer
     * @return 
     */
    public boolean createUser(Customer customer) {

        // check if user exists
        if (getUserByEmail(customer.getEmail()) == null) {
            super.create(customer);
            return true;
        } else {
            return false;
        }
    }

    public Person getUserByEmail(String email) {
        Query createNamedQuery = getEntityManager().createNamedQuery("Person.findByEmail");

        createNamedQuery.setParameter("email", email);

        if (createNamedQuery.getResultList().size() > 0) {
            return (Person) createNamedQuery.getSingleResult();
        }
        else {
            return null;
        }
    }

    public UserBean() {
        super(Customer.class);
    }
}