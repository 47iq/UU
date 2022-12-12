package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.OrderAddressDAOConverter;
import org.iq47.model.OrderAddressRepository;
import org.iq47.model.UserRepository;
import org.iq47.model.entity.OrderAddress;
import org.iq47.model.entity.User;
import org.iq47.network.OrderAddressDAO;
import org.iq47.network.request.OrderAddressCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderAddressServiceImpl implements OrderAddressService{
    @Autowired
    private OrderAddressRepository orderAddressRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseWrapper createOrderAddress(long userId, OrderAddressCreateRequest request) {
        User user = userRepository.getById(userId);

        OrderAddress address = new OrderAddress();
        address.setName(request.getName());
        address.setDescription(request.getDescription());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongtitude());
        address.setUser(user);
        address.setOrders(new ArrayList<>());

        orderAddressRepository.save(address);
        return new ResponseWrapper("ok");
    }

    public ResponseWrapper removeOrderAddress(long userId, long addrId) {
        /*User user = userRepository.getById(userId);

        userRepository.*/
        return null; //TODO
    }

    public List<OrderAddressDAO> getAllOrderAddressesByUser(long userId) {
        User user = userRepository.getById(userId);

        return orderAddressRepository.findAll()
                .stream()
                .map(OrderAddressDAOConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public OrderAddressDAO getAllOrderAddressById(long userId, long addrId) {
        User user = userRepository.getById(userId);

        return orderAddressRepository.findById(addrId).map(OrderAddressDAOConverter::entityToDto).orElse(null);
    }


}
