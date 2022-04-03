package com.BackendMerLibre.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.BackendMerLibre.constante.Endpoint;
import com.BackendMerLibre.dto.BodyRequestSplitDto;
import com.BackendMerLibre.dto.ResPositionDto;
import com.BackendMerLibre.dto.ResponseSplitDto;
import com.BackendMerLibre.exceptions.LocationException;
import com.BackendMerLibre.exceptions.MessageException;
import com.BackendMerLibre.servicio.ITopsecretSplitServicio;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(Endpoint.TOPSECRETSPLIT)
public class TopsecretSplitControlador {
	
	@Autowired
	ITopsecretSplitServicio topsecretSplitServicio;
	
	@PostMapping("/{satellite_name}")
	public ResponseEntity<ResponseSplitDto> ubicacionMsgNaveSplit(@PathVariable("satellite_name") String satelliteName,
			@RequestBody BodyRequestSplitDto body) throws MessageException, LocationException {
		 try {
			 ResponseSplitDto res = topsecretSplitServicio.ubicacionSplitMsgNave(body,satelliteName);
        	 return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (MessageException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }catch (LocationException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
	}
	
	@GetMapping
	public ResponseEntity<ResPositionDto> getUbicacionMsgNaveSplit() throws MessageException, LocationException {
		try {
			ResPositionDto res = topsecretSplitServicio.getUbicacionMsgNaveSplit();
			return new ResponseEntity<>(res, HttpStatus.OK);
		 } catch (MessageException e){
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
	     }catch (LocationException e){
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
	     }
	}

}
