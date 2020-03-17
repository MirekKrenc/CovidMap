package miro.CovidMap.service;

import miro.CovidMap.data.Punkt;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CSVParser {

    private CSVDataRetriever csvDataRetriever;

    @Autowired
    public CSVParser(CSVDataRetriever csvDataRetriever) {
        this.csvDataRetriever = csvDataRetriever;
    }

    public List<Punkt> getFormattedData() throws URISyntaxException, IOException {
        List<Punkt> punkty = new ArrayList<>();

        String rawData = csvDataRetriever.getRawData();
        StringReader stringReader = new StringReader(rawData);
        Punkt Polska = new Punkt();

        org.apache.commons.csv.CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
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

    /*
    Funkcja podsumowuje niektore dane powodujac ze mniej znaczbikow na mapie jest wyswietlanych
     */
    private Punkt createIndividualData(String key, List<Punkt> value) {
        double latitude = 0;
        double longitude = 0;
        int sumOfConfirmed = 0;
        int sumOfDailyGrowth = 0;

        int count = value.size();

        for(Punkt p: value)
        {
            if (latitude == 0) latitude = p.getLatitude();
            if (longitude == 0)  longitude =  p.getLongitude();
            sumOfConfirmed += p.getConfirmed();
            sumOfDailyGrowth += p.getDailyGrowth();
        }

        //nie usredniam bo czesc krajow ma kolonie i wtedy wychadza dziwne sytuacje. Bedzie brany pierwszy rekord z bazy csv
        //latitude = latitude/count;
        //longitude = longitude/count;

        return new Punkt(latitude, longitude, sumOfDailyGrowth, sumOfConfirmed, key);

    }



}
