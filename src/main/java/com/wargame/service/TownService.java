package com.wargame.service;

import com.wargame.domain.*;
import com.wargame.dto.incoming.BuildingCreationDTO;
import com.wargame.dto.incoming.TownCreationDTO;
import com.wargame.dto.outgoing.*;
import com.wargame.repository.CustomUserRepository;
import com.wargame.repository.TownRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.lang.model.type.UnionType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
        CustomUser owner = customUserRepository.findByMail(loggedInUser.getUsername()).orElse(null);
        town.setOwner(owner);

        townRepository.save(town);
    }

    public void buildingAdder(BuildingCreationDTO bcDTO) {
        Town town = townRepository.findById(bcDTO.getTownId()).orElse(null);
        if (town != null) {
            switch (bcDTO.getBuilding()) {
                case "Barracks" -> addBuilding(town, Buildings.Barracks);
                case "Mine" -> addBuilding(town, Buildings.Mine);
                case "Wall" -> addBuilding(town, Buildings.Wall);
            }
        }
    }

    public BuildingListDTO listBuildings(Long id) {
        Town town = townRepository.findById(id).orElse(null);
        BuildingListDTO blTDO = new BuildingListDTO();
        blTDO.setBuildings(new HashMap<>());

        for (Map.Entry<Buildings, Long> entry : town.getBuildings().entrySet()) {
            blTDO.getBuildings().put(entry.getKey().getDisplayName(), entry.getValue());
        }

        return blTDO;
    }

    public List<AltBuildingListDTO> listBuildingsAlternative(Long id) {
        Town town = townRepository.findById(id).orElse(null);
        List<AltBuildingListDTO> list = new ArrayList<>();

        for (Map.Entry<Buildings, Long> entry : town.getBuildings().entrySet()) {
            AltBuildingListDTO abdDTO = new AltBuildingListDTO();
            abdDTO.setBuilding(entry.getKey().getDisplayName());
            abdDTO.setQuantity(entry.getValue());

            list.add(abdDTO);
        }
        return list;
    }

    private void addBuilding(Town town, Buildings building) {
        town.getBuildings().computeIfPresent(building, (k, v) -> v + 1);
        town.getBuildings().putIfAbsent(building, 1L);
    }

    public TownIdDTO townIdentifier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findByMail(loggedInUser.getUsername()).orElse(null);

        Town town = townRepository.findByOwner(owner.getId()).orElse(null);
        return new TownIdDTO(town.getId());
    }

    public LoggedInUserIdDTO userIdentifier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findAllByEmail(loggedInUser.getUsername()).orElse(null);
        return new LoggedInUserIdDTO(owner.getId(), owner.getName());
    }

}
