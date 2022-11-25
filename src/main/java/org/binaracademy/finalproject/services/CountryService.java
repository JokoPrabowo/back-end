package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.CountryEntity;

import java.util.List;

public interface CountryService {

    CountryEntity create(CountryEntity country);
    List<CountryEntity> getAll();

}
