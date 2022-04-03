package com.BackendMerLibre.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class SatellitesDto extends Coordinates implements Serializable{
	
	private static final long serialVersionUID = -3814727417614705758L;
	
	@JsonProperty("name")
	private String name;
	@JsonProperty("distance")
	private double distance;
	@JsonProperty("message")
	private List<String> message;

}
