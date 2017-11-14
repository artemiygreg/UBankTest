package artemiygreg.ubanktest.utils;

import java.util.ArrayList;
import java.util.List;

import artemiygreg.ubanktest.model.Data;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public class DataHelper {

    public static List<Data> buildData(int count) {
        List<Data> list = new ArrayList<>();
        for(int i = 1; i <= count; i++) {
            StringBuilder titleBuilder = new StringBuilder();
            String time = DateUtils.getTime();
            for (int c = 1; c <= i; c++) {
                titleBuilder.append(c);
                if(c < i) {
                    titleBuilder.append("_");
                }
            }
            Data data = new Data(titleBuilder.toString(), time);
            list.add(data);
        }
        return list;
    }
}
