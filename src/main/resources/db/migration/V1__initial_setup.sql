CREATE TABLE location (
  id UUID not null,
  description VARCHAR(100) not null,
  longitude DOUBLE PRECISION,
  latitude DOUBLE PRECISION,
  PRIMARY KEY (id)
);

CREATE TABLE thermostat_source (
  id UUID not null,
  name VARCHAR(100) not null,
  url VARCHAR(2083) not null,
  location_id UUID not null,
  PRIMARY KEY (id),
  CONSTRAINT thermostat_src_loc_id_fkey FOREIGN KEY(location_id) references location(id)
);

CREATE TABLE weather_source (
  id UUID not null,
  location_id UUID not null,
  url VARCHAR(2083) not null,
  PRIMARY KEY (id),
  CONSTRAINT weather_src_loc_id_fkey FOREIGN KEY(location_id) references location(id)
);

