package org.iq47.network.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.iq47.network.ItemDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ItemResponse {
    List<ItemDTO> items;

}
