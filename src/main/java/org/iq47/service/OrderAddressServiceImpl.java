package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.OrderAddressDAOConverter;
import org.iq47.repository.OrderAddressRepository;
import org.iq47.repository.UserRepository;
import org.iq47.model.entity.order.OrderAddress;
import org.iq47.model.entity.user.User;
import org.iq47.network.OrderAddressDAO;
import org.iq47.network.request.OrderAddressCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderAddressServiceImpl implements OrderAddressService{
    private OrderAddressRepository orderAddressRepository;

    private UserRepository userRepository;

    public OrderAddressServiceImpl(OrderAddressRepository orderAddressRepository, UserRepository userRepository) {
        this.orderAddressRepository = orderAddressRepository;
        this.userRepository = userRepository;
    }

    public OrderAddressDAO createOrderAddress(int userId, OrderAddressCreateRequest request) {
        User user = userRepository.getById(userId);

        OrderAddress address = new OrderAddress();
        address.setName(request.getName());
        address.setDescription(request.getDescription());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongtitude());
        address.setUser(user);
        address.setOrders(new ArrayList<>());

        address = orderAddressRepository.save(address);

        return OrderAddressDAOConverter.entityToDto(address);
    }

    public List<OrderAddressDAO> getAllOrderAddressesByUser(int userId) {
        return orderAddressRepository.findAll()
                .stream()
                .map(OrderAddressDAOConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public OrderAddressDAO getAllOrderAddressById(int userId, int addrId) {
        return orderAddressRepository.findById(addrId).map(OrderAddressDAOConverter::entityToDto).orElse(null);
    }


}
