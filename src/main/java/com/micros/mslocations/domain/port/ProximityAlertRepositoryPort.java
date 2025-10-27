package com.micros.mslocations.domain.port;

import com.micros.mslocations.domain.model.ProximityAlert;
import java.util.List;

public interface ProximityAlertRepositoryPort {
    ProximityAlert save(ProximityAlert alert);
    List<ProximityAlert> findByUserId1(Long userId1);
    List<ProximityAlert> findByUserId2(Long userId2);
}