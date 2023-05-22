package ibf2022.csf.day36workshop.server.controller;

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

import jakarta.json.Json;

@Controller
@RequestMapping(path = "/api")
public class WeatherController {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Value("${openweathermap.key}")
    private String appId;

    @GetMapping(path = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getWeather(@RequestParam String city) {
        try {
            String weatherURL = UriComponentsBuilder.fromUriString(API_URL).queryParam("q", city.replaceAll(" ", "+")).queryParam("units", "metric").queryParam("appid", appId).toUriString();
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
}
