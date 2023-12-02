package com.codfish.bikeSalesAndService.api.dto.mapper;

import com.codfish.bikeSalesAndService.api.dto.BikeHistoryDTO;
import com.codfish.bikeSalesAndService.api.dto.BikeToBuyDTO;
import com.codfish.bikeSalesAndService.api.dto.BikeToServiceDTO;
import com.codfish.bikeSalesAndService.domain.BikeHistory;
import com.codfish.bikeSalesAndService.domain.BikeToBuy;
import com.codfish.bikeSalesAndService.domain.BikeToService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BikeMapper extends OffsetDateTimeMapper {

    BikeToBuyDTO map(final BikeToBuy bike);

    BikeToServiceDTO map(final BikeToService bike);

    @Mapping(source = "bikeServiceRequests", target = "bikeServiceRequests", qualifiedByName = "mapServiceRequests")
    BikeHistoryDTO map(BikeHistory bikeHistory);

    @SuppressWarnings("unused")
    @Named("mapServiceRequests")
    default List<BikeHistoryDTO.ServiceRequestDTO> mapServiceRequests(
            List<BikeHistory.BikeServiceRequest> requests
    ) {
        return requests.stream().map(this::mapServiceRequest).toList();
    }

    @Mapping(source = "receivedDateTime", target = "receivedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(source = "completedDateTime", target = "completedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    BikeHistoryDTO.ServiceRequestDTO mapServiceRequest(BikeHistory.BikeServiceRequest bikeServiceRequest);
}
