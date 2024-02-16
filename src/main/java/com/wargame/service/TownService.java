package com.wargame.service;

import com.wargame.domain.*;
import com.wargame.dto.incoming.ArmyCreationDTO;
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
    private ArmyService armyService;


    public TownService(TownRepository townRepository, CustomUserRepository customUserRepository,ArmyService armyService) {
        this.armyService = armyService;
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

//    public void buildingAdder(BuildingCreationDTO bcDTO) {
//        Town town = townRepository.findById(bcDTO.getTownId()).orElse(null);
//        if (town != null) {
//            switch (bcDTO.getBuilding()) {
//                case "Barracks" -> addBuilding(town, Buildings.Barracks);
//                case "Mine" -> addBuilding(town, Buildings.Mine);
//                case "Wall" -> addBuilding(town, Buildings.Wall);
//            }
//        }
//    }

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

    public void addBuilding(String building) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findAllByEmail(loggedInUser.getUsername()).orElse(null);

        Town town = townRepository.findByOwnerId(owner.getId());
        town.getBuildings().computeIfPresent(Buildings.valueOf(building), (k, v) -> v + 1);
        town.getBuildings().putIfAbsent(Buildings.valueOf(building), 1L);
        owner.getTowns().get(0).setVault(owner.getTowns().get(0).getVault()-Buildings.valueOf(building).getCost());
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
    public List<BuildingsDTO> buildingLister() {
        List<BuildingsDTO> buildings = new ArrayList<>();
        for (Buildings value : Buildings.values()) {
            BuildingsDTO building = new BuildingsDTO();
            building.setName(value.getDisplayName());
            building.setProduction(value.getProduction());
            building.setCost(value.getCost());
            buildings.add(building);
        }
        return buildings;
    }

    public TownDetailsDTO townDetailer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findAllByEmail(loggedInUser.getUsername()).orElse(null);

        Town town = townRepository.findByOwnerId(owner.getId());
        return new TownDetailsDTO(town.getVault(), town.getName(), town.getRace().getDisplayName());
    }
    
    public void newTurn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findAllByEmail(loggedInUser.getUsername()).orElse(null);
        Town town = townRepository.findByOwnerId(owner.getId());
        System.out.println(owner.getId());
        System.out.println(owner);

        for (Map.Entry<Buildings, Long> building : town.getBuildings().entrySet()) {
            if (building.getKey().equals(Buildings.Mine)){
                town.setVault(town.getVault() + building.getValue()*Buildings.Mine.getProduction());
            }
            if (building.getKey().equals(Buildings.Barracks)){
                for (int i = 0; i < building.getValue(); i++) {
                    armyService.increaseUnit(Units.Swordsman1.getDisplayName());
                    town.setVault(town.getVault() + Units.Swordsman1.getCost());
                }

            }
            System.out.println(building.getKey());
            System.out.println(building.getValue());
        }
        
    }
}
