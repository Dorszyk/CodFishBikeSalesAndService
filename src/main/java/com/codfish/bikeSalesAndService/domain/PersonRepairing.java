package com.codfish.bikeSalesAndService.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "codeNameSurname")
@ToString(of = {"personRepairingId", "name", "surname", "codeNameSurname"})

public class PersonRepairing {

    Integer personRepairingId;
    String name;
    String surname;
    String codeNameSurname;

    Set<ServicePerson> servicePerson;
}
