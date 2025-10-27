package com.micros.mslocations.presentation.rest;

import com.micros.mslocations.application.service.LocationService;
import com.micros.mslocations.domain.model.Location;
import com.micros.mslocations.domain.model.ProximityAlert;
import com.micros.mslocations.presentation.dto.UserLocationDTO;
import com.micros.mslocations.presentation.dto.AddLocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/locations")
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @PostMapping(path="/add")
    public @ResponseBody String addNewLocation(@RequestBody AddLocationRequest request) {
        Location location = new Location();
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        location.setUserId(request.getUserId());

        return locationService.addLocation(location);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping(path="/user/{userId}")
    public @ResponseBody UserLocationDTO getLocationsByUser(@PathVariable Long userId) {
        List<Location> locations = locationService.getLocationsByUserId(userId);
        List<ProximityAlert> alerts = locationService.getProximityAlertsByUserId(userId);

        logger.info("Récupération des infos User {} - {} locations, {} alertes",
            userId, locations.size(), alerts.size());

        return new UserLocationDTO(userId, locations, alerts);
    }

    @GetMapping(path="/{id}")
    public @ResponseBody Optional<Location> getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }
}