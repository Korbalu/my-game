import {Component} from '@angular/core';
import {UserService} from "../../services/user.service";
import {UnitListModel} from "../../models/unit-list-model";

@Component({
  selector: 'app-train',
  templateUrl: './train.component.html',
  styleUrls: ['./train.component.css']
})
export class TrainComponent {
  units: Array<UnitListModel> = [];

  constructor(private userService: UserService) {

  }

  ngOnInit(): void {
    this.userService.unitLister().subscribe({
      next: (data) => {
        this.units = data;
      },
      error: err => {
        console.log(err)
      },
      complete: () => {
      }
    })
  }

  increaser(unit: string) {
    this.userService.unitIncreaser(unit).subscribe({
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
