package com.BackendMerLibre.dto;

import java.io.Serializable;

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
public class ResponseSplitDto implements Serializable{
	
	private static final long serialVersionUID = -3814727417614705758L;
	private String message;
}
