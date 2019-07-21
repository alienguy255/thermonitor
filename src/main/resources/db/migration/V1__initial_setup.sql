CREATE TABLE location (
  id VARCHAR_IGNORECASE(36) not null,
  zip_code VARCHAR(5) not null,
  city VARCHAR(50) not null,
  state VARCHAR(2) not null,
  PRIMARY KEY (id)
);

CREATE TABLE thermostat (
  id VARCHAR_IGNORECASE(36) not null,
  name VARCHAR(100) not null,
  ip_address VARCHAR(15) not null,
  location_id VARCHAR_IGNORECASE(36) not null,
  model VARCHAR(100) DEFAULT 'UNKNOWN' not null,
  PRIMARY KEY (id),
  CONSTRAINT location_id FOREIGN KEY(location_id) references location(id)
);

CREATE TABLE weather_source (
  id VARCHAR_IGNORECASE(36) not null,
  location_id VARCHAR_IGNORECASE(36) not null,
  url VARCHAR(255) not null,
  api_target VARCHAR(100) DEFAULT 'UNKNOWN' not null
);
