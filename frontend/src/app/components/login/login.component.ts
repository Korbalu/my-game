import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationRequestModel} from "../../models/authentication-request-model";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  datas: AuthenticationRequestModel = {email: '', password: ''};

  constructor(private router: Router, private userService: UserService) {
  }

  tosubmit(name: string, pass: string) {
    this.datas.email = name;
    this.datas.password = pass;
    this.userService.login(this.datas).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        console.log("gooood boooy!");
        this.router.navigate(["town"])
      }
    });
  }

  logout() {
    this.userService.logout().subscribe({
      next: () => {
        console.log("baaad boooy!");
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        localStorage.removeItem('token');
      }
    })
  }
  protected readonly localStorage = localStorage;

}
