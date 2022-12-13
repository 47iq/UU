package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.exception.PointSaveException;
import org.iq47.model.entity.item.TagEnum;
import org.iq47.network.ItemDTO;
import org.iq47.network.request.ItemCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.security.userDetails.CustomUserDetails;
import org.iq47.service.ItemService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@RestController
@RequestMapping("api/items")
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService service) {
        this.itemService = service;
    }

    private ResponseEntity<ResponseWrapper> reportError(Object req, Exception e) {
        if(req != null)
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
        else
            log.error(String.format("Got %s while processing request", e.getClass()));
        return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
    }

    @PostMapping("/create")
    private ResponseEntity<?> save(Long userId, @RequestBody ItemCreateRequest req) {
        Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        return ResponseEntity.ok().body(itemService.saveItem(uid.intValue(), req));
    }

    @GetMapping("/items")
    private ResponseEntity<?> getItems(@RequestParam String query) {
        Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (query.isEmpty()) {
            try {
                return ResponseEntity.ok().body(itemService.getCatalog(uid.intValue(), 10));
            } catch (PSQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return ResponseEntity.ok().body(itemService.getItemsByNameStartsWith(query));
    }

    @GetMapping("/item/{id}")
    private ResponseEntity<?> getItem(@PathVariable int id) {
        Optional<ItemDTO> item = itemService.getItemById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok().body(item.get());
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/autocomplete")
    private ResponseEntity<?> autocompleteItem(@RequestParam String query) {
        return ResponseEntity.ok().body(itemService.getAutocompleteEntries(query));
    }

    @PostMapping("/favorite_item/add/{item_id}")
    private ResponseEntity<?> addFavoriteItemToUser(@PathVariable int item_id) {
        Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return ResponseEntity.ok().body(itemService.addFavoriteItem(uid.intValue(), item_id));
    }

    @PostMapping("/favorite_item/remove/{item_id}")
    private ResponseEntity<?> removeFavoriteItemFromUser(@PathVariable int item_id) {
        Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return ResponseEntity.ok().body(itemService.removeFavoriteItem(uid.intValue(), item_id));
    }

    @GetMapping("/favourite_item")
    private ResponseEntity<?> getUserFavoriteItems() {
        Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return ResponseEntity.ok().body(itemService.getFavoriteItems(uid.intValue()));
    }

    @GetMapping("/get_catalog/{item_count}")
    private ResponseEntity<?> getCatalog(@PathVariable int item_count) throws PSQLException {
        Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return ResponseEntity.ok().body(itemService.getCatalog(uid.intValue(), item_count));
    }
}
