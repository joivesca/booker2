package com.onndoo.booker.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.onndoo.booker.entities.CustomerOrder;
import com.onndoo.booker.entities.Groups;
import com.onndoo.booker.entities.OrderDetail;
import com.onndoo.booker.entities.OrderDetailPK;
import com.onndoo.booker.entities.OrderStatus;
import com.onndoo.booker.entities.Person;
import com.onndoo.booker.entities.Product;
import com.onndoo.booker.events.OrderEvent;
import com.onndoo.booker.qualifiers.LoggedIn;
import com.onndoo.booker.web.util.JsfUtil;
import com.onndoo.booker.web.util.PageNavigation;


import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named(value = "shoppingCart")
@ConversationScoped
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 3313992336071349028L;
    @Inject
    Conversation conversation;
    @EJB
    OrderBean facade;
    @Inject
    @LoggedIn
    Person user;
    private static final Logger LOGGER = Logger.getLogger(ShoppingCart.class.getCanonicalName());
    private List<Product> cartItems;
    @EJB
    EventDispatcherBean eventDispatcher;

    public void init() {
        cartItems = new ArrayList<>();
    }

    public String addItem(final Product p) {

        if (cartItems == null) {
            cartItems = new ArrayList<>();
            if (conversation.isTransient()) {
                conversation.begin();
            }
        }

        LOGGER.log(Level.FINEST, "Adding product {0}", p.getName());
        LOGGER.log(Level.FINEST, "Cart Size: {0}", cartItems.size());

        if (!cartItems.contains(p)) {
            cartItems.add(p);
        }

        return "";
    }

    public boolean removeItem(Product p) {
        if (cartItems.contains(p)) {
            return cartItems.remove(p);
        } else {
            // no items removed
            return false;
        }
    }

    public double getTotal() {
        if (cartItems == null || cartItems.isEmpty()) {
            return 0f;
        }

        double total = 0f;
        for (Product item : cartItems) {
            total += item.getPrice();
        }

        LOGGER.log(Level.FINEST, "Actual Total:{0}", total);
        return total;
    }

    /**
     * This annotation will mark the ejb to be removed from memory
     */
    public PageNavigation checkout() {

        if (user == null) {
            JsfUtil.addErrorMessage(JsfUtil.getStringFromBundle("bundles.Bundle", "LoginBeforeCheckout"));

        } else {

            for (Groups g : user.getGroupsList()) {
                if (g.getName().equals("ADMINS")) {

                    JsfUtil.addErrorMessage(JsfUtil.getStringFromBundle("bundles.Bundle", "AdministratorNotAllowed"));
                    return PageNavigation.INDEX;
                }
            }

            CustomerOrder order = new CustomerOrder();
            List<OrderDetail> details = new ArrayList<>();

            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setId(1); //by default the initial status

            order.setDateCreated(Calendar.getInstance().getTime());
            order.setOrderStatus(orderStatus);
            order.setAmount(getTotal());
            order.setCustomer(user);

            facade.create(order);

            for (Product p : getCartItems()) {
                OrderDetail detail = new OrderDetail();

                OrderDetailPK pk = new OrderDetailPK(order.getId(), p.getId());
                //TODO: next version will handle qty on shoppingCart 
                detail.setQty(1);
                detail.setProduct(p);
                //detail.setCustomerOrder(order);
                detail.setOrderDetailPK(pk);

                details.add(detail);
            }

            order.setOrderDetailList(details);
            facade.edit(order);

            OrderEvent event = orderToEvent(order);

            LOGGER.log(Level.FINEST, "{0} Sending event from ShoppingCart", Thread.currentThread().getName());
            eventDispatcher.publish(event);

            JsfUtil.addSuccessMessage(JsfUtil.getStringFromBundle("bundles.Bundle", "Cart_Checkout_Success"));
            clear();
        }


        return PageNavigation.INDEX;
    }

    public void clear() {
        cartItems.clear();
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public Conversation getConversation() {
        return conversation;
    }

    private OrderEvent orderToEvent(CustomerOrder order) {
        OrderEvent event = new OrderEvent();

        event.setAmount(order.getAmount());
        event.setCustomerID(order.getCustomer().getId());
        event.setDateCreated(order.getDateCreated());
        event.setStatusID(order.getOrderStatus().getId());
        event.setOrderID(order.getId());

        return event;
    }
}