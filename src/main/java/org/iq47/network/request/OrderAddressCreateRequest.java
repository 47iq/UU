package org.iq47.network.request;

import lombok.Data;

@Data
public class OrderAddressCreateRequest {
    private String description;

    private double latitude;

    private double longtitude;

    private String name;
}
