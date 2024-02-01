package com.wargame.controller;

import com.wargame.dto.incoming.ArmyAddDTO;
import com.wargame.dto.incoming.ArmyCreationDTO;
import com.wargame.dto.incoming.BattleDTO;
import com.wargame.dto.outgoing.ArmyListDTO;
import com.wargame.dto.incoming.ArmyUpdateDTO;
import com.wargame.service.ArmyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/army")
public class ArmyController {
    private ArmyService armyService;

    public ArmyController(ArmyService armyService) {
        this.armyService = armyService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Void> saveArmy(@RequestBody ArmyCreationDTO creationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        armyService.saveArmy(creationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<ArmyListDTO>> getEveryArmy(@AuthenticationPrincipal UserDetails userDetails) {
        List<ArmyListDTO> armies = armyService.listAllArmies();
        return new ResponseEntity<>(armies, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> changeOneArmy(@PathVariable Long id, @RequestBody ArmyUpdateDTO updateDTO) {
        armyService.updateArmy(id, updateDTO);
        return new ResponseEntity<>("Army Updated to " + updateDTO.getQuantity() + "!", HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<ArmyListDTO>> getOneArmy(@PathVariable Long id) {
        List<ArmyListDTO> armies = armyService.listOnesArmy(id);
        return new ResponseEntity<>(armies, HttpStatus.OK);
    }
    @PostMapping("/battle")
    public ResponseEntity<String> battle(@RequestBody BattleDTO battleDTO){
        return new ResponseEntity<>(armyService.battle(battleDTO.getArmy1(), battleDTO.getArmy2()), HttpStatus.OK);
    }
    @PostMapping("/unit")
    public ResponseEntity<Void> unitAdding(@RequestBody ArmyAddDTO addDTO){
        armyService.addUnits(addDTO.getUnit(), addDTO.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
