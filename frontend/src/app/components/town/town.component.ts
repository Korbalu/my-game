import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {ArmyListModel} from "../../models/army-list-model";
import {AltBuildingListModel} from "../../models/alt-building-list-model";

@Component({
  selector: 'app-town',
  templateUrl: './town.component.html',
  styleUrls: ['./town.component.css']
})

export class TownComponent implements OnInit {

  armyList: Array<ArmyListModel> = [];
  buildingList: Array<AltBuildingListModel> = [];
  townId!: number;
  userId!: number;
  owner: string = '';
  townName: string = '';
  vault: number = 0;
  townRace: string = '';

  constructor(private userService: UserService) {

  }

  ngOnInit(): void {
    this.userService.townDetails().subscribe({
      next: (data) => {
        console.log(data)
        this.vault = data.vault;
        this.townRace = data.race;
        this.townName = data.townName;
      },
      error: (err)=>{
        console.log(err)
      },
      complete:()=>{}
    })
    this.userService.userAsker().subscribe({
      next: (data) => {
        this.userId = data.userId;
        this.owner = data.userName;
      },
      error: err => {
        console.log(err)
      },
      complete: () => {
        this.userService.armyAsker(this.userId).subscribe({
          next: (data) => {
            this.armyList = data;
            console.log(this.armyList);
          },
          error: err => {
            console.log(err);
          },
          complete: () => {
            console.log("done boooy!");
          }
        });
      }
    })
    this.userService.townIdentity().subscribe({
      next: (data) => {
        console.log(data);
        this.townId = data.townId;
      },
      error: err => {
        console.log(err)
      },
      complete: () => {
        this.userService.buildingAsker2(this.townId).subscribe({
          next: (data2) => {
            this.buildingList = data2;
            console.log(data2);
            console.log(this.buildingList);
          },
          error: err => {
            console.log(err);
          },
          complete: () => {
            console.log("done again boooy2!");
          }
        });
      }
    })

  }

  newTurner(){
    this.userService.newTurn().subscribe({
      next:()=>{},
      error: err => {
        console.log(err);
      },
      complete:() =>{
        console.log("A new day has risen!")
        this.ngOnInit()
      }
    })
  }
}
