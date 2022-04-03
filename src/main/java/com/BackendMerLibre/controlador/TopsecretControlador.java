package com.BackendMerLibre.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.BackendMerLibre.constante.Endpoint;
import com.BackendMerLibre.dto.BodyRequestDto;
import com.BackendMerLibre.dto.ResPositionDto;
import com.BackendMerLibre.exceptions.LocationException;
import com.BackendMerLibre.exceptions.MessageException;
import com.BackendMerLibre.servicio.ITopsecretServicio;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Endpoint.TOPSECRET)
public class TopsecretControlador {
	
	@Autowired 
	ITopsecretServicio topsecretServicio;
	
	@PostMapping
	public ResponseEntity<ResPositionDto> ubicacionMsgNave(@RequestBody BodyRequestDto body) {
        try {
        	 ResPositionDto res = topsecretServicio.ubicacionMsgNave(body);
        	 return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (MessageException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }catch (LocationException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
	}
}
