import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthenticationRequestModel} from "../models/authentication-request-model";
import {ArmyListModel} from "../models/army-list-model";
import {TokenModel} from "../models/token-model";
import {BuildingListModel} from "../models/building-list-model";
import {AltBuildingListModel} from "../models/alt-building-list-model";

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
  logout():Observable<any>{
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    return this.http.post(BASE_URL + "/api/auth/logout", {headers});
  }
  getToken(token: TokenModel):Observable<any>{
    return this.http.get<TokenModel>(BASE_URL + "/api/auth/token")
  }

  armyAsker(): Observable<Array<ArmyListModel>> {
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    return this.http.get<Array<ArmyListModel>>(BASE_URL + "/api/army", {headers})
  }
  buildingAsker(townId: number):Observable<Array<BuildingListModel>>{
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    return this.http.get<Array<BuildingListModel>>(BASE_URL + "/api/town/building/" + townId, {headers})
  }
  buildingAsker2(townId: number):Observable<Array<AltBuildingListModel>>{
    // @ts-ignore
    this.token = localStorage.getItem("token");
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    return this.http.get<Array<AltBuildingListModel>>(BASE_URL + "/api/town/building2/" + townId, {headers})
  }
}
