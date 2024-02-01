package com.wargame.domain;

import com.wargame.dto.incoming.TownCreationDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "town")
@Data
@NoArgsConstructor
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "townId")
    private Long id;
    @Column(name = "ownerId")
    private Long owner;
    @Column
    private Long vault;
    @Column
    private List<Buildings> buildings;

    public Town(TownCreationDTO TCDTO) {
        this.owner = TCDTO.getOwner();
        this.vault = TCDTO.getVault();
        this.buildings = new ArrayList<>();
    }
}
