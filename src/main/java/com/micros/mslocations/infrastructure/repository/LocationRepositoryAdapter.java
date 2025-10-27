package com.micros.mslocations.infrastructure.repository;

import com.micros.mslocations.domain.model.Location;
import com.micros.mslocations.domain.port.LocationRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepositoryAdapter implements LocationRepositoryPort {

    private final LocationRepository locationRepository;

    public LocationRepositoryAdapter(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public List<Location> findAll() {
        return (List<Location>) locationRepository.findAll();
    }

    @Override
    public List<Location> findByUserId(Long userId) {
        return locationRepository.findByUserId(userId);
    }
}