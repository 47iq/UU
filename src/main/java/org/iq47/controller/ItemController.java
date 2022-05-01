package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.exception.ItemSaveException;
import org.iq47.network.ItemDTO;
import org.iq47.network.request.ItemPlaceRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.security.userDetails.CustomUserDetails;
import org.iq47.service.ItemService;
import org.iq47.validate.ItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/points")
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ItemValidator itemValidator;

    @Autowired
    public ItemController(ItemService itemService, ItemValidator itemValidator) {
        this.itemService = itemService;
        this.itemValidator = itemValidator;
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody ItemPlaceRequest req) {
        try {
            Optional<String> error = itemValidator.getErrorMessage(req);
            if(error.isPresent())
                throw new InvalidRequestException(error.get());
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return save(userId, req);
        } catch (ItemSaveException | InvalidRequestException ex) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(ex.getMessage()));
        } catch (Exception e) {
            return reportError(req, e);
        }
    }

    private ResponseEntity<ResponseWrapper> reportError(Object req, Exception e) {
        if(req != null)
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
        else
            log.error(String.format("Got %s while processing request", e.getClass()));
        return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
    }

    private ResponseEntity<?> save(Long userId, ItemPlaceRequest req) throws ItemSaveException {
        ItemDTO pointDto = ItemDTO.newBuilder()
                .setUserId(userId)
                .setCoordinateX(req.getX())
                .setCoordinateY(req.getY())
                .setRadius(req.getR()).build();
        Optional<ItemDTO> pointDtoOptional = itemService.savePoint(pointDto);
        if (!pointDtoOptional.isPresent()) {
            throw new ItemSaveException("Point has not been saved.");
        }
        return ResponseEntity.ok().body(pointDtoOptional.get());
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(itemService.getPointsByUserId(userId));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }


    @PostMapping("/clear")
    public ResponseEntity<?> clear() {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(itemService.removePointsByUserId(userId));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }
}
