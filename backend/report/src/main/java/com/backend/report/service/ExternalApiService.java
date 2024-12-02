package com.backend.report.service;

import com.backend.report.model.entity.AddressDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ExternalApiService {

    @Autowired
    private RestTemplate restTemplate;

    public AddressDetails fetchAddressDetails(BigDecimal latitude, BigDecimal longitude) {
        String apiUrl = String.format("https://api-adresse.data.gouv.fr/reverse/?lat=%s&lon=%s", latitude, longitude);

        ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map features = (Map) ((List) response.getBody().get("features")).get(0);
            Map properties = (Map) features.get("properties");

            AddressDetails details = new AddressDetails();
            details.setAddress((String) properties.get("label"));
            details.setCityName((String) properties.get("city"));
            details.setPostcode((String) properties.get("postcode"));
            details.setInseeCode((String) properties.get("citycode"));

            return details;
        }

        return null;
    }
}
