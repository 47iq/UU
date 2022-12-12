package org.iq47.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemCreateRequest implements Serializable {
    private String name;
    private String description;
    private String[] categories;
}
