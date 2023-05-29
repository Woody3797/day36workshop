package ibf2022.csf.day36workshop.server.service;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.csf.day36workshop.server.model.WeatherInfo;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService {
    
    public static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Value("${openweathermap.key}")
    private String appId;

    public List<WeatherInfo> getWeather(String city, String units) throws WeatherException {
        String weatherURL = UriComponentsBuilder.fromUriString(API_URL)
        .queryParam("q", city.replaceAll(" ", "+"))
        .queryParam("units", units)
        .queryParam("appId", appId).toUriString();

        RestTemplate template = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(weatherURL).accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (RestClientException e) {
            throw new WeatherException(e.getMessage());
        }

        String payload = resp.getBody();
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject data = reader.readObject();

        return data.getJsonArray("weather").stream()
        .map(v -> v.asJsonObject())
        .map(o -> new WeatherInfo(o.getString("main"), o.getString("description"), o.getString("icon")))
        .toList(); 
    }
}
