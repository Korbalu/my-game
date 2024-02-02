package com.wargame.domain;

import com.wargame.dto.incoming.TownCreationDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "town")
@Data
@NoArgsConstructor
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "townId")
    private Long id;
    @Column(name = "townName")
    private String name;
    @Column
    private Long vault;
    @ElementCollection
    private Map<Buildings, Long> buildings;
    @Enumerated(EnumType.STRING)
    @Column(name = "raceType")
    private Race race;
    @ManyToOne
    private CustomUser owner;

    public Town(TownCreationDTO TCDTO) {
        this.name = TCDTO.getName();
        this.vault = 2000L;
        this.buildings = new HashMap<>();
    }
}
