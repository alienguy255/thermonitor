package org.scottsoft.monitor.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<LocationDTO>> getLocations() {
        List<LocationDTO> locations = locationService.getAllLocations().stream()
                .map(Location::toDto)
                .toList();

        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    private record LocationRequestBody(String description, double longitude, double latitude) {}

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createLocation(@RequestBody LocationRequestBody location) {
        return new ResponseEntity<>(locationService.createLocation(location.description(), location.longitude(), location.latitude()), HttpStatus.OK);
    }

}
