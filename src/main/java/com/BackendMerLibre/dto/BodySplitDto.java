package com.BackendMerLibre.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.BackendMerLibre.modelo.Position;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BodySplitDto implements Serializable{
	
	private static final long serialVersionUID = -3814727417614705758L;
	@JsonProperty("satellites")
	private List<SatellitesDto> satellites;
	
	public List<List<String>> getMessages(){
        List<List<String>> messages = new ArrayList<List<String>>();
        for(SatellitesDto s : satellites){
            messages.add(s.getMessage());
        }
        return  messages;
    }
    
    public double[] getDistances(){
        double [] distances = new double[satellites.size()];
        for(int i = 0; i < satellites.size(); i ++){
            distances[i] = satellites.get(i).getDistance();
        }
        return  distances;
    }
    
    public double[][] getPositions(){
        double [][] positions = new double[satellites.size()][];
        String[] points;
        for(int i = 0; i < satellites.size(); i ++){
            if(satellites.get(i).getPosition() != null) {
                points = satellites.get(i).getPosition().toString().split(",");
                positions[i] = Arrays.stream(points)
                        .map(Double::valueOf)
                        .mapToDouble(Double::doubleValue)
                        .toArray();
            }
        }
        return positions;
    }
    
    public void setPositions(double[][] pointsList){
        Position position;
        for(int i = 0; i < pointsList.length; i++){
            position = new Position(pointsList[i]);
            satellites.get(i).setPosition(position);
        }
    }    

}
