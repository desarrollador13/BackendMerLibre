package com.BackendMerLibre.servicio.impl;

import com.BackendMerLibre.dto.BodyRequestDto;
import com.BackendMerLibre.dto.ResPositionDto;
import com.BackendMerLibre.exceptions.LocationException;
import com.BackendMerLibre.exceptions.MessageException;
import com.BackendMerLibre.modelo.Position;
import com.BackendMerLibre.servicio.ITopsecretServicio;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TopsecretServicioImpl implements ITopsecretServicio{
	
	@Autowired
    MessageService messageService;
	@Autowired
    private Environment environment;
	
	public ResPositionDto ubicacionMsgNave(BodyRequestDto body) throws MessageException, LocationException{
		
		if(body.getMessages().size() < 2) {
			throw new MessageException("Numero de mensajes insuficientes");
		}
		setValuePositions(body);
		if((body.getPositions().length < 2) || (body.getDistances().length < 2)) {
            throw new LocationException("Numero posicion o distancias insuficientes");
		}
		
		String message = messageService.getMessage(body.getMessages());
		double [] points = getLocation(body.getPositions(), body.getDistances());
		Position position = new Position(points);
		ResPositionDto res = ResPositionDto.builder().position(position).message(message).build();
        return  res;
	}
	
	public void setValuePositions(BodyRequestDto body) {
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
	
	public double[] getLocation(double[][] positions, double [] distances) throws MessageException {
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
