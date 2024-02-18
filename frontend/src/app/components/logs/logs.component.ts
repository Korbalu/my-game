import {Component} from '@angular/core';
import {LogListModel} from "../../models/log-list-model";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css']
})
export class LogsComponent {
  logs: Array<LogListModel> = [];

  constructor(private userService: UserService) {

  }

  ngOnInit() {
    this.userService.logLister().subscribe({
      next:(data) =>{
        this.logs = data;
        console.log(data);
      },
      error: err => {
        console.log(err)
      },
      complete:() =>{

      }
    })
  }
}
