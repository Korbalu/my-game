package com.wargame.dto.outgoing;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitListDTO {
    private String name;
    private Integer attack;
    private Integer defense;
    private Integer cost;
}
