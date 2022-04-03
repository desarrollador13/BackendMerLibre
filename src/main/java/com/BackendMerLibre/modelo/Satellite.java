package com.BackendMerLibre.modelo;

import java.util.List;

import com.BackendMerLibre.dto.Coordinates;

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
public class Satellite extends Coordinates{
	
	private double distance;
    private String name;
    private List<String> message;

}
