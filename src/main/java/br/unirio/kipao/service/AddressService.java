package br.unirio.kipao.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import br.unirio.kipao.model.Address;
import br.unirio.kipao.model.dto.DistanceDTO;
import br.unirio.kipao.repository.AddressRepository;

@Service
public class AddressService {

	@Value("093ac7c916a3ea7b683d3de237e825e01e5ddc8a")
	private String API_KEY;

	@Autowired
	private AddressRepository addressRepository;

	public DistanceMatrix getDistance(DistanceDTO distance)
			throws ApiException, InterruptedException, IOException {

		GeoApiContext context = new GeoApiContext.Builder().apiKey(API_KEY).build();
		
		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
		if (distance.isForCalculateArrivalTime()) {
			req.departureTime(distance.getTime().toInstant());
		} else {
			req.departureTime(distance.getTime().toInstant());
		}

		DistanceMatrix trix = req.origins(distance.getOrigin()).destinations(distance.getAddress().toString())
				.mode(TravelMode.BICYCLING).language("pt-BR").await();
		return trix;

	}
	
	public Address saveAddress(Address address) {
		
		return addressRepository.save(address);
	}

}
