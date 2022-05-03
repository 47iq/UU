package org.iq47.network.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class PointPlaceRequest implements Serializable {
    Double x;
    Double y;
    Double r;
}
