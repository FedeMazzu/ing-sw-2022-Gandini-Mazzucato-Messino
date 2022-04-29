package it.polimi.deib.ingsw.gruppo44.Server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * class to manage the Virtual view data
 * @author filippogandini
 */
public class Data implements Serializable {
    private BoardData boardData; //to trace mother nature position
    private List<SchoolData> schoolDataList;
    private IslandsData islandsData;
    private CloudsData cloudsData;

    public Data() {
        schoolDataList = new ArrayList<>();
    }

    public void addSchoolData(SchoolData schoolData){ schoolDataList.add(schoolData);}

    public void setCloudsData(CloudsData cloudsData) {
        this.cloudsData = cloudsData;
    }

    public void setBoardData(BoardData boardData) {
        this.boardData = boardData;
    }

    public void setIslandsData(IslandsData islandsData) {
        this.islandsData = islandsData;
    }

    public CloudsData getCloudsData() { return cloudsData; }

    public BoardData getBoardData() {
        return boardData;
    }

    public List<SchoolData> getSchoolDataList() {
        return schoolDataList;
    }

    public IslandsData getIslandsData() {
        return islandsData;
    }
}
