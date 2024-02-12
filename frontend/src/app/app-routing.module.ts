import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {TownComponent} from "./components/town/town.component";
import {CreateTownComponent} from "./components/create-town/create-town.component";
import {TrainComponent} from "./components/train/train.component";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "town", component: TownComponent},
  {path: "create-town", component: CreateTownComponent},
  {path: "train", component: TrainComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
