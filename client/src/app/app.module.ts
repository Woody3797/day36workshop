import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { WeatherComponent } from './weather/weather.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { WeatherService } from './weather.service';
import { MainComponent } from './main/main.component';

const routes: Routes = [
    { path: '', component: MainComponent, title: 'Main' },
    { path: 'weather', component: WeatherComponent, title: 'Weather' },
    { path: '**', redirectTo: '/', pathMatch: 'full' },
]

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    WeatherComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [ WeatherService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
