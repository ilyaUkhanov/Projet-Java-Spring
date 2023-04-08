package com.g6.nfp121.entities;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class Contact {

    public Contact() {
        this.addresses = new ArrayList<>();
    }

    @Id
    @EqualsAndHashCode.Include()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String firstname;
    String lastname;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "contact_addresses",
            joinColumns = @JoinColumn(name = "checkpoint_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id"))
    private List<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Email> emails;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Contact)) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(this.getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public void addAddress(Address address) {
        address.addContact(this);
        this.addresses.add(address);
    }
    public Contact removeAddress(Address address) {
        this.getAddresses().remove(address);
        address.removeContact(this);
        return this;
    }

    public Contact addEmail(Email email) {
        this.emails.add(email);
        return this;
    }

    public Contact removeEmail(Email email) {
        this.emails.remove(email);
        return this;
    }
}
