package com.wargame.dto.incoming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BattleDTO {
    private Long army1;
    private Long army2;
}
