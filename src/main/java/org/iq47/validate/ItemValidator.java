package org.iq47.validate;

import org.iq47.network.request.ItemPlaceRequest;

import java.util.Optional;

public interface ItemValidator {
    Optional<String> getErrorMessage(ItemPlaceRequest point);
}
