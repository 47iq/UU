package org.iq47.network;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iq47.model.entity.item.Tag;
import org.iq47.model.entity.item.TagEnum;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {

    private Long id;

    private Long userId;
    private String userName;
    private String name;
    private String description;
    private int price;
    private double coordinatesX;
    private double coordinatesY;
    private String imageURL;
    private Collection<TagEnum> tags;

    public static Builder newBuilder() {
        return new ItemDTO().new Builder();
    }

    public class Builder {
        private Builder() {}

        public Builder setName(String name) {
            ItemDTO.this.name = name;
            return this;
        }

        public Builder setId(Long id) {
            ItemDTO.this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            ItemDTO.this.userName = username;
            return this;
        }

        public Builder setUserId(long uid) {
            ItemDTO.this.userId = uid;
            return this;
        }

        public Builder setDescription(String description) {
            ItemDTO.this.description = description;
            return this;
        }

        public Builder setPrice(int price) {
            ItemDTO.this.price = price;
            return this;
        }

        public Builder setCoordinatesX(double x) {
            ItemDTO.this.coordinatesX = x;
            return this;
        }

        public Builder setCoordinatesY(double y) {
            ItemDTO.this.coordinatesY = y;
            return this;
        }

        public Builder setTags(Collection<TagEnum> tags) {
            ItemDTO.this.tags = tags;
            return this;
        }

        public Builder setImageURL(String imageURL) {
            ItemDTO.this.imageURL = imageURL;
            return this;
        }

        public ItemDTO build(){
            return ItemDTO.this;
        }
    }
}
