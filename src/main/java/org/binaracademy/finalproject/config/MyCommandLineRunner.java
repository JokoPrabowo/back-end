package org.binaracademy.finalproject.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.data.CountryData;
import org.binaracademy.finalproject.data.ScheduleTimeData;
import org.binaracademy.finalproject.data.TimeData;
import org.binaracademy.finalproject.entity.CityEntity;
import org.binaracademy.finalproject.entity.CountryEntity;
import org.binaracademy.finalproject.entity.ScheduleTimeEntity;
import org.binaracademy.finalproject.services.CityService;
import org.binaracademy.finalproject.services.CountryService;
import org.binaracademy.finalproject.services.ScheduleTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.DataInput;
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

    @Autowired
    private final ScheduleTimeService scheTimeService;

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
                    country1.getCity().forEach(cityData ->
                            cityService.create(new CityEntity(null, cityData.getName(), LocalDateTime.now(), null, country1.getId(), null)));
                });
            }catch (IOException e){
                log.info("Unable to save country : {}", e.getMessage());
            }
        }

        if(scheTimeService.getAll().isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<ScheduleTimeData>> typeReference = new TypeReference<List<ScheduleTimeData>>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/scheduleTime.json");

            try {
                List<ScheduleTimeData> scheduleTime = mapper.readValue(inputStream, typeReference);
                scheduleTime.forEach(schedule -> {
                    List<TimeData> schTime = schedule.getScheduleTime();

                    schTime.forEach(time -> {
                        scheTimeService.create(new ScheduleTimeEntity(time.getId(), schedule.getDay(), time.getStart_time(), time.getEnd_time(), LocalDateTime.now(), null));
                    })
                ;});
            }catch (IOException e){
                log.info("Unable to save Schedule Time : {}", e.getMessage());
            }
        }
    }

}
