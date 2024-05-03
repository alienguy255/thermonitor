package org.scottsoft.monitor.notification;

import j2html.TagCreator;
import j2html.tags.ContainerTag;
import j2html.tags.specialized.BodyTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scottsoft.monitor.location.Location;
import org.scottsoft.monitor.thermostat.Thermostat;
import org.scottsoft.monitor.thermostat.IThermostatSample;
import org.scottsoft.monitor.weather.IWeatherSample;
import org.scottsoft.monitor.location.LocationService;
import org.scottsoft.monitor.thermostat.ThermostatService;
import org.scottsoft.monitor.weather.WeatherService;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
@ManagedResource
public class DailyRuntimeEmailReportSender {

    private static final DecimalFormat DOUBLE_FORMATTER = new DecimalFormat(".##");

    private final LocationService locationService;
    private final ThermostatService thermostatService;
    private final WeatherService weatherService;
    private final JavaMailSender mailSender;

    // TODO: this should be a template instead of building style and body in code
    @ManagedOperation
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendDailyRuntimeReport() {
        try {
            Instant now = Instant.now();
            Instant twentyFourHoursAgo = now.minus(24, ChronoUnit.HOURS);
            ContainerTag<BodyTag> body = TagCreator.body();
            for (Location location : this.locationService.getAllLocations()) {
                List<IWeatherSample> weatherSamples = this.weatherService.getSamples(location.getId(), twentyFourHoursAgo.toEpochMilli(), now.toEpochMilli());

                double avgOutsideTemp = weatherSamples.stream()
                        .filter(sample -> !Double.isNaN(sample.getCurrentTemp()))
                        .mapToDouble(IWeatherSample::getCurrentTemp)
                        .average().orElse(Double.NaN);

                if (!Double.isNaN(avgOutsideTemp)) {
                    body.with(TagCreator
                            .h3("Average outside temperature for " + location.getDescription() + " in the past 24 hours: " + DailyRuntimeEmailReportSender.DOUBLE_FORMATTER.format(avgOutsideTemp) + " F")
                            .attr("style", "margin-top:0px;margin-bottom:0px;"));

                    Optional<IWeatherSample> minOutsideTempSampleOptional = weatherSamples.stream()
                            .filter(sample -> !Double.isNaN(sample.getCurrentTemp()))
                            .min(Comparator.comparingDouble(IWeatherSample::getCurrentTemp));
                    body.with(TagCreator
                            .h3("Past 24 hr low: " + minOutsideTempSampleOptional.get().getCurrentTemp() + " F at " + new SimpleDateFormat("hh:mm a").format(minOutsideTempSampleOptional.get().getTime()))
                            .attr("style", "margin-top:0px;margin-bottom:0px;"));

                    Optional<IWeatherSample> maxOutsideTempSampleOptional = weatherSamples.stream()
                            .filter(sample -> !Double.isNaN(sample.getCurrentTemp()))
                            .max(Comparator.comparingDouble(IWeatherSample::getCurrentTemp));
                    body.with(TagCreator.h3("Past 24 hr high: " + maxOutsideTempSampleOptional.get().getCurrentTemp() + " F at " + new SimpleDateFormat("hh:mm a").format(maxOutsideTempSampleOptional.get().getTime())).attr("style", "margin-top:0px;margin-bottom:0px;"));

                    body.with(TagCreator.br());
                } else {
                    body.with(TagCreator
                            .h3("Weather data is unavailable. Check if you have a weather source configured in order to see more details in this section")
                            .attr("style", "margin-top:0px;margin-bottom:0px;"));
                }

                Iterable<Thermostat> thermostats = this.thermostatService.getThermostatsByLocation(location);
                for (Thermostat thermostat : thermostats) {
                    List<IThermostatSample> thermostatSamples = this.thermostatService.getThermostatSamples(thermostat.getId(), twentyFourHoursAgo.toEpochMilli(), now.toEpochMilli());
                    log.info("Retrieved {} thermostat samples for {}", thermostatSamples.size(), thermostat.getName());
                    body.with(TagCreator.h3(thermostat.getName() + ":"));
                    double runtimeInMins = thermostatSamples.stream()
                            .mapToDouble(IThermostatSample::tstate)
                            .filter(sample -> !Double.valueOf(sample).isNaN())
                            .sum();
                    body.with(TagCreator.p("Past 24hr runtime: " + this.runtimeDisplayString(runtimeInMins)).attr("style", "margin-top:0px;margin-bottom:0px;"));
                    body.with(TagCreator.p("Past 24hr runtime percentage: " + DailyRuntimeEmailReportSender.DOUBLE_FORMATTER.format(runtimeInMins / 1440.0 * 100.0) + "%").attr("style", "margin-top:0px;margin-bottom:0px;"));
                    body.with(TagCreator.br());
                }
            }
            String emailContent = TagCreator.html().with(body).renderFormatted();
            log.info("Emailing daily runtime report...");
            log.info("email content={}", emailContent);

            MimeMailMessageSender.createMailer(mailSender)
                    .addRecipients("scottmlaplante@gmail.com")
                    .setSubject("Daily heating report for " + new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(new Date(twentyFourHoursAgo.toEpochMilli())) + " to " + new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(new Date(now.toEpochMilli())))
                    .setContent(emailContent)
                    .sendMail();
        } catch (Exception e) {
            log.warn("An error occurred running email report.", e);
        }
    }

    private String runtimeDisplayString(double runtimeInMins) {
        final long hours = TimeUnit.MINUTES.toHours((long)runtimeInMins);
        final long remainMinutes = (long)runtimeInMins - TimeUnit.HOURS.toMinutes(hours);
        return (hours > 0L) ? String.format("%02d hour(s), %02d min(s)", hours, remainMinutes) : String.format("%02d min(s)", remainMinutes);
    }

}
