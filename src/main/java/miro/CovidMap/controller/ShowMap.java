package miro.CovidMap.controller;

import miro.CovidMap.data.Punkt;
import miro.CovidMap.service.CSVDataRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShowMap {

    private List<Punkt> punkty;

    @Autowired
    public ShowMap(CSVDataRetriever punktyService) {
        try {
            this.punkty = punktyService.getFormattedData();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        punkty.add(new Punkt(52.02, 23.07, 0, "AWF Biała Podlaska"));
//        punkty.add(new Punkt(51.47, 19.28, 0, "Lodz"));
    }

    @GetMapping("/")
    public String showMap(Model model)
    {
        model.addAttribute("punkty", punkty);
        return "mapa";
    }
}
