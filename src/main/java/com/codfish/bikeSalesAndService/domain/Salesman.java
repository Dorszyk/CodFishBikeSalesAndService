package com.codfish.bikeSalesAndService.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "codeNameSurname")
@ToString(of = {"salesmanId", "name", "surname", "codeNameSurname", "userId", "userName", "email"})
public class Salesman {

    Integer salesmanId;
    String name;
    String surname;
    String codeNameSurname;
    Integer userId;
    String userName;
    String email;
    String password;
    String roles;
    Set<Invoice> invoices;
}
