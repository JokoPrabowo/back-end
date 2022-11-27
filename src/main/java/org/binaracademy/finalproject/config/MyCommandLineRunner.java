package org.binaracademy.finalproject.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.data.CountryData;
import org.binaracademy.finalproject.entity.AirportEntity;
import org.binaracademy.finalproject.entity.CityEntity;
import org.binaracademy.finalproject.entity.CountryEntity;
import org.binaracademy.finalproject.entity.PesawatEntity;
import org.binaracademy.finalproject.services.AirportService;
import org.binaracademy.finalproject.services.CityService;
import org.binaracademy.finalproject.services.CountryService;
import org.binaracademy.finalproject.services.PesawatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MyCommandLineRunner implements CommandLineRunner {

    private final CityService cityService;
    private final CountryService countryService;

    private final AirportService airportService;

    private final PesawatService pesawatService;

    @Override
    public void run(String... args) throws Exception {
        if (countryService.getAll().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<CountryData>> typeReference = new TypeReference<List<CountryData>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/countryandcity.json");
            try {
                List<CountryData> country = mapper.readValue(inputStream, typeReference);
                country.forEach(country1 -> {
                    countryService.create(new CountryEntity(country1.getId(), country1.getName(), LocalDateTime.now(), null));
                    country1.getCity().forEach(cityData -> {
                        CityEntity city = cityService.create(new CityEntity(null, cityData.getName(), LocalDateTime.now(), null, country1.getId(), null));
                        AirportEntity airport = airportService.create(new AirportEntity(null, cityData.getAirport().getName(),
                                city.getId(), LocalDateTime.now(), null, null));
                        cityData.getAirport().getPesawat().forEach(pesawatData -> {
                            pesawatService.create(new PesawatEntity(null, pesawatData.getName(), airport.getId(), null, null, null));
                        });
                    });
                });
            }catch (IOException e){
                log.info("Unable to save country : {}", e);
            }
        }
    }

}
