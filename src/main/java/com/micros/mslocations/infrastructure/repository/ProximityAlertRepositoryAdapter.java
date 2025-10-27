package com.micros.mslocations.infrastructure.repository;

import com.micros.mslocations.domain.model.ProximityAlert;
import com.micros.mslocations.domain.port.ProximityAlertRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProximityAlertRepositoryAdapter implements ProximityAlertRepositoryPort {

    private final ProximityAlertRepository proximityAlertRepository;

    public ProximityAlertRepositoryAdapter(ProximityAlertRepository proximityAlertRepository) {
        this.proximityAlertRepository = proximityAlertRepository;
    }

    @Override
    public ProximityAlert save(ProximityAlert alert) {
        return proximityAlertRepository.save(alert);
    }

    @Override
    public List<ProximityAlert> findByUserId1(Long userId1) {
        return proximityAlertRepository.findByUserId1(userId1);
    }

    @Override
    public List<ProximityAlert> findByUserId2(Long userId2) {
        return proximityAlertRepository.findByUserId2(userId2);
    }
}