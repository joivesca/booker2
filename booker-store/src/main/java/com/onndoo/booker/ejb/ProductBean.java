package com.onndoo.booker.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.onndoo.booker.entities.Category;
import com.onndoo.booker.entities.Product;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author markito
 */
@Stateless
public class ProductBean extends AbstractFacade<Product> {

    private static final Logger logger = 
            Logger.getLogger(ProductBean.class.getCanonicalName());
    
    @PersistenceContext(unitName="primary")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductBean() {
        super(Product.class);
    }

    /**
     * Example usage of JPA CriteriaBuilder. You can also use NamedQueries
     * @param range
     * @param categoryId
     * @return 
     */
    public List<Product> findByCategory(int[] range, int categoryId) {       
         Category cat = new Category();
         cat.setId(categoryId);
         
         CriteriaBuilder qb = em.getCriteriaBuilder();
         CriteriaQuery<Product> query = qb.createQuery(Product.class);
         Root<Product> product = query.from(Product.class);
         query.where(qb.equal(product.get("category"), cat));

         List<Product> result = this.findRange(range, query);
         
         logger.log(Level.FINEST, "Product List size: {0}", result.size());
         
        return result;
    }
    
    
}