package org.iq47.network;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {

    private Long userId;
    private double coordinateX;
    private double coordinateY;
    private double radius;
    private Boolean hit;
    private LocalDateTime ldt;
    private String time;
    private Long pointId;

    public static Builder newBuilder() {
        return new ItemDTO().new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO that = (ItemDTO) o;
        return Double.compare(that.pointId, pointId) == 0 && Double.compare(that.coordinateX, coordinateX) == 0 && Double.compare(that.coordinateY, coordinateY) == 0 && Double.compare(that.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointId, coordinateX, coordinateY, radius);
    }

    //builder
    public class Builder {
        private Builder() {
        }

        public Builder setCoordinateX(double coordinateX) {
            ItemDTO.this.coordinateX = coordinateX;
            return this;
        }

        public Builder setCoordinateY(double coordinateY) {
            ItemDTO.this.coordinateY = coordinateY;
            return this;
        }

        public Builder setRadius(double radius) {
            ItemDTO.this.radius = radius;
            return this;
        }

        public Builder setHit(Boolean hit) {
            ItemDTO.this.hit = hit;
            return this;
        }

        public Builder setLocalTime(LocalDateTime ldt) {
            ItemDTO.this.ldt = ldt;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            time = ldt.format(formatter);
            return this;
        }

        public Builder setUserId(Long userId) {
            ItemDTO.this.userId = userId;
            return this;
        }

        public Builder setPointId(Long pointId) {
            ItemDTO.this.pointId = pointId;
            return this;
        }

        public ItemDTO build() {
            return ItemDTO.this;
        }
    }
}
