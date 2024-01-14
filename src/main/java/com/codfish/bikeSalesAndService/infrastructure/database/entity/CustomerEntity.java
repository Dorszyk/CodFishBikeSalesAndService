package com.codfish.bikeSalesAndService.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "customerId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<InvoiceEntity> invoices;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<BikeServiceRequestEntity> bikeServiceRequests;

    @Override
    public String toString() {
        return name + " " + surname + " - " + email;
    }
}
