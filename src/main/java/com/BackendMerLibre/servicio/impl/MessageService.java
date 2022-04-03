package com.BackendMerLibre.servicio.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.BackendMerLibre.exceptions.MessageException;

@Service
public class MessageService {
	
	//Eliminar dupicados y concatenar los datos del mensaje en un solo Array
    public List<String> getMsgLine(List<List<String>> msgList){
        List<String> listWords = new ArrayList<String>();
        for( List<String> msg : msgList){
            listWords = Stream.concat(listWords.stream(), msg.stream())
                    .distinct()
                    .collect(Collectors.toList());
        }
        listWords.remove("");
        return listWords;
    }
	
	
	//Servicio recibe un arreglo multidimencional 
	public String getMessage(List<List<String>> msgList) throws MessageException {
        List<String> msgFinal = getMsgLine(msgList);
        if(!isValidMessageSize(msgList, msgFinal.size())) {
            throw new MessageException("Tamaño del mensaje incorrecto");
        }
        //removes(msgList,msgFinal.size());
        String message = messageString(msgList);
        if(!validateMessageOrder(msgFinal,message))
        	throw new MessageException("No se puede conocer el mensaje");
        return message;
    }
	
	
	//Convierte el 	Array de mensajes en String 
	public String messageString(List<List<String>> msgList){
        String convertString = "";
        for(List<String> m : msgList){
            if(m.size()>0 && !m.get(0).equals("")){
            	convertString = (m.size() == 1) ? m.get(0) : m.get(0) + " ";
                msgList.stream().forEach( s -> s.remove(0));
                return  convertString + messageString(msgList);
            }
        }
        return "";
    }

	//Valida si el array la longitud del arreglo
    public boolean isValidMessageSize(List<List<String>> messages, int size){
        for(List<String> m : messages){
            if(m.size() < size){
                return false;
            }
        }
        return true;
    }
    
    public void removes(List<List<String>> msgList, int size){
        int s = 0;
        for(int i = 0; i < msgList.size(); i++){
            s = msgList.get(i).size();
            msgList.set(i, msgList.get(i).subList(s-size, s));
        }
    }
    
    //Se ordena el Array y el String y se compara si son iguales
    public boolean validateMessageOrder(List<String> msgFinal, String message){
        List<String> msg = Arrays.stream(
        		message.split(" ")).collect(Collectors.toList());
        Collections.sort(msgFinal);
        Collections.sort(msg);
        return Arrays.equals(msgFinal.toArray(), msg.toArray());
    }

}
