package com.routing.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteResponseDTO {
    
    private List<String> route;
}
