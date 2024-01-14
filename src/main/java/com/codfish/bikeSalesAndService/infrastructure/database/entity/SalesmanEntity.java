package com.codfish.bikeSalesAndService.infrastructure.database.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "salesmanId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salesman")
public class SalesmanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesman_id")
    private Integer salesmanId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "code_name_surname")
    private String codeNameSurname;

    @Column(name = "userId")
    private Integer userId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "salesman")
    private Set<InvoiceEntity> invoices;

    @Override
    public String toString() {
        return codeNameSurname;
    }
}
