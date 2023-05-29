package ibf2022.csf.day36workshop.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.csf.day36workshop.server.service.WeatherException;
import ibf2022.csf.day36workshop.server.service.WeatherService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

@Controller
@RequestMapping(path = "/api")
public class WeatherController {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Value("${openweathermap.key}")
    private String appId;

    @Autowired
    private WeatherService weatherService;

    @GetMapping(path = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getWeather(@RequestParam String city, @RequestParam(defaultValue = "metric") String units) {
        try {
            String weatherURL = UriComponentsBuilder.fromUriString(API_URL).queryParam("q", city.replaceAll(" ", "+")).queryParam("units", units).queryParam("appid", appId).toUriString();
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = template.getForEntity(weatherURL, String.class);
            return resp;
        } catch (Exception e) {
            return ResponseEntity.status(400).body(
                Json.createObjectBuilder()
                .add("error", e.getMessage()).build().toString()
            );
        }
    }

    @GetMapping(path = "/weather2")
    @ResponseBody
    public ResponseEntity<String> getWeatherFromService(@RequestParam String city, @RequestParam(defaultValue = "metric") String units) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        try {
            weatherService.getWeather(city, units).stream()
            .map(d -> Json.createObjectBuilder()
            .add("main", d.main())
            .add("description", d.description())
            .add("icon", d.icon())
            .build())
            .forEach(arrBuilder::add);

            return ResponseEntity.ok().body(arrBuilder.build().toString());
        } catch (WeatherException e) {
            return ResponseEntity.status(400).body(Json.createObjectBuilder()
            .add("error", e.getMessage()).build().toString());
        }
    }
}
