package com.test.netcracker.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Entity for customer type
 * Created by IPahomov on 16.05.2016.
 */
@Entity
@Table(name = "CUSTOMER_TYPES")
@SequenceGenerator(name = "PK", sequenceName = "customer_type_seq")
public class CustomerType implements Serializable {
    private static final long serialVersionUID = 3L;

    private Long customerTypeId;
    private TypeCaption customerTypeCaption;
    private Set<Customer> customers;

    public CustomerType() {
    }

    @Id
    @Column(name = "CUSTOMER_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK")
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    @Column(name = "CUSTOMER_TYPE_CAPTION", length = 50)
    @Enumerated(EnumType.STRING)
    public TypeCaption getCustomerTypeCaption() {
        return customerTypeCaption;
    }

    public void setCustomerTypeCaption(TypeCaption customerTypeCaption) {
        this.customerTypeCaption = customerTypeCaption;
    }

    @OneToMany(mappedBy = "customerType")
    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerType that = (CustomerType) o;

        if (customerTypeId != null ? !customerTypeId.equals(that.customerTypeId) : that.customerTypeId != null)
            return false;
        return customerTypeCaption == that.customerTypeCaption;

    }

    @Override
    public int hashCode() {
        int result = customerTypeId != null ? customerTypeId.hashCode() : 0;
        result = 31 * result + (customerTypeCaption != null ? customerTypeCaption.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CustomerType{" +
                "customerTypeId=" + customerTypeId +
                ", customerTypeCaption=" + customerTypeCaption +
                '}';
    }
}
