package com.onndoo.booker.entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.xml.bind.annotation.XmlTransient;


@Entity
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name = "Customer.findByFirstname", query = "SELECT c FROM Customer c WHERE c.firstname = :firstname"),
    @NamedQuery(name = "Customer.findByLastname", query = "SELECT c FROM Customer c WHERE c.lastname = :lastname"),
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name = "Customer.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address"),
    @NamedQuery(name = "Customer.findByCity", query = "SELECT c FROM Customer c WHERE c.city = :city")})
public class Customer extends Person {
    
    private static final long serialVersionUID = -1964261708710396652L;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<CustomerOrder> customerOrderList;

    public Customer() {
        this.customerOrderList = new ArrayList<CustomerOrder>();
        this.groupsList = new ArrayList<Groups>();
    }

    public Customer(Integer id) {
        this.id = id;
        this.customerOrderList = new ArrayList<CustomerOrder>();
        this.groupsList = new ArrayList<Groups>();
    }

    public Customer(Integer id, String firstname, String lastname, String email, String address, String city) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.city = city;
        this.customerOrderList = new ArrayList<CustomerOrder>();
        this.groupsList = new ArrayList<Groups>();
    }
    @XmlTransient
    @JsonbTransient
    public List<CustomerOrder> getCustomerOrderList() {
        return customerOrderList;
    }

    public void setCustomerOrderList(List<CustomerOrder> customerOrderList) {
        this.customerOrderList = customerOrderList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.forest.entity.Customer[id=" + id + "]";
    }
}