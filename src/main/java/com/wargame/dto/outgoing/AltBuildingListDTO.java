package com.wargame.dto.outgoing;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AltBuildingListDTO {
    private String building;
    private Long quantity;
}
