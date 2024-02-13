import {Component} from '@angular/core';
import {UserService} from "../../services/user.service";
import {UnitListModel} from "../../models/unit-list-model";
import {BuildingsModel} from "../../models/buildings-model";

@Component({
  selector: 'app-build',
  templateUrl: './build.component.html',
  styleUrls: ['./build.component.css']
})
export class BuildComponent {
  buildings: Array<BuildingsModel> = [];

  constructor(private userService: UserService) {

  }

  ngOnInit() {
    this.userService.buildingLister().subscribe({
      next: (data) => {
        this.buildings = data;
      },
      error: err => {
        console.log(err)
      },
      complete: () => {
      }
    })
  }
  increaser(building: string) {
    this.userService.buildingIncreaser(building).subscribe({
      next: () => {
      },
      error: err => {
        console.log(err)
      },
      complete: () => {
      }
    })
  }
}
