package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.h2.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.SupportContact;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SupportService {

	public List<SupportContact> getSupportContacts() {
		List<SupportContact> listaContactos = new ArrayList<>();
		return adoptionRepository.findAll();
	}
	
	public JSONObject getObjects( String objectClass, String key )
	{
		Map<String,String> map = new HashMap<>();
		map.put("json_data", "{\"operation\":\"core/get\",\"class\":\"" + objectClass + "\",\"key\":\"" + key + "\"}");
		String jsonResponse = ((String)new RestTemplate().postForObject(url, map, String.class, new Object[0]));

		JSONObject objects = new JSONObject(this.jsonResponse);
		JSONObject itopObjects = objects.getJSONObject("objects");

		return itopObjects;
	}

}
