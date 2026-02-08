package com.codfish.bikeSalesAndService.infrastructure.database.repository.mapper;


import com.codfish.bikeSalesAndService.domain.BikeHistory;
import com.codfish.bikeSalesAndService.domain.BikeServiceRequest;
import com.codfish.bikeSalesAndService.domain.BikeToService;
import com.codfish.bikeSalesAndService.domain.Part;
import com.codfish.bikeSalesAndService.domain.Service;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.BikeServiceRequestEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePartEntity;
import com.codfish.bikeSalesAndService.infrastructure.database.entity.ServicePersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BikeServiceRequestEntityMapper {

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "bike", ignore = true)
    @Mapping(target = "servicePersons", ignore = true)
    @Mapping(target = "servicePart", ignore = true)
    BikeServiceRequest mapFromEntity(BikeServiceRequestEntity entity);

    default BikeHistory.BikeServiceRequest mapToBikeHistoryRequest(BikeServiceRequestEntity request) {
        if (request == null) {
            return null;
        }
        return BikeHistory.BikeServiceRequest.builder()
                .bikeServiceRequestNumber(request.getBikeServiceRequestNumber())
                .receivedDateTime(request.getReceivedDateTime())
                .completedDateTime(request.getCompletedDateTime())
                .customerComment(request.getCustomerComment())
                .technicianComment(request.getServicePerson().stream()
                        .map(ServicePersonEntity::getComment)
                        .filter(comment -> comment != null && !comment.isBlank() && !"No comment".equalsIgnoreCase(comment))
                        .distinct()
                        .collect(java.util.stream.Collectors.joining("; ")))
                .services(request.getServicePerson().stream()
                        .map(ServicePersonEntity::getService)
                        .map(service -> Service.builder()
                                .serviceCode(service.getServiceCode())
                                .description(service.getDescription())
                                .price(service.getPrice())
                                .build())
                        .toList())
                .parts(request.getServicePart().stream()
                        .map(ServicePartEntity::getPart)
                        .map(part -> Part.builder()
                                .serialNumber(part.getSerialNumber())
                                .description(part.getDescription())
                                .price(part.getPrice())
                                .build())
                        .toList())
                .technicians(request.getServicePerson().stream()
                        .map(ServicePersonEntity::getPersonRepairing)
                        .map(person -> person.getCodeNameSurname())
                        .distinct()
                        .toList())
                .build();
    }

    default BikeServiceRequest mapFromEntityWithCustomer(BikeServiceRequestEntity entity) {
        if (entity == null) {
            return null;
        }
        BikeServiceRequest request = mapFromEntity(entity);
        if (entity.getCustomer() != null) {
            request = request.withCustomer(com.codfish.bikeSalesAndService.domain.Customer.builder()
                    .email(entity.getCustomer().getEmail())
                    .name(entity.getCustomer().getName())
                    .surname(entity.getCustomer().getSurname())
                    .phone(entity.getCustomer().getPhone())
                    .build());
        }
        if (entity.getBike() != null) {
            request = request.withBike(com.codfish.bikeSalesAndService.domain.BikeToService.builder()
                    .serial(entity.getBike().getSerial())
                    .build());
        }
        return request;
    }

    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "customer.bikeServiceRequests", ignore = true)
    BikeServiceRequestEntity mapToEntity(BikeServiceRequest request);
}
