import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {ArmyListModel} from "../../models/army-list-model";

@Component({
  selector: 'app-town',
  templateUrl: './town.component.html',
  styleUrls: ['./town.component.css']
})
export class TownComponent implements OnInit{

  armyList: Array<ArmyListModel> = [];

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.armyAsker().subscribe({
      next: (data) => {
        this.armyList = data;
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        console.log("done boooy!");
      }
    });
  }

}
