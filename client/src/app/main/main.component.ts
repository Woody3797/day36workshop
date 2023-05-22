import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Params, Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

    form!: FormGroup
    fb = inject(FormBuilder)
    router = inject(Router)

    ngOnInit(): void {
        this.form = this.fb.group({
            city: this.fb.control('Singapore', [Validators.required]),
            units: this.fb.control('metric', [Validators.required])
        })
    }

    getWeather() {
        console.info(this.form.value)
        const query = this.form.value
        const queryParams: Params = { units: query.units }
        this.router.navigate([ '/weather', query.city ], { queryParams: queryParams })
    }


}
