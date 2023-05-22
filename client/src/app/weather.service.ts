import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { WeatherApiResponse } from "./model";

@Injectable()
export class WeatherService {

    http = inject(HttpClient)
    
    getWeather(city: string): Observable<WeatherApiResponse> {
        const params = new HttpParams().set('city', city)
        return this.http.get<WeatherApiResponse>('/api/weather', {params})
    }
}