package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.CountryEntity;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    CountryEntity create(CountryEntity country);
    List<CountryEntity> getAll();
    List<CountryEntity> createAll(List<CountryEntity> countryList);
    CountryEntity getOneCountry(Long id);

}
