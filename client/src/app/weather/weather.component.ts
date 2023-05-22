import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map } from 'rxjs';
import { WeatherApiResponse } from '../model';
import { WeatherService } from '../weather.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent implements OnInit {

    activatedRoute = inject(ActivatedRoute)
    title = inject(Title)
    weatherService = inject(WeatherService)
    weather$!: Observable<WeatherApiResponse>
    city = ''

    ngOnInit(): void {
        this.city = this.activatedRoute.snapshot.queryParams['city']
        this.weather$ = this.weatherService.getWeather(this.city)
        this.title.setTitle(this.city + 'weather')
    }

    displayMetric() {
        this.weather$ = this.weatherService.getWeather(this.city)
    }

    displaySI() {
        this.weather$ = this.weatherService.getWeather(this.city, 'standard')
    }

}
