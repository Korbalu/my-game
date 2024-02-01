package com.wargame.dto.incoming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TownCreationDTO {
    private Long owner;
    private Long vault;
}
