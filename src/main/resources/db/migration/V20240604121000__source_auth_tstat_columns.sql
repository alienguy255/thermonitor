
ALTER TABLE thermostat_source ADD COLUMN IF NOT EXISTS source_type VARCHAR(100);

-- by default set existing thermostats to radio_thermostat type, then require column to contain data
UPDATE thermostat_source set source_type = 'RADIO_THERMOSTAT';
ALTER TABLE thermostat_source ALTER COLUMN source_type VARCHAR(100) not null;

ALTER TABLE thermostat_source ADD auth_token VARCHAR(512);