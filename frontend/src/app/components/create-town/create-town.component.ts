import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {RaceNameModel} from "../../models/race-name-model";
import {TownCreationModel} from "../../models/town-creation-model";

@Component({
  selector: 'app-create-town',
  templateUrl: './create-town.component.html',
  styleUrls: ['./create-town.component.css']
})
export class CreateTownComponent implements OnInit {
  townForm!: FormGroup
  races: Array<RaceNameModel> = [];

  constructor(private router: Router, private userService: UserService, private formBuilder: FormBuilder) {
    this.townForm = this.formBuilder.group({
      'name': [''],
      'race': ['']
    })

  }

  ngOnInit() {
    this.userService.raceLister().subscribe({
      next: (data) => {
        this.races = data;
      },
      error: err => {
        console.log(err)
      },
      complete: () => {
      }
    })
  }

  cont() {
    let data: TownCreationModel = {race: '', name: ''};
    // @ts-ignore
    data.name = this.townForm.value.name;
    // @ts-ignore
    data.race = this.townForm.get('race')?.value;
    // @ts-ignore
    this.userService.townCreator(data).subscribe({
      next: () => {
      },
      error: err => {
        console.log(err)
      },
      complete: () => {
        this.router.navigate(["town"])
      }
    })
  }
}
