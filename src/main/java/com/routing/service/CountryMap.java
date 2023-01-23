package com.routing.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import org.springframework.web.client.RestTemplate;

import com.routing.model.CountryNode;

//Component to keep the data ready 
//Initialized during the application startup
@Component
public class CountryMap {

    @Autowired
    private RestTemplate restTemplate;

    private Map<String, CountryNode> adjacencyMap = new HashMap<>();
    private Map<String, Integer> distanceMap = new HashMap<>();
    private Map<String, List<String>> parentMap = new HashMap<>();

    @PostConstruct
    public void prepareTheGraph() {
        ResponseEntity<CountryNode[]> response = restTemplate.getForEntity("https://raw.githubusercontent.com/mledoze/countries/master/countries.json", CountryNode[].class);
        CountryNode[] countryData = response.getBody();
        List<CountryNode> adjacencyList  = Arrays.asList(countryData);
        adjacencyMap = adjacencyList.stream().collect(Collectors.toMap(CountryNode::getCca3, Function.identity()));

        //Prepare the initial distance map and parent map
        for(String key: adjacencyMap.keySet()) {
            distanceMap.put(key, Integer.MAX_VALUE);
            parentMap.put(key, new ArrayList<>());
        }
    }

    public CountryNode getNode(String countryCode) {
        return adjacencyMap.get(countryCode);
    }

    public Map<String, Integer> getDistanceMap() {
        //send a deep copy
        return SerializationUtils.clone(new HashMap<>(distanceMap));
    }

    public Map<String, List<String>> getParentMap() {
        //send a deep copy
        return SerializationUtils.clone(new HashMap<>(parentMap));
    }
    
}
