package com.micros.mslocations.domain.port;

import com.micros.mslocations.domain.model.ProximityAlert;

public interface ProximityAlertPublisherPort {
    void publishProximityAlert(ProximityAlert alert);
}