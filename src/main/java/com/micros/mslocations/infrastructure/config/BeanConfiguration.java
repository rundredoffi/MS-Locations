package com.micros.mslocations.infrastructure.config;

import com.micros.mslocations.application.service.LocationService;
import com.micros.mslocations.domain.port.LocationRepositoryPort;
import com.micros.mslocations.domain.port.ProximityAlertPublisherPort;
import com.micros.mslocations.domain.port.ProximityAlertRepositoryPort;
import com.micros.mslocations.domain.service.DistanceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public DistanceService distanceService() {
        return new DistanceService();
    }

    @Bean
    public LocationService locationService(
            LocationRepositoryPort locationRepository,
            ProximityAlertRepositoryPort proximityAlertRepository,
            ProximityAlertPublisherPort proximityAlertPublisher,
            DistanceService distanceService) {
        return new LocationService(locationRepository, proximityAlertRepository, proximityAlertPublisher, distanceService);
    }
}