package com.micros.mslocations.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;
import com.micros.mslocations.domain.model.Location;
import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findByUserId(Long userId);
}