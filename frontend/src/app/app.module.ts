import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { HeaderComponent } from './components/header/header.component';
import { TownComponent } from './components/town/town.component';
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import { CreateTownComponent } from './components/create-town/create-town.component';
import { TrainComponent } from './components/train/train.component';
import { BuildComponent } from './components/build/build.component';
import { ListOfTownsComponent } from './components/list-of-towns/list-of-towns.component';
import { LogsComponent } from './components/logs/logs.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    TownComponent,
    CreateTownComponent,
    TrainComponent,
    BuildComponent,
    ListOfTownsComponent,
    LogsComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
