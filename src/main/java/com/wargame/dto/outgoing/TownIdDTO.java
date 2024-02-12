package com.wargame.dto.outgoing;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TownIdDTO {
    private Long townId;

    public TownIdDTO(Long townId) {
        this.townId = townId;
    }
}
