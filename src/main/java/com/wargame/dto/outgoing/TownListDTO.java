package com.wargame.dto.outgoing;

import com.wargame.domain.Town;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TownListDTO {
    private Long id;
    private String name;
    private String owner;
    private String race;

    public TownListDTO(Town town) {
        this.id = town.getId();
        this.name = town.getName();
        this.owner = town.getOwner().getName();
        this.race = town.getRace().getDisplayName();
    }
}
