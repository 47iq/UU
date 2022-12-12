package org.iq47.converter;

import org.iq47.model.entity.Cart;
import org.iq47.model.entity.OrderAddress;
import org.iq47.network.CartDAO;
import org.iq47.network.OrderAddressDAO;

import java.util.stream.Collectors;

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
