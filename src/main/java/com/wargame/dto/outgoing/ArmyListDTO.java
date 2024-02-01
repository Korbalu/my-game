package com.wargame.dto.outgoing;

import com.wargame.domain.Army;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArmyListDTO {
    private Long id;
    private String type;
    private Long quantity;
    private String owner;
    private String race;

    public ArmyListDTO(Army army) {
        this.id = army.getId();
        this.type = army.getType().getDisplayName();
        this.quantity = army.getQuantity();
        this.owner = army.getOwner().getName();
        this.race = army.getRace().getDisplayName();
    }
}
