package ibf2022.csf.day36workshop.server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {
    
    public static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Value("${openweathermap.key}")
    private String appId;

    public void getWeather(String city) {
        String weatherURL = UriComponentsBuilder.fromUriString(API_URL).queryParam("q", city.replaceAll(" ", "+")).queryParam("units", "metric").queryParam("appId", appId).toUriString();
        RestTemplate template = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(weatherURL).build();
        try {
            ResponseEntity<String> resp = template.exchange(req, String.class);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
