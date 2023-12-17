package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.network.response.ResponseWrapper;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseUtils {
    public static ResponseEntity<ResponseWrapper> reportError(Object req, Exception e) {
        if(req != null)
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
        else
            log.error(String.format("Got %s while processing request", e.getClass()));
        return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
    }
}
