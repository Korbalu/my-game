package com.wargame.service;

import com.wargame.domain.Buildings;
import com.wargame.domain.CustomUser;
import com.wargame.domain.Race;
import com.wargame.domain.Town;
import com.wargame.dto.incoming.BuildingCreationDTO;
import com.wargame.dto.incoming.TownCreationDTO;
import com.wargame.dto.outgoing.BuildingListDTO;
import com.wargame.repository.CustomUserRepository;
import com.wargame.repository.TownRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Transactional
public class TownService {
    private TownRepository townRepository;
    private CustomUserRepository customUserRepository;

    public TownService(TownRepository townRepository, CustomUserRepository customUserRepository) {
        this.townRepository = townRepository;
        this.customUserRepository = customUserRepository;
    }

    public void townCreator(TownCreationDTO tcDTO) {
        Town town = new Town(tcDTO);
        Race race = switch (tcDTO.getRace()) {
            case "Human" -> Race.Human;
            case "Orc" -> Race.Orc;
            case "Elf" -> Race.Elf;
            default -> null;
        };
        town.setRace(race);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findAllByEmail(loggedInUser.getUsername()).orElse(null);
        town.setOwner(owner);
        townRepository.save(town);
    }

    public void buildingAdder(BuildingCreationDTO bcDTO) {
        Town town = townRepository.findById(bcDTO.getTownId()).orElse(null);
        if (town != null) {
            switch (bcDTO.getBuilding()) {
                case "Barracks" -> town.getBuildings().add(Buildings.Barracks);
                case "Mine" -> town.getBuildings().add(Buildings.Mine);
                case "Wall" -> town.getBuildings().add(Buildings.Wall);
            }
        }
    }

    public BuildingListDTO listBuildings(Long id) {
        Town town = townRepository.findById(id).orElse(null);

        BuildingListDTO blTDO = new BuildingListDTO();
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
