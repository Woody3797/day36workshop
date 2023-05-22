import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map } from 'rxjs';
import { WeatherApiResponse } from '../model';
import { WeatherService } from '../weather.service';

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent implements OnInit {

    activatedRoute = inject(ActivatedRoute)
    weatherService = inject(WeatherService)
    weather$!: Observable<WeatherApiResponse>
    city = ''

    ngOnInit(): void {
        this.city = this.activatedRoute.snapshot.queryParams['city']
        this.weather$ = this.weatherService.getWeather(this.city)
    }

    displayMetric() {
        this.weather$ = this.weatherService.getWeather(this.city)
    }

    displaySI() {
        this.weather$ = this.weatherService.getWeather(this.city).pipe(
            map(data => {
                data.main.temp = Math.round((data.main.temp + 273)*10)/10
                data.main.temp_max = Math.round((data.main.temp_max + 273)*10)/10
                data.main.temp_min = Math.round((data.main.temp_min + 273)*10)/10
                return data
            })
        )
    }

}
