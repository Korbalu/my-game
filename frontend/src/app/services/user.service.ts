import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthenticationRequestModel} from "../models/authentication-request-model";
import {ArmyListModel} from "../models/army-list-model";
import {TokenModel} from "../models/token-model";
import {BuildingListModel} from "../models/building-list-model";
import {AltBuildingListModel} from "../models/alt-building-list-model";
import {RegisterRequestModel} from "../models/register-request-model";
import {RaceNameModel} from "../models/race-name-model";
import {CreateTownComponent} from "../components/create-town/create-town.component";
import {TownCreationModel} from "../models/town-creation-model";
import {TownIdModel} from "../models/town-id-model";
import {LoggedInUserIdModel} from "../models/logged-in-user-id-model";
import {UnitListModel} from "../models/unit-list-model";
import {BuildingsModel} from "../models/buildings-model";
import {TownDetailsModel} from "../models/town-details-model";

const BASE_URL: string = "http://localhost:8080";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private token: string = "";

  constructor(private http: HttpClient) {
  }

  login(auth: AuthenticationRequestModel): Observable<any> {
    return this.http.post(BASE_URL + "/api/auth/auth", auth);
  }

  register(reg: RegisterRequestModel): Observable<any> {
    return this.http.post(BASE_URL + "/api/auth/reg", reg)
  }

  raceLister(): Observable<Array<RaceNameModel>> {
    // // @ts-ignore
    // this.token = localStorage.getItem("token");
    // const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.get<Array<RaceNameModel>>(BASE_URL + "/api/auth/races")
  }

  townCreator(town: TownCreationModel): Observable<any> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.post(BASE_URL + "/api/town", town, {headers})
  }

  townIdentity(): Observable<any> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    return this.http.get<TownIdModel>(BASE_URL + "/api/town/townid", {headers})
  }

  logout(): Observable<any> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    return this.http.post(BASE_URL + "/api/auth/logout", {headers});
  }

  getToken(token: TokenModel): Observable<any> {
    return this.http.get<TokenModel>(BASE_URL + "/api/auth/token")
  }

  userAsker(): Observable<any> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    return this.http.get<LoggedInUserIdModel>(BASE_URL + "/api/town/user", {headers})
  }

  armyAsker(userId: number): Observable<Array<ArmyListModel>> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    return this.http.get<Array<ArmyListModel>>(BASE_URL + "/api/army/user/" + userId, {headers})
  }

  buildingAsker(townId: number): Observable<Array<BuildingListModel>> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    return this.http.get<Array<BuildingListModel>>(BASE_URL + "/api/town/building/" + townId, {headers})
  }

  buildingAsker2(townId: number): Observable<Array<AltBuildingListModel>> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    return this.http.get<Array<AltBuildingListModel>>(BASE_URL + "/api/town/building2/" + townId, {headers})
  }

  unitLister(): Observable<Array<UnitListModel>> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.get<Array<UnitListModel>>(BASE_URL + "/api/army/list", {headers})
  }

  buildingLister(): Observable<Array<BuildingsModel>> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.get<Array<BuildingsModel>>(BASE_URL + "/api/town/list", {headers})
  }

  unitIncreaser(unit: string): Observable<any> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.put(BASE_URL + "/api/army/increase", unit, {headers})
  }

  buildingIncreaser(building: string): Observable<any> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.put(BASE_URL + "/api/town/increase", building, {headers})
  }

  townDetails(): Observable<any> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.get(BASE_URL + "/api/town/details", {headers})
  }

  newTurn():Observable<any>{
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.get(BASE_URL + "/api/town/newTurn", {headers})
  }
}
