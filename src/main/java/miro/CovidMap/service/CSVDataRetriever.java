package miro.CovidMap.service;

import miro.CovidMap.data.Punkt;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CSVDataRetriever {

    public CSVDataRetriever() {
    }

    private static final String URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    public String getRawData() throws URISyntaxException {
        System.out.println("W metodzie getRawData ....");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(new URI(URL), String.class);
    }
}
