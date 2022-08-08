package br.unirio.kipao.model.dto;

import java.util.Date;

import br.unirio.kipao.model.Address;
import lombok.Data;

@Data
public class DistanceDTO {
	
	private boolean isForCalculateArrivalTime;
	private Date time;
	private String origin;
	private Address address;

}
