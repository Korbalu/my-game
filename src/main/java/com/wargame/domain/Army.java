package com.wargame.domain;

import com.wargame.dto.incoming.ArmyCreationDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "army")
@Data
public class Army {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "armyId")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "armyType")
    private Units type;
    @Column(name = "unitQuantity")
    private Long quantity;
    @ManyToOne
    private CustomUser owner;
    @Enumerated(EnumType.STRING)
    @Column(name = "raceType")
    private Race race;

    public Army() {
    }

    public Army(ArmyCreationDTO armyCreationDTO) {
        this.type = armyCreationDTO.getType();
        this.quantity = armyCreationDTO.getQuantity();
        this.race = armyCreationDTO.getRace();
    }
}
