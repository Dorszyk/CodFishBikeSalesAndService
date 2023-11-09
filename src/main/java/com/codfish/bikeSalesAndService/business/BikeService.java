package com.codfish.bikeSalesAndService.business;

import com.codfish.bikeSalesAndService.business.dao.BikeToBuyDAO;
import com.codfish.bikeSalesAndService.business.dao.BikeToServiceDAO;
import com.codfish.bikeSalesAndService.domain.BikeHistory;
import com.codfish.bikeSalesAndService.domain.BikeToBuy;
import com.codfish.bikeSalesAndService.domain.BikeToService;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BikeService {

    private final BikeToBuyDAO bikeToBuyDAO;
    private final BikeToServiceDAO bikeToServiceDAO;


    @Transactional
    public List<BikeToBuy> findAvailableBike() {
        List<BikeToBuy> availableBike = bikeToBuyDAO.findAvailable();
        log.info("Available bike: [{}]", availableBike.size());
        return availableBike;
    }

    @Transactional
    public BikeToBuy findBikeToBuy(String serial) {
        Optional<BikeToBuy> bikeToBuySerial = bikeToBuyDAO.findBikeToBuyBySerial(serial);
        if (bikeToBuySerial.isEmpty()) {
            throw new NotFoundException("Could not find bike by serial: [%s]".formatted(serial));
        }
        return bikeToBuySerial.get();
    }

    @Transactional
    public Optional<BikeToService> findBikeToService(String serial) {
        return bikeToServiceDAO.findBikeToServiceBySerial(serial);
    }

    @Transactional
    public BikeToService savedBikeToService(BikeToBuy bikeToBuy) {
        BikeToService bikeToService = BikeToService.builder()
                .serial(bikeToBuy.getSerial())
                .brand(bikeToBuy.getBrand())
                .model(bikeToBuy.getModel())
                .year(bikeToBuy.getYear())
                .build();
        return bikeToServiceDAO.saveBikeToService(bikeToService);
    }

    @Transactional
    public BikeToService saveBikeToService(BikeToService bike) {
        return bikeToServiceDAO.saveBikeToService(bike);
    }

    public List<BikeToService> findAllBikesWithHistory() {
        List<BikeToService> allBikes = bikeToServiceDAO.findAll();
        log.info("Bike to show history: [{}]", allBikes.size());
        return allBikes;
    }

    public BikeHistory findBikeHistoryBySerial(String serial) {
        return bikeToServiceDAO.findBikeHistoryBySerial(serial);
    }

}
