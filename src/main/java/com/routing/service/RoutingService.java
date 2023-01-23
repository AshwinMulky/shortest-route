package com.routing.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.routing.dto.RouteResponseDTO;
import com.routing.model.CountryNode;

@Service
public class RoutingService {

    @Autowired
    private CountryMap countryMap;

    public ResponseEntity<?> getRouting(String origin, String destination) {
        Map<String, List<String>> parentMap = countryMap.getParentMap();
        if(parentMap.get(origin) == null || parentMap.get(destination) == null) {
            return new ResponseEntity<>("Invalid source or destination", HttpStatus.BAD_REQUEST);
        }
        BFS(origin, destination, parentMap);
        RouteResponseDTO routeResponseDTO = retrievePath(parentMap, origin, destination);
        return routeResponseDTO.getRoute().size() > 1 ? 
               new ResponseEntity<>(routeResponseDTO, HttpStatus.OK) :
               new ResponseEntity<>("No Land Crossing Found", HttpStatus.BAD_REQUEST);
        
    }

    //Breadth First Search
    private void BFS(String origin, String destination, Map<String, List<String>> parentMap) {
        Queue<CountryNode> queue = new LinkedList<>();
        Map<String, Integer> distanceMap = countryMap.getDistanceMap();

        queue.offer(countryMap.getNode(origin));
        distanceMap.put(origin, 0);
        parentMap.get(origin).add("START");

        while(!queue.isEmpty()) {
            CountryNode node = queue.poll();
            for(String neighbour: node.getBorders()) {
                if(distanceMap.get(neighbour) > distanceMap.get(node.getCca3())+1) {
                    distanceMap.put(neighbour, distanceMap.get(node.getCca3())+1);
                    queue.offer(countryMap.getNode(neighbour));

                    parentMap.get(neighbour).clear();
                    parentMap.get(neighbour).add(node.getCca3());

                    //Stop if the destination is found - avoid visiting all
                    if(neighbour.equals(destination)) return;
                }
            }
        }
        
    }

    private RouteResponseDTO retrievePath(Map<String, List<String>> parentMap, String origin, String destination) {
        List<String> path = new ArrayList<>();
        List<String> parents = parentMap.get(destination);
        path.add(destination);
        while(parents.size() > 0 && !parents.get(0).equals("START")) {
            path.add(parents.get(0));
            parents = parentMap.get(parents.get(0));
        }
        Collections.reverse(path);
        return new RouteResponseDTO(path);
    }
    
}
