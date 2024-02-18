import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {TownComponent} from "./components/town/town.component";
import {CreateTownComponent} from "./components/create-town/create-town.component";
import {TrainComponent} from "./components/train/train.component";
import {BuildComponent} from "./components/build/build.component";
import {ListOfTownsComponent} from "./components/list-of-towns/list-of-towns.component";
import {LogsComponent} from "./components/logs/logs.component";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "town", component: TownComponent},
  {path: "create-town", component: CreateTownComponent},
  {path: "train", component: TrainComponent},
  {path: "build", component: BuildComponent},
  {path: "list-of-towns", component: ListOfTownsComponent},
  {path: "logs", component: LogsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
