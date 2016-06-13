package com.test.netcracker.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity for customers
 * Created by IPahomov on 16.05.2016.
 */
@Entity
@Table(name = "CUSTOMERS")
@SequenceGenerator(name = "PK", sequenceName = "customer_seq")
public class Customer implements Serializable {
    private static final long serialVersionUID = 4L;

    private Long customerId;
    private String title;
    private String firstName;
    private String firstNameMetaphone;
    private String lastName;
    private String lastNameMetaphone;
    private Date modifiedWhen;
    private CustomerType customerType;

    public Customer() {
    }

    @Id
    @Column(name = "CUSTOMER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PK")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "TITLE", length = 3)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*@NotNull(message = "Input first name")
    @Length(min = 2, message = "Minimum 2 letters")
    @Pattern(regexp = "^[A-Z]+[a-z]+$", message = "First name must be alphanumeric with no spaces and first capital")*/
    @Column(name = "FIRST_NAME", length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "FIRST_NAME_METAPHONE", length = 50)
    public String getFirstNameMetaphone() {
        return firstNameMetaphone;
    }

    public void setFirstNameMetaphone(String firstNameMetaphone) {
        this.firstNameMetaphone = firstNameMetaphone;
    }

    /*@NotNull(message = "Input Last name")
    @Length(min = 2, message = "Minimum 2 letters")
    @Pattern(regexp = "^[A-Z]+[a-z]+$", message = "Last name must be alphanumeric with no spaces and first capital")*/
    @Column(name = "LAST_NAME", length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "LAST_NAME_METAPHONE", length = 50)
    public String getLastNameMetaphone() {
        return lastNameMetaphone;
    }

    public void setLastNameMetaphone(String lastNameMetaphone) {
        this.lastNameMetaphone = lastNameMetaphone;
    }

    @Column(name = "MODIFIED_WHEN")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getModifiedWhen() {
        return modifiedWhen;
    }

    public void setModifiedWhen(Date modifiedWhen) {
        this.modifiedWhen = modifiedWhen;
    }

    @JoinColumn(name = "TYPE")
    @ManyToOne(fetch = FetchType.EAGER)
    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (customerId != null ? !customerId.equals(customer.customerId) : customer.customerId != null) return false;
        if (title != null ? !title.equals(customer.title) : customer.title != null) return false;
        if (firstName != null ? !firstName.equals(customer.firstName) : customer.firstName != null) return false;
        if (firstNameMetaphone != null ? !firstNameMetaphone.equals(customer.firstNameMetaphone) : customer.firstNameMetaphone != null)
            return false;
        if (lastName != null ? !lastName.equals(customer.lastName) : customer.lastName != null) return false;
        if (lastNameMetaphone != null ? !lastNameMetaphone.equals(customer.lastNameMetaphone) : customer.lastNameMetaphone != null)
            return false;
        if (modifiedWhen != null ? !modifiedWhen.equals(customer.modifiedWhen) : customer.modifiedWhen != null)
            return false;
        return !(customerType != null ? !customerType.equals(customer.customerType) : customer.customerType != null);

    }

    @Override
    public int hashCode() {
        int result = customerId != null ? customerId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (firstNameMetaphone != null ? firstNameMetaphone.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (lastNameMetaphone != null ? lastNameMetaphone.hashCode() : 0);
        result = 31 * result + (modifiedWhen != null ? modifiedWhen.hashCode() : 0);
        result = 31 * result + (customerType != null ? customerType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", firstNameMetaphone='" + firstNameMetaphone + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lastNameMetaphone='" + lastNameMetaphone + '\'' +
                ", modifiedWhen=" + modifiedWhen +
                ", customerType=" + customerType +
                '}';
    }
}
