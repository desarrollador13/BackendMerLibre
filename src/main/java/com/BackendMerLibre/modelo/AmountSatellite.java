package com.BackendMerLibre.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public class AmountSatellite {
	
	private List<Satellite> satellites;
	private List<Satellite> copiSatellites;
	public String kenobi;
	public String skywalker;
	public String sato;
	private static AmountSatellite instance = null; 
	private String end = null;
	private AmountSatellite() {}
	
	public static AmountSatellite getInstance() {
        if (instance == null) {
            instance = new AmountSatellite();
        }
        return instance;
    }
	
	public void setSatellites(Satellite satellites) {
		if(this.satellites == null) {
			this.satellites = new ArrayList<Satellite>();
		}
		if(satellites.getName().equals("kenobi")) {
			if(kenobi!=null) {
				this.satellites = this.satellites.stream()
					.filter(item -> !item.getName().equals("kenobi")).collect(Collectors.toList());
			}
		}
		if(satellites.getName().equals("skywalker")) {
			if(skywalker!=null) {
				this.satellites = this.satellites.stream()
					.filter(item -> !item.getName().equals("skywalker")).collect(Collectors.toList());
			}
		}
		if(satellites.getName().equals("sato")) {
			if(sato!=null) {
				this.satellites = this.satellites.stream()
					.filter(item -> !item.getName().equals("sato")).collect(Collectors.toList());
			}
		}
        this.satellites.add(satellites);
    }
	
	public void setEnd(String end) {
		this.end = end;
		this.satellites = null;
	}
	
}
