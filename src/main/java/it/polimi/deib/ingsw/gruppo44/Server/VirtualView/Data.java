package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import java.util.ArrayList;
import java.util.List;

/**
 * class to manage the Virtual view data
 */
public class Data {
    private List<SchoolData> schoolDataList;
    private IslandsData islandsData;
    public Data() {
        schoolDataList = new ArrayList<>();
    }

    public void addSchoolData(SchoolData schoolData){ schoolDataList.add(schoolData);}

    public void setIslandsData(IslandsData islandsData) {
        this.islandsData = islandsData;
    }
}
