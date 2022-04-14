package it.polimi.deib.ingsw.gruppo44.Server.Model;

import it.polimi.deib.ingsw.gruppo44.Observer;
import it.polimi.deib.ingsw.gruppo44.Server.VirtualView.CloudsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer of the clouds
 * @author filippogandini
 */
public class CloudsObserver {
    private List<Cloud> clouds;
    private CloudsData cloudsData;

    public CloudsObserver(int cloudsNumber){
        clouds = new ArrayList<>();
        cloudsData = new CloudsData(cloudsNumber);
    }

    public void addCloud(Cloud cloud){
        clouds.add(cloud);
    }

    /**
     * updates the clouds in the Virtual View
     * @param cloudId
     */
    public void update(int cloudId){
        for(Color color: Color.values()){
            cloudsData.setStudentsNum(cloudId,color, clouds.get(cloudId).getStudentsNum(color));
        }
    }

    public CloudsData getCloudsData() {
        return cloudsData;
    }
}
