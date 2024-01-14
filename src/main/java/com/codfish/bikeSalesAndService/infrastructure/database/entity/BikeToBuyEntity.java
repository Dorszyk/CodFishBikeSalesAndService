package com.codfish.bikeSalesAndService.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "bikeToBuyId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bike_to_buy")
public class BikeToBuyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bike_to_buy_id")
    private Integer bikeToBuyId;

    @Column(name = "category")
    private String category;

    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "serial", unique = true)
    private String serial;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "production_year")
    private Integer year;

    @Column(name = "color")
    private String color;

    @Column(name = "price")
    private BigDecimal price;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "bike")
    private InvoiceEntity invoice;
    @Override
    public String toString() {
        return serial;
    }
}
