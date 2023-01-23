package com.routing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.routing.service.RoutingService;

@RestController
public class RoutingController {

    @Autowired
    private RoutingService routingService;
    
    @GetMapping("/routing/{origin}/{destination}")
    public ResponseEntity<?> getRouting(@PathVariable String origin, @PathVariable String destination) {
        return routingService.getRouting(origin, destination);
    }
}
