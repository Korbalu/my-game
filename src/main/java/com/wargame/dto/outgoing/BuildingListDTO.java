package com.wargame.dto.outgoing;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class BuildingListDTO {
    private Map<String, Long> buildings;
}
