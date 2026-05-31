package com.arunnitd.middleware.mw;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class Msg91Service {
    private final String MSG91_URL = "https://api.msg91.com/api/v5/whatsapp/whatsapp-outbound-message/bulk/";
    // private final String MSG91_URL =
    // "https://api.msg91.com/api/v5/whatsapp/whatsapp-outbound-message/bulk";
    private final String AUTH_KEY = "479629At46a4wnN5iE6a1018b6P1";
    private final RestTemplate restTemplate = new RestTemplate();

    public String processAndSendMessage(CallRequest callRequest) {

        // The fix:
        String bannerUrl = "rider50".equals(callRequest.getTemplateName())
                ? "https://files.msg91.com/479629/ymddnsjn"
                : "https://files.msg91.com/479629/xmyclfjz";

        // 2. Build the nested "2D" JSON structure exactly as required
        Map<String, Object> payload = new HashMap<>();
        payload.put("integrated_number", "918860360361");
        payload.put("content_type", "template");

        Map<String, Object> innerPayload = new HashMap<>();
        innerPayload.put("messaging_product", "whatsapp");
        innerPayload.put("type", "template");

        Map<String, Object> template = new HashMap<>();
        template.put("name", callRequest.getTemplateName());

        Map<String, String> language = new HashMap<>();
        language.put("code", "en");
        language.put("policy", "deterministic");
        template.put("language", language);

        template.put("namespace", "da19982e_458e_4aa1_b29f_a91d0ddce77c");

        // Build to_and_components array
        Map<String, Object> toAndComponentsItem = new HashMap<>();
        toAndComponentsItem.put("to", Collections.singletonList(callRequest.getPhoneNumber()));

        Map<String, Object> components = new HashMap<>();
        Map<String, String> header1 = new HashMap<>();
        header1.put("type", "image");
        header1.put("value", bannerUrl);
        components.put("header_1", header1);

        toAndComponentsItem.put("components", components);
        template.put("to_and_components", Collections.singletonList(toAndComponentsItem));

        innerPayload.put("template", template);
        payload.put("payload", innerPayload);

        // 3. Set up HTTP Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authkey", AUTH_KEY);

        // 4. Make the external API Call
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(MSG91_URL, requestEntity, String.class);
            return "Success: " + response.getBody();
        } catch (Exception e) {
            return "Failed to send message: " + e.getMessage();
        }
    }
}
