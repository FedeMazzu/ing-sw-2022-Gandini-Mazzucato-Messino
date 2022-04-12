package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import java.util.ArrayList;
import java.util.List;

/**
 * class to manage the Virtual view data
 */
public class Data {
    List<SchoolData> schoolDataList;

    public Data() {
        schoolDataList = new ArrayList<>();
    }

    public void addSchoolData(SchoolData schoolData){ schoolDataList.add(schoolData);}
}
