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
        this.city = this.activatedRoute.snapshot.params['city']
        const units = this.activatedRoute.snapshot.queryParams['units'] || 'metric'
        this.title.setTitle(this.city + 'weather')
        this.weather$ = this.weatherService.getWeather(this.city, units)
    }


}
