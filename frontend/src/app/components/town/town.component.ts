import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {ArmyListModel} from "../../models/army-list-model";
import {BuildingListModel} from "../../models/building-list-model";

@Component({
  selector: 'app-town',
  templateUrl: './town.component.html',
  styleUrls: ['./town.component.css']
})
export class TownComponent implements OnInit {

  armyList: Array<ArmyListModel> = [];
  buildingList: Array<BuildingListModel> = [];
  // buildings!:{};
  townId!: number;
  owner: string='';

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.townId = 2;
    this.userService.armyAsker().subscribe({
      next: (data) => {
        this.armyList = data;
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        console.log("done boooy!");
        this.owner = this.armyList[0].owner;
      }
    });
    this.userService.buildingAsker(this.townId).subscribe({
      next: (data) => {
          this.buildingList = data;
        console.log(this.buildingList);
        // this.buildingList = data;
        // console.log(this.buildingList);
      },
      error: err => {
        console.log(err);

      },
      complete: () => {
        console.log("done again boooy!");
      }
    });
  }

}
