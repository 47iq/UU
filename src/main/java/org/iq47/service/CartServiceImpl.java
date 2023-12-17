package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.CartDAOConverter;
import org.iq47.repository.CartRepository;
import org.iq47.repository.ShopItemRepository;
import org.iq47.repository.UserRepository;
import org.iq47.model.entity.user.Cart;
import org.iq47.model.entity.shop.ShopItem;
import org.iq47.model.entity.user.User;
import org.iq47.network.CartDAO;
import org.iq47.network.response.ResponseWrapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private CartRepository repository;

    private ShopItemRepository shopItemRepository;

    private UserRepository userRepository;

    public CartServiceImpl(CartRepository repository, ShopItemRepository shopItemRepository, UserRepository userRepository) {
        this.repository = repository;
        this.shopItemRepository = shopItemRepository;
        this.userRepository = userRepository;
    }

    public CartDAO getUserCart(int userId) {
        User user = userRepository.getById(userId);
        Cart cart = repository.findCartByUser(user);
        if (cart != null) {
            return CartDAOConverter.entityToDto(cart);
        } else return null;
    }

    public ResponseWrapper createCart(int userId) {
        User user = userRepository.getById(userId);
        repository.save(new Cart(user, 0));
        return new ResponseWrapper(String.format("cart created for user %s", user.getName()));
    }

    public ResponseWrapper addShopItemToCart(int userId, int shopItemId) {
        User user = userRepository.getById(userId);
        Optional<ShopItem> shopItem = shopItemRepository.findById(shopItemId);
        if (shopItem.isPresent()) {
            Cart cart = repository.findCartByUser(user);
            cart.addShopItem(shopItem.get());
            cart.setItem_count(cart.getItem_count() + 1);
            repository.save(cart);
        } else return new ResponseWrapper("error adding shopitem to cart: item not found");

        return new ResponseWrapper("ok");
    }

    public ResponseWrapper removeShopItemFromCart(int userId, int shopItemId) {
        User user = userRepository.getById(userId);
        Optional<ShopItem> shopItem = shopItemRepository.findById(shopItemId);
        if (shopItem.isPresent()) {
            Cart cart = repository.findCartByUser(user);
            cart.removeShopItem(shopItem.get().getId());
            cart.setItem_count(cart.getItem_count() - 1);
            repository.save(cart);
        } else return new ResponseWrapper("error removing shopitem to cart: item not found");

        return new ResponseWrapper("ok");
    }


}
