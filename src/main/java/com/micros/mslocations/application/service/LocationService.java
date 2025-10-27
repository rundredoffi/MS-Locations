package com.micros.mslocations.application.service;

import com.micros.mslocations.domain.model.Location;
import com.micros.mslocations.domain.model.ProximityAlert;
import com.micros.mslocations.domain.port.LocationRepositoryPort;
import com.micros.mslocations.domain.port.ProximityAlertRepositoryPort;
import com.micros.mslocations.domain.port.ProximityAlertPublisherPort;
import com.micros.mslocations.domain.service.DistanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

public class LocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
    private static final Double PROXIMITY_THRESHOLD_KM = 1.0;

    private final LocationRepositoryPort locationRepository;
    private final ProximityAlertRepositoryPort proximityAlertRepository;
    private final ProximityAlertPublisherPort proximityAlertPublisher;
    private final DistanceService distanceService;

    public LocationService(LocationRepositoryPort locationRepository,
                          ProximityAlertRepositoryPort proximityAlertRepository,
                          ProximityAlertPublisherPort proximityAlertPublisher,
                          DistanceService distanceService) {
        this.locationRepository = locationRepository;
        this.proximityAlertRepository = proximityAlertRepository;
        this.proximityAlertPublisher = proximityAlertPublisher;
        this.distanceService = distanceService;
    }

    public String addLocation(Location location) {
        location.setTimestamp(LocalDateTime.now());
        locationRepository.save(location);

        logger.info("Localisation sauvegardée - User: {}, Lat: {}, Lon: {}",
            location.getUserId(), location.getLatitude(), location.getLongitude());

        if (location.getUserId() != null) {
            checkProximity(location);
        }

        return "Location saved successfully";
    }

    public Iterable<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public List<Location> getLocationsByUserId(Long userId) {
        return locationRepository.findByUserId(userId);
    }

    public List<ProximityAlert> getProximityAlertsByUserId(Long userId) {
        List<ProximityAlert> alertsAsUser1 = proximityAlertRepository.findByUserId1(userId);
        List<ProximityAlert> alertsAsUser2 = proximityAlertRepository.findByUserId2(userId);

        List<ProximityAlert> allAlerts = new ArrayList<>();
        allAlerts.addAll(alertsAsUser1);
        allAlerts.addAll(alertsAsUser2);

        return allAlerts;
    }

    private void checkProximity(Location newLocation) {
        Iterable<Location> allLocations = locationRepository.findAll();
        Set<String> sentAlerts = new HashSet<>();

        for (Location otherLocation : allLocations) {
            // Éviter de comparer un utilisateur avec lui-même
            if (newLocation.getUserId().equals(otherLocation.getUserId())) {
                continue;
            }

            if (otherLocation.getUserId() == null || newLocation.getUserId() == null) {
                continue;
            }

            double distance = distanceService.calculateDistance(
                newLocation.getLatitude(), newLocation.getLongitude(),
                otherLocation.getLatitude(), otherLocation.getLongitude()
            );

            logger.debug("Distance entre User {} et User {}: {} km",
                newLocation.getUserId(), otherLocation.getUserId(), distance);

            if (distance <= PROXIMITY_THRESHOLD_KM) {
                String alertKey1 = Math.min(newLocation.getUserId(), otherLocation.getUserId()) + "-" +
                                   Math.max(newLocation.getUserId(), otherLocation.getUserId());

                if (!sentAlerts.contains(alertKey1)) {
                    ProximityAlert alert = new ProximityAlert(
                        newLocation.getUserId(),
                        otherLocation.getUserId(),
                        distance,
                        newLocation.getLatitude(),
                        newLocation.getLongitude(),
                        otherLocation.getLatitude(),
                        otherLocation.getLongitude()
                    );

                    proximityAlertPublisher.publishProximityAlert(alert);
                    sentAlerts.add(alertKey1);

                    logger.warn("ALERTE PROXIMITÉ: User {} et User {} sont à {} km l'un de l'autre!",
                        newLocation.getUserId(), otherLocation.getUserId(), distance);
                }
            }
        }
    }
}