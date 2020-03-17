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

    private String getRawData() throws URISyntaxException {
        System.out.println("W metodzie getRawData ....");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(new URI(URL), String.class);
    }

    public List<Punkt> getFormattedData() throws URISyntaxException, IOException {
        List<Punkt> punkty = new ArrayList<>();

        String rawData = getRawData();
        StringReader stringReader = new StringReader(rawData);
        Punkt Polska = new Punkt();

        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
        for (CSVRecord record: parser)
        {

            double latitude = Double.parseDouble(record.get("Lat"));
            double longitude = Double.parseDouble(record.get("Long"));
            int dailyGrowth = Integer.parseInt(record.get(record.size()-1)) - Integer.parseInt(record.get(record.size()-2));
            int confirmed = Integer.parseInt(record.get(record.size()-1));
            String country = record.get(1);
            punkty.add(new Punkt(latitude, longitude, dailyGrowth, confirmed, country));
        }

        //wydzielam dane dla pojedynczych krajow
        Map<String, List<Punkt>> punktMap = punkty.stream()
                .collect(groupingBy(item -> item.getCountry()));

        List<Punkt> resultList = new ArrayList<>();

        for (Map.Entry<String, List<Punkt>> mapaWgKraju: punktMap.entrySet())
        {
            if (mapaWgKraju.getKey().equals("Poland"))
            {
                Polska = createIndividualData(mapaWgKraju.getKey(), mapaWgKraju.getValue());
            }
            else {
                resultList.add(createIndividualData(mapaWgKraju.getKey(), mapaWgKraju.getValue()));
            }
        }

        resultList.add(Polska);

        return resultList;
    }

    private Punkt createIndividualData(String key, List<Punkt> value) {
        double latitude = 0;
        double longitude = 0;
        int sumOfConfirmed = 0;
        int sumOfDailyGrowth = 0;

        int count = value.size();

        for(Punkt p: value)
        {
            latitude += p.getLatitude();
            longitude += p.getLongitude();
            sumOfConfirmed += p.getConfirmed();
            sumOfDailyGrowth += p.getDailyGrowth();
        }

        latitude = latitude/count;
        longitude = longitude/count;

        return new Punkt(latitude, longitude, sumOfDailyGrowth, sumOfConfirmed, key);

    }

}
