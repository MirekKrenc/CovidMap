package miro.CovidMap;

import miro.CovidMap.data.Punkt;
import miro.CovidMap.service.CSVDataRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;


@SpringBootApplication
public class CovidMapApplication{

//	@Autowired
//	private CSVDataRetriever punktyService;

	public static void main(String[] args) {
		SpringApplication.run(CovidMapApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner() {
//		return args -> {
//			List<Punkt> punkty = punktyService.getFormattedData();
//			Map<String, List<Punkt>> punktMap = punkty.stream()
//					.collect(groupingBy(item -> item.getCountry()));
//
//			punkty.stream().limit(10)
//					.forEach(System.out::println);
//
//		};
//	}


}
