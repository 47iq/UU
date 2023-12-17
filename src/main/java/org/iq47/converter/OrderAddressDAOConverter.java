package org.iq47.converter;

import org.iq47.model.entity.order.OrderAddress;
import org.iq47.network.OrderAddressDAO;

public class OrderAddressDAOConverter {
    public static OrderAddress dtoToEntity(OrderAddressDAO orderAddressDAO) {
        return null;
    }

    public static OrderAddressDAO entityToDto(OrderAddress orderAddress) {
        return new OrderAddressDAO(
                orderAddress.getId(),
                orderAddress.getName(),
                orderAddress.getDescription(),
                orderAddress.getLongitude(),
                orderAddress.getLatitude()
                );
    }
}
