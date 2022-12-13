package org.iq47.network.response;

import lombok.Data;
import org.iq47.network.ItemDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemResponse {
    List<ItemDTO> items;
    List<Integer> lowestPrice = new ArrayList<>();
}
