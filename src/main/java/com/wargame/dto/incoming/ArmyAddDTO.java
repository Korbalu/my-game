package com.wargame.dto.incoming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArmyAddDTO {
    private String unit;
    private Long amount;
}
