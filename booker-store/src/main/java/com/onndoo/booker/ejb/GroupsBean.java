package com.onndoo.booker.ejb;

import com.onndoo.booker.entities.Groups;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class GroupsBean extends AbstractFacade<Groups> {
    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupsBean() {
        super(Groups.class);
    }
    
}