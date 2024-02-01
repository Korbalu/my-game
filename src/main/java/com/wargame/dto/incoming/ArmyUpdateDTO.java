package com.wargame.dto.incoming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArmyUpdateDTO {
    private Long id;
    private String type;
    private Long quantity;
    private Long owner;
}
