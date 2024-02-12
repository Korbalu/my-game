package com.wargame.controller;

import com.wargame.dto.incoming.ArmyCreationDTO;
import com.wargame.dto.outgoing.ArmyListDTO;
import com.wargame.dto.incoming.ArmyUpdateDTO;
import com.wargame.dto.outgoing.UnitListDTO;
import com.wargame.service.ArmyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Void> saveArmy(@RequestBody ArmyCreationDTO creationDTO) {
        armyService.saveArmy(creationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<ArmyListDTO>> getEveryArmy() {
        List<ArmyListDTO> armies = armyService.listAllArmies();
        return new ResponseEntity<>(armies, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<ArmyListDTO>> getUsersArmy(@PathVariable Long id) {
        List<ArmyListDTO> armies = armyService.listUsersArmy(id);
        return new ResponseEntity<>(armies, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> changeOneArmy(@PathVariable Long id, @RequestBody ArmyUpdateDTO updateDTO) {
        armyService.updateArmy(id, updateDTO);
        return new ResponseEntity<>("Army Updated to " + updateDTO.getQuantity() + "!", HttpStatus.OK);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<UnitListDTO>> unitLister(){
        return new ResponseEntity<>(armyService.unitLister(), HttpStatus.OK);
    }

    @PutMapping("/increase")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Void> increaseArmy(@RequestBody String unit){
        armyService.increaseUnit(unit);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
