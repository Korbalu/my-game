package com.wargame.dto.incoming;

import com.wargame.domain.Race;
import com.wargame.domain.Units;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArmyCreationDTO {
    private Units type;
    private Long quantity;
    private Race race;
}
