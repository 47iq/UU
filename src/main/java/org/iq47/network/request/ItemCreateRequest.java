package org.iq47.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemCreateRequest implements Serializable {
    private String name;
    private String description;
    private int price;
    private double coordinatesX;
    private double coordinatesY;
    private String[] tags;
}
