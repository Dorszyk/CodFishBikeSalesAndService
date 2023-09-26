package com.codfish.bikeSalesAndService.infrastructure.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "bikeServiceRequestId")
@ToString(of = {"bikeServiceRequestId", "bikeServiceRequestNumber", "receivedDateTime", "completedDateTime", "customerComment"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bike_service_request")
public class BikeServiceRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bike_service_request_id")
    private Integer bikeServiceRequestId;

    @Column(name = "bike_service_request_number", unique = true)
    private String bikeServiceRequestNumber;

    @Column(name = "received_date_time")
    private OffsetDateTime receivedDateTime;

    @Column(name = "completed_date_time")
    private OffsetDateTime completedDateTime;

    @Column(name = "customer_comment")
    private String customerComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_to_service_id")
    private BikeToServiceEntity bike;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bikeServiceRequest")
    private Set<ServicePersonEntity> servicePersons;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bikeServiceRequest")
    private Set<ServicePartEntity> serviceParts;
}
