package com.wargame.controller;

import com.wargame.domain.Buildings;
import com.wargame.dto.incoming.BuildingCreationDTO;
import com.wargame.dto.incoming.TownCreationDTO;
import com.wargame.dto.outgoing.AltBuildingListDTO;
import com.wargame.dto.outgoing.BuildingListDTO;
import com.wargame.dto.outgoing.LoggedInUserIdDTO;
import com.wargame.dto.outgoing.TownIdDTO;
import com.wargame.service.TownService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/town")
public class TownController {
    private TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Void> townCreator(@RequestBody TownCreationDTO townCreationDTO){
        System.out.println("1-----------------------------------------------------------------");

        townService.townCreator(townCreationDTO);
        System.out.println("2-----------------------------------------------------------------");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/building")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Void> buildingAdder(@RequestBody BuildingCreationDTO buildingCreationDTO){
        townService.buildingAdder(buildingCreationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/building/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<BuildingListDTO> listBuildings(@PathVariable Long id){
        return new ResponseEntity<>(townService.listBuildings(id), HttpStatus.OK);
    }
    @GetMapping("/building2/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<AltBuildingListDTO>> listBuildingsAlternative(@PathVariable Long id){
        return new ResponseEntity<>(townService.listBuildingsAlternative(id), HttpStatus.OK);
    }
    @GetMapping("/townid")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<TownIdDTO> townIdentifier(){
        return new ResponseEntity<>(townService.townIdentifier(), HttpStatus.OK);
    }
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<LoggedInUserIdDTO> userIdentity(){
        return new ResponseEntity<>(townService.userIdentifier(), HttpStatus.OK);
    }
}
