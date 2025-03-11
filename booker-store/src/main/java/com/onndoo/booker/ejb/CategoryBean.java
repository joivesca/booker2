package com.onndoo.booker.ejb;



import com.onndoo.booker.entities.Category;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Stateless
public class CategoryBean extends AbstractFacade<Category> {
    @PersistenceContext(unitName="primary")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryBean() {
        super(Category.class);
    }

}