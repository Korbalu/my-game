package com.wargame.dto.incoming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuildingCreationDTO {
    private Long townId;
    private String building;
}
