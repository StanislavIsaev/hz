package ua.bot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

/**
 * Created by stani on 02-Apr-17.
 */
@Getter
@Setter
@Builder
public class Announcement {

    private String summary;
    private CharSequence description;
    private Double price;
    private String category;
    private String subCategory;
    private String type;
    private List<File> photos;

}
