package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.network.ItemDTO;
import org.iq47.network.request.ItemCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.security.userDetails.CustomUserDetails;
import org.iq47.service.ItemService;
import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("${urls.base}/${urls.items.base}")
@Slf4j
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService service) {
        this.itemService = service;
    }

    @PostMapping("/")
    private ResponseEntity<?> save(@RequestBody ItemCreateRequest req) {
        Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        return ResponseEntity.ok().body(itemService.saveItem(uid.intValue(), req));
    }

    @GetMapping("/")
    private ResponseEntity<?> getItems(@RequestParam String query) {
        Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (query.isEmpty()) {
            try {
                return ResponseEntity.ok().body(itemService.getCatalog(uid.intValue(), 10));
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return ResponseEntity.ok().body(itemService.getItemsByNameStartsWith(query));
            }
        }
        return ResponseEntity.ok().body(itemService.getItemsByNameStartsWith(query));
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getItem(@PathVariable int id) {
        Optional<ItemDTO> item = itemService.getItemById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok().body(item.get());
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/${urls.items.autocomplete}")
    private ResponseEntity<?> autocompleteItem(@RequestParam String query) {
        return ResponseEntity.ok().body(itemService.getAutocompleteEntries(query));
    }


    @PostMapping("/${urls.items.favourite}/{item_id}")
    private ResponseEntity<?> addFavoriteItemToUser(@PathVariable int item_id) {
        try {
            Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            boolean res = itemService.addFavoriteItem(uid.intValue(), item_id);
            if (res) return ResponseEntity.ok().build();
            else return ResponseEntity.status(404).body(new ResponseWrapper("item not found"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @DeleteMapping("/${urls.items.favourite}/{item_id}")
    private ResponseEntity<?> removeFavoriteItemFromUser(@PathVariable int item_id) {
        try {
            Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            boolean res = itemService.removeFavoriteItem(uid.intValue(), item_id);
            if (res) return ResponseEntity.ok().build();
            else return ResponseEntity.status(404).body(new ResponseWrapper("item not found"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @GetMapping("/${urls.items.favourite}")
    private ResponseEntity<?> getUserFavoriteItems() {
        try {
            Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(itemService.getFavoriteItems(uid.intValue()));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @GetMapping("/${urls.items.catalog}/{item_count}")
    private ResponseEntity<?> getCatalog(@PathVariable int item_count) {
        try {
            Long uid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(itemService.getCatalog(uid.intValue(), item_count));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }
}
