package com.wargame.service;

import com.wargame.domain.Buildings;
import com.wargame.domain.Town;
import com.wargame.dto.incoming.BuildingCreationDTO;
import com.wargame.dto.incoming.TownCreationDTO;
import com.wargame.dto.outgoing.BuildingListDTO;
import com.wargame.repository.TownRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Transactional
public class TownService {
    private TownRepository townRepository;

    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    public void townCreator(TownCreationDTO tcDTO) {
        townRepository.save(new Town(tcDTO));
    }

    public void buildingAdder(BuildingCreationDTO bcDTO) {
        Town town = townRepository.findById(bcDTO.getTownId()).orElse(null);
        if (town != null) {
            if (bcDTO.getBuilding().equals(Buildings.Barracks.getDisplayName())) {
                town.getBuildings().add(Buildings.Barracks);
            } else if (bcDTO.getBuilding().equals("Mine")) {
                town.getBuildings().add(Buildings.Mine);
            } else if (bcDTO.getBuilding().equals("Wall")) {
                town.getBuildings().add(Buildings.Wall);
            }
        }
    }

    public BuildingListDTO listBuildings(Long id) {
        Town town = townRepository.findById(id).orElse(null);
        BuildingListDTO blTDO = new BuildingListDTO();
        blTDO.setId(town.getId());
        blTDO.setBuildings(new HashMap<>());
        for (Buildings townBuilding : town.getBuildings()) {
            if (!blTDO.getBuildings().containsKey(townBuilding.getDisplayName())) {
                blTDO.getBuildings().put(townBuilding.getDisplayName(), 1L);
            } else {
//                blTDO.getBuildings().computeIfPresent(townBuilding.getDisplayName(), (k, v) -> v + 1);
                blTDO.getBuildings().put(townBuilding.getDisplayName(),
                        blTDO.getBuildings().get(townBuilding.getDisplayName()) + 1L);
            }
        }
        return blTDO;
    }
}
