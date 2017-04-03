package ua.bot.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.bot.domain.Announcement;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stani on 02-Apr-17.
 */
@Component
@Setter
@Getter
@Log
public class Worksheet {

    @Autowired
    private KlubokService klubokService;
    private Announcement announcement;

    @PostConstruct
    private void init() {
        List<String> photoPaths = Arrays.asList("C:\\Users\\stani\\Desktop\\58343.jpg"
                , "C:\\Users\\stani\\Desktop\\shop_items_catalog_image517.jpg");
        setAnnouncement(Announcement.builder()
                .summary("Носки")
                .description("Очень яркие и качетвенные носки фирмы Next")
                .price(13d)
                .category("Детские вещи")
                .subCategory("Одежда")
                .type("Носки, гольфы")
                .photos(photoPaths.stream().map(File::new).collect(Collectors.toList()))
                .build());

    }

    public String addAnnouncementAction(ActionEvent event) {
        log.info("addAnnouncementAction");
        klubokService.addAnnouncement(announcement);
        return null;
    }
}
