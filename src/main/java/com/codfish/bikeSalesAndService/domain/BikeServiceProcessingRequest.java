package com.codfish.bikeSalesAndService.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Objects;

@With
@Value
@Builder
public class BikeServiceProcessingRequest {

    String personRepairingCodeNameSurname;
    String bikeSerial;
    String partSerialNumber;
    String description;
    Integer partQuantity;
    String serviceCode;
    Integer hours;
    String comment;
    Boolean done;

    public boolean partNotIncluded() {
        return Objects.isNull(getPartSerialNumber())
                || Objects.isNull(getPartQuantity())
                || Part.NONE.equals(getPartSerialNumber());
    }
}
