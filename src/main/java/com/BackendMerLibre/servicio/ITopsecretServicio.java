package com.BackendMerLibre.servicio;

import com.BackendMerLibre.dto.BodyRequestDto;
import com.BackendMerLibre.dto.ResPositionDto;
import com.BackendMerLibre.exceptions.LocationException;
import com.BackendMerLibre.exceptions.MessageException;

public interface ITopsecretServicio {
	ResPositionDto ubicacionMsgNave(BodyRequestDto body)throws MessageException, LocationException;
}
