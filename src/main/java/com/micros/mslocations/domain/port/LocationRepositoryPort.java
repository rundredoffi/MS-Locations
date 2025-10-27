package com.micros.mslocations.domain.port;

import com.micros.mslocations.domain.model.Location;
import java.util.List;
import java.util.Optional;

public interface LocationRepositoryPort {
    Location save(Location location);
    Optional<Location> findById(Long id);
    List<Location> findAll();
    List<Location> findByUserId(Long userId);
}