package com.wargame.service;

import com.wargame.domain.Army;
import com.wargame.domain.CustomUser;
import com.wargame.domain.Units;
import com.wargame.dto.incoming.ArmyCreationDTO;
import com.wargame.dto.outgoing.ArmyListDTO;
import com.wargame.dto.incoming.ArmyUpdateDTO;
import com.wargame.repository.ArmyRepository;
import com.wargame.repository.CustomUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArmyService {
    private ArmyRepository armyRepository;
    private CustomUserRepository customUserRepository;

    public ArmyService(ArmyRepository armyRepository, CustomUserRepository customUserRepository) {
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

    public void updateArmy(Long id, ArmyUpdateDTO armyDTO) {
        Army army = armyRepository.findOneById(id);
        army.setQuantity(armyDTO.getQuantity());
        armyRepository.save(army);
    }

    public List<ArmyListDTO> listAllArmies() {
        return armyRepository.findAllOrderedbyOwner().stream().map(ArmyListDTO::new).collect(Collectors.toList());
    }

    public List<ArmyListDTO> listOnesArmy(Long id) {
        return armyRepository.findAllById(id).stream().map(ArmyListDTO::new).collect(Collectors.toList());
    }

    public void addUnits(String unit, Long amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findAllByEmail(loggedInUser.getUsername()).orElse(null);
        Long id = owner.getId();
        List<Army> armies = armyRepository.findAllById(id);
        for (Army army : armies) {
            if (army.getType().getDisplayName().equals(unit)) {
                army.setQuantity(army.getQuantity() + amount);
            }
        }
    }

    public String battle(Long id1, Long id2) {
        List<Army> army1 = armyRepository.findAllById(id1);
        List<Army> army2 = armyRepository.findAllById(id2);

        int sumAttack1 = 0;
        int sumAttack2 = 0;
        int sumDefense1 = 0;
        int sumDefense2 = 0;
        String endcome = "";

        for (Army army : army1) {
            Long multiplier1 = army.getQuantity();
            if (army.getRace().getDisplayName().equals("Elf") &&
                    (army.getType().equals(Units.Archer1) || army.getType().equals(Units.Archer2)
                            || army.getType().equals(Units.Archer3))) {
                sumAttack1 += army.getType().getAttack() * 1.2 * multiplier1;
                sumDefense1 += army.getType().getDefense() * multiplier1;
            } else if (army.getRace().getDisplayName().equals("Orc") &&
                    (army.getType().equals(Units.Swordsman1) || army.getType().equals(Units.Swordsman2)
                            || army.getType().equals(Units.Swordsman3))) {
                sumAttack1 += army.getType().getAttack() * 1.1 * multiplier1;
                sumDefense1 += army.getType().getDefense() * 1.2 * multiplier1;
            } else if (army.getRace().getDisplayName().equals("Human") &&
                    (army.getType().equals(Units.Horseman1) || army.getType().equals(Units.Horseman2)
                            || army.getType().equals(Units.Horseman3))) {
                sumAttack1 += army.getType().getAttack() * 1.1 * multiplier1;
                sumDefense1 += army.getType().getDefense() * 1.1 * multiplier1;
            } else {
                sumAttack1 += army.getType().getAttack() * multiplier1;
                sumDefense1 += army.getType().getDefense() * multiplier1;
            }
        }
        for (Army army : army2) {
            Long multiplier1 = army.getQuantity();
            if (army.getRace().getDisplayName().equals("Elf") &&
                    (army.getType().equals(Units.Archer1) || army.getType().equals(Units.Archer2)
                            || army.getType().equals(Units.Archer3))) {
                sumAttack2 += army.getType().getAttack() * 1.2 * multiplier1;
                sumDefense2 += army.getType().getDefense() * multiplier1;
            } else if (army.getRace().getDisplayName().equals("Orc") &&
                    (army.getType().equals(Units.Swordsman1) || army.getType().equals(Units.Swordsman2)
                            || army.getType().equals(Units.Swordsman3))) {
                sumAttack2 += army.getType().getAttack() * 1.1 * multiplier1;
                sumDefense2 += army.getType().getDefense() * 1.2 * multiplier1;
            } else if (army.getRace().getDisplayName().equals("Human") &&
                    (army.getType().equals(Units.Horseman1) || army.getType().equals(Units.Horseman2)
                            || army.getType().equals(Units.Horseman3))) {
                sumAttack2 += army.getType().getAttack() * 1.1 * multiplier1;
                sumDefense2 += army.getType().getDefense() * 1.1 * multiplier1;
            } else {
                sumAttack2 += army.getType().getAttack() * multiplier1;
                sumDefense2 += army.getType().getDefense() * multiplier1;
            }
        }

        endcome += " army1 sumdefense: " + sumDefense1 + " army1 sumattack: " + sumAttack1
                + "\n army2 sumdefense: " + sumDefense2 + " army2 sumattack: " + sumAttack2;
        sumDefense1 -= sumAttack2;
        sumDefense2 -= sumAttack1;
        endcome += "\n army1 sumdefense: " + sumDefense1 + " army1 sumattack: " + sumAttack1
                + "\n army2 sumdefense: " + sumDefense2 + " army2 sumattack: " + sumAttack2;

        if (sumDefense1 >= sumDefense2) {
            for (Army army : army1) {
                army.setQuantity((long) (army.getQuantity() * 0.8));
            }
            for (Army army : army2) {
                army.setQuantity((long) (army.getQuantity() * 0.75));
            }
            endcome += "\n Army1 is Victorious!";
        } else {
            for (Army army : army1) {
                army.setQuantity((long) (army.getQuantity() * 0.75));
            }
            for (Army army : army2) {
                army.setQuantity((long) (army.getQuantity() * 0.8));
            }
            endcome += "\n Army2 is Victorious!";
        }
        return endcome;
    }

}
