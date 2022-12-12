package org.iq47.network.request;

import lombok.Data;

@Data
public class OrderCreateRequest {
    private String promocode;

    private long addrId;
}
