package com.BackendMerLibre.servicio.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.BackendMerLibre.dto.BodyRequestSplitDto;
import com.BackendMerLibre.dto.BodySplitDto;
import com.BackendMerLibre.dto.ResPositionDto;
import com.BackendMerLibre.dto.ResponseSplitDto;
import com.BackendMerLibre.dto.SatellitesDto;
import com.BackendMerLibre.exceptions.LocationException;
import com.BackendMerLibre.exceptions.MessageException;
import com.BackendMerLibre.modelo.AmountSatellite;
import com.BackendMerLibre.modelo.Position;
import com.BackendMerLibre.modelo.Satellite;
import com.BackendMerLibre.servicio.ITopsecretSplitServicio;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

@Service
public class TopsecretSplitServicioImpl implements ITopsecretSplitServicio{
	
	@Autowired
    MessageService messageService;
	@Autowired
    private Environment environment;
	
	public ResponseSplitDto ubicacionSplitMsgNave(BodyRequestSplitDto body, String satelliteName)
			throws MessageException, LocationException {
		if(satelliteName.equals("kenobi")) {
			Satellite kenobi = Satellite.builder().distance(body.getDistance())
					.name(satelliteName).message(body.getMessage()).build();
			AmountSatellite.getInstance().setSatellites(kenobi);
			AmountSatellite.getInstance().setKenobi("kenobi");
		}
		if(satelliteName.equals("skywalker")) {
			Satellite skywalker = Satellite.builder().distance(body.getDistance())
					.name(satelliteName).message(body.getMessage()).build();
			AmountSatellite.getInstance().setSatellites(skywalker);
			AmountSatellite.getInstance().setSkywalker("skywalker");
		}
		if(satelliteName.equals("sato")) {
			Satellite sato = Satellite.builder().distance(body.getDistance())
					.name(satelliteName).message(body.getMessage()).build();
			AmountSatellite.getInstance().setSatellites(sato);
			AmountSatellite.getInstance().setSato("sato");
		}
		return ResponseSplitDto.builder().message("created succesfully").build();
	}
	
	public ResPositionDto getUbicacionMsgNaveSplit()throws MessageException, LocationException {
		SatellitesDto satellitesDto = null;
		if(AmountSatellite.getInstance().getSatellites()==null) {
			throw new MessageException("No hay suficiente información se tienen que cargar los 3 satélites");
		}
		if(AmountSatellite.getInstance().getSatellites().size() < 3 ) {
			throw new MessageException("No hay suficiente información se tienen que cargar los 3 satélites");
		}
		
		List<SatellitesDto> satellite = new ArrayList<>();
		for(int i = 0; i < AmountSatellite.getInstance().getSatellites().size(); i++){
			satellitesDto = SatellitesDto.builder()
					.distance(AmountSatellite.getInstance().getSatellites().get(i).getDistance())
					.message(AmountSatellite.getInstance().getSatellites().get(i).getMessage()).build();
			satellite.add(satellitesDto);
		}
		BodySplitDto bodySplit = BodySplitDto.builder().satellites(satellite).build();
		
		setValuePositions(bodySplit);
		
		String message = messageService.getMessage(bodySplit.getMessages());
		double [] points = getLocation(bodySplit.getPositions(), bodySplit.getDistances());
		
		Position positions = new Position(points);
		ResPositionDto res = ResPositionDto.builder().position(positions).message(message).build();
		AmountSatellite.getInstance().setEnd("Final");
        return  res;
	}
	
	public void setValuePositions(BodySplitDto body) {
		System.out.println(body.getPositions()[0]);
		if(body.getPositions()[0] == null) {
			int amout = Integer.parseInt(environment.getProperty("satellites.amount"));
			double[][] pointsList = new double[amout][];
			String[] satelliteString;
			for (int i = 0; i < body.getSatellites().size(); i++) {
				satelliteString = environment.getProperty("satellites." + i + ".position").split(",");
				pointsList[i] = Arrays.stream(satelliteString)
						.map(Double::valueOf)
						.mapToDouble(Double::doubleValue)
						.toArray();
			}
			body.setPositions(pointsList);
		}	
	}
	
	public double[] getLocation(double[][] positions, double [] distances)throws MessageException {
		for(int i = 0; i < distances.length; i++) {
			if(distances[i] == 0.0) {
				throw new MessageException("El campo  distancia esta vacío.");
			}
		}
        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, distances);
        NonLinearLeastSquaresSolver nSolver = new NonLinearLeastSquaresSolver(
        		trilaterationFunction, new LevenbergMarquardtOptimizer());
        return  nSolver.solve().getPoint().toArray();
    }
}
