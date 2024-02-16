import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {ArmyListModel} from "../../models/army-list-model";
import {TownListModel} from "../../models/town-list-model";

@Component({
  selector: 'app-list-of-towns',
  templateUrl: './list-of-towns.component.html',
  styleUrls: ['./list-of-towns.component.css']
})
export class ListOfTownsComponent {
  townList: Array<TownListModel> = [];
  constructor(private userService: UserService) {

  }

  ngOnInit(){
    this.userService.townLister().subscribe({
      next:(data) =>{
        this.townList = data;
      },
      error: (err)=>{
        console.log(err);
      },
      complete:()=>{
        console.log("Good Job Man!")
      }
    })
  }
}
