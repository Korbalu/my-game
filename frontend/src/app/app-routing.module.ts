import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {TownComponent} from "./components/town/town.component";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "town", component: TownComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
