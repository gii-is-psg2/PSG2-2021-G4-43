package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.SupportContact;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SupportService {

	public List<SupportContact> getSupportContacts() {
		List<SupportContact> contacts = getInfo("Person", "SELECT Person WHERE email LIKE '%alum.us.es'");
		return contacts;
	}
	
	public List<SupportContact> getInfo( String objetClass, String key )
	{
		final String url = "http://psg2-g4-43.indevspace.xyz/webservices/rest.php";
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("json_data", "{\"operation\":\"core/get\",\"class\":\"" + objetClass + "\",\"key\":\"" + key + "\",\"output_fields\":\"friendlyname,phone, email\"}");
		map.add("auth_user", "admin");
		map.add("auth_pwd", "admin");
		map.add("version", "1.3");
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,
			    map,
			    String.class
			);
		
		List<SupportContact> contacts = new ArrayList<>();
		String friendlyNameSplitter = "friendlyname\":\"";
		String []s = responseEntity.getBody().split(friendlyNameSplitter);
		for(int i = 1;i<s.length;i++) {
			SupportContact sp = new SupportContact();
			String infoSplitter = "\"}},\"Person";
			String info = s[i].split(infoSplitter)[0];
			String nameSplitter = "\",\"phone\":\"";
			
			String firstInfoSplit[] = info.split(nameSplitter);
			String name = firstInfoSplit[0];
			
			String emailSplitter = "\",\"email\":\"";
			String secondInfoSplit[] = firstInfoSplit[1].split(emailSplitter);
			
			String phone = secondInfoSplit[0];
			
			String email = secondInfoSplit[1];
			if(i==s.length-1) {
				String lastSplitter = "\"}}},\"code\"";
				email = email.split(lastSplitter)[0];
			}
			
			sp.setName(name);
			sp.setEmail(email);
			sp.setPhone(phone);
			contacts.add(sp);
		}
		
		return contacts;
	}

}
