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

}
