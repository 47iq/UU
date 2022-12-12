package org.iq47.network;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderAddressDAO {
    private int id;

    private String name;

    private String description;

    private double longitude;

    private double latitude;
}
