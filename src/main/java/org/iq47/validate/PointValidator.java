package org.iq47.validate;

import org.iq47.network.request.PointPlaceRequest;

import java.util.Optional;

public interface PointValidator {
    Optional<String> getErrorMessage(PointPlaceRequest point);
}
