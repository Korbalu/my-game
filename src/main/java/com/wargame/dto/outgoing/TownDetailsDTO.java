package com.wargame.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TownDetailsDTO {
    private Long vault;
    private String townName;
    private String race;
    private Integer turns;
}
