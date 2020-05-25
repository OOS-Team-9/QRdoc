package project.controller.extractor;

import project.model.*;
import project.model.information.Information;


import java.util.ArrayList;
import java.io.IOException;
import java.util.regex.Pattern;

public class LinkExtractor extends InfoExtractor {

    public LinkExtractor(MyDoc doc) throws IOException {
        super(doc);
        pattern = Pattern.compile("(((http(s)?:\\\\/\\\\/)\\\\S+(\\\\.[^(\\\\n|\\\\t|\\\\s,)]+)+)|((http(s)?:\\\\/\\\\/)?\" +\n" +
                "(([a-zA-z\\\\-_]+[0-9]*)|([0-9]*[a-zA-z\\\\-_]+)){2,}(\\\\.[^(\\\\n|\\\\t|\\\\s,)]+)+))+");
    }

    public ArrayList<ArrayList<Information>> getLinkList() {
        return super.getInfoList();
    }
}
