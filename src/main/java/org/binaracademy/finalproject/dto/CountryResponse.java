package org.binaracademy.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CountryResponse {

    private Long id;
    private String countryName;

}
