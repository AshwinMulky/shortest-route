package com.routing.model;

import java.util.List;

import lombok.Data;

@Data
public class CountryNode {
    
    private String cca3;

    private List<String> borders;
}
