package org.scottsoft.monitor.location;

import java.util.UUID;

public record LocationDTO(UUID id, String description, double longitude, double latitude) {}
