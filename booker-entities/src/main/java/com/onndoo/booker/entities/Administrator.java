package com.onndoo.booker.entities;

import java.util.ArrayList;

import jakarta.persistence.Entity;

@Entity
public class Administrator extends Person {
    
    private static final long serialVersionUID = 7261229187771153310L;

    public Administrator() {
        this.groupsList = new ArrayList<Groups>();
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
        if (!(object instanceof Administrator)) {
            return false;
        }
        Administrator other = (Administrator) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.forest.entity.Administrator[ id=" + id + " ]";
    }
    
}