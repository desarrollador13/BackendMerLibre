package com.BackendMerLibre.servicio;

import com.BackendMerLibre.dto.BodyRequestSplitDto;
import com.BackendMerLibre.dto.ResPositionDto;
import com.BackendMerLibre.dto.ResponseSplitDto;
import com.BackendMerLibre.exceptions.LocationException;
import com.BackendMerLibre.exceptions.MessageException;

public interface ITopsecretSplitServicio {
	
	ResponseSplitDto ubicacionSplitMsgNave(BodyRequestSplitDto body, String satelliteName)
			throws MessageException, LocationException;
	
	ResPositionDto getUbicacionMsgNaveSplit() throws MessageException, LocationException;
}
