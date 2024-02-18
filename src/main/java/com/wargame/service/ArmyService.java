package com.wargame.service;

import com.wargame.domain.*;
import com.wargame.dto.incoming.ArmyCreationDTO;
import com.wargame.dto.outgoing.ArmyListDTO;
import com.wargame.dto.incoming.ArmyUpdateDTO;
import com.wargame.dto.outgoing.UnitListDTO;
import com.wargame.repository.ArmyRepository;
import com.wargame.repository.CustomUserRepository;
import com.wargame.repository.TownRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArmyService {
    private ArmyRepository armyRepository;
    private CustomUserRepository customUserRepository;

    private TownRepository townRepository;

    public ArmyService(ArmyRepository armyRepository, CustomUserRepository customUserRepository, TownRepository townRepository) {
        this.townRepository = townRepository;
        this.armyRepository = armyRepository;
        this.customUserRepository = customUserRepository;
    }

    public void saveArmy(ArmyCreationDTO creationDTO) {
        Army army = new Army(creationDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findAllByEmail(loggedInUser.getUsername()).orElse(null);

        Army army2;

        if (owner != null) {
            army2 = armyRepository.findByOwnerAndType(owner.getId(), creationDTO.getType());
        } else {
            army2 = null;
        }
        if (army2 != null) {
            army2.setQuantity(army2.getQuantity() + creationDTO.getQuantity());
            armyRepository.save(army2);
        } else {
            army.setOwner(owner);
            armyRepository.save(army);
        }
    }

    public void increaseUnit(String unit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findAllByEmail(loggedInUser.getUsername()).orElse(null);

        Army army = armyRepository.findByOwnerAndType(owner.getId(), Units.valueOf(unit));

        if (army == null) {
            ArmyCreationDTO armyCreationDTO = new ArmyCreationDTO(Units.valueOf(unit), 1L, owner.getTowns().get(0).getRace());
            Army army2 = new Army(armyCreationDTO);
            army2.setOwner(owner);
            owner.getTowns().get(0).setVault(owner.getTowns().get(0).getVault() - Units.valueOf(unit).getCost());
            armyRepository.save(army2);
        } else {
            owner.getTowns().get(0).setVault(owner.getTowns().get(0).getVault() - Units.valueOf(unit).getCost());
            army.setQuantity(army.getQuantity() + 1);
        }
    }

    public List<ArmyListDTO> listAllArmies() {
        return armyRepository.findAllOrderedbyOwner().stream().map(ArmyListDTO::new).collect(Collectors.toList());
    }

    public List<ArmyListDTO> listUsersArmy(Long id) {
        return armyRepository.findAllById(id).stream().map(ArmyListDTO::new).collect(Collectors.toList());
    }

    public void updateArmy(Long id, ArmyUpdateDTO armyDTO) {
        Army army = armyRepository.findOneById(id);
        army.setQuantity(armyDTO.getQuantity());
        armyRepository.save(army);
    }

    public List<UnitListDTO> unitLister() {
        List<UnitListDTO> units = new ArrayList<>();
        for (Units value : Units.values()) {
            UnitListDTO unit = new UnitListDTO();
            unit.setName(value.getDisplayName());
            unit.setAttack(value.getAttack());
            unit.setDefense(value.getDefense());
            unit.setCost(value.getCost());
            units.add(unit);
        }
        return units;
    }

    public void battle(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findAllByEmail(loggedInUser.getUsername()).orElse(null);

        CustomUser enemy = customUserRepository.findByName(name).orElse(null);

        List<Army> army = armyRepository.findAllById(owner.getId());
        List<Army> otherArmy = armyRepository.findAllById(enemy.getId());

        double sumAttack = 0;
        double sumDefense = 0;

        for (Army army1 : army) {
            if (army1.getType().equals(Units.Archer1) || army1.getType().equals(Units.Archer2) || army1.getType().equals(Units.Archer3)
                    && army1.getRace().equals(Race.Elf)) {
                sumAttack += army1.getQuantity() * army1.getType().getAttack() * 1.2;
            } else if (army1.getType().equals(Units.Swordsman1) || army1.getType().equals(Units.Swordsman2) || army1.getType().equals(Units.Swordsman3)
                    && army1.getRace().equals(Race.Orc)) {
                sumAttack += army1.getQuantity() * army1.getType().getAttack() * 1.1;
            } else if (army1.getType().equals(Units.Horseman1) || army1.getType().equals(Units.Horseman2) || army1.getType().equals(Units.Horseman3)
                    && army1.getRace().equals(Race.Human)) {
                sumAttack += army1.getQuantity() * army1.getType().getAttack() * 1.15;
            }
        }

        for (Army army1 : otherArmy) {
            if (army1.getType().equals(Units.Swordsman1) || army1.getType().equals(Units.Swordsman2) || army1.getType().equals(Units.Swordsman3)
                    && army1.getRace().equals(Race.Orc)) {
                sumDefense += army1.getQuantity() * army1.getType().getDefense() * 1.1;
            } else if (army1.getType().equals(Units.Horseman1) || army1.getType().equals(Units.Horseman2) || army1.getType().equals(Units.Horseman3)
                    && army1.getRace().equals(Race.Human)) {
                sumAttack += army1.getQuantity() * army1.getType().getDefense() * 1.05;
            }
        }
        if (enemy.getTowns().get(0).getBuildings().containsKey(Buildings.Wall)) {
            int multiplier = Math.toIntExact(enemy.getTowns().get(0).getBuildings().get(Buildings.Wall));
            sumDefense *= 1 + (double) multiplier / 50;
        }

        if (sumAttack > sumDefense) {
            System.out.println("Attacker won!");
            for (Army army1 : army) {
                army1.setQuantity(Math.round(army1.getQuantity() * 0.9));
            }
            for (Army army1 : otherArmy) {
                army1.setQuantity(Math.round(army1.getQuantity() * 0.85));
            }
            owner.getTowns().get(0).setVault(owner.getTowns().get(0).getVault() + Math.round(enemy.getTowns().get(0).getVault() * 0.15));
            enemy.getTowns().get(0).setVault(Math.round(enemy.getTowns().get(0).getVault() * 0.85));
        } else {
            System.out.println("Defender won!");
            for (Army army1 : army) {
                army1.setQuantity(Math.round(army1.getQuantity() * 0.85));
            }
            for (Army army1 : otherArmy) {
                army1.setQuantity(Math.round(army1.getQuantity() * 0.9));
            }
        }

//        for (Army army1 : army) {
//            long attack;
//            long defense;
//            if (army1.getType().equals(Units.Archer1)) {
//                attack = army1.getQuantity() * army1.getType().getAttack();
//                for (Army army2 : otherArmy) {
//                    if (army2.getType().equals(Units.Swordsman1)) {
//                        defense = army2.getQuantity() * army2.getType().getDefense();
//                        double damage = defense - attack*1.2;
//
//
//                        while (attack >= 0 && defense >= 0) {
//                            attack -= army1.getType().getAttack();
//                            long defense2 = defense - army1.getType().getAttack();
//
//                        }
//                    }
//                }
//            }
//        }
    }

}
