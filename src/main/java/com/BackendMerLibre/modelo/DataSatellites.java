package com.BackendMerLibre.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class DataSatellites implements Serializable{
	
	private static final long serialVersionUID = -3814727417614705758L;
	private List<Satellite> satellites;
	
	public void setSatellites(Satellite satellites) {
		if(this.satellites == null) {
			this.satellites = new ArrayList<Satellite>();
		}
        this.satellites.add(satellites);
    }
}
