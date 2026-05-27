package com.arunnitd.middleware.mw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/middleware")
public class MiddlewareController {

    @Autowired
    private Msg91Service msg91Service;

    @PostMapping("/trigger-message")
    public ResponseEntity<String> triggerMessage(@RequestBody CallRequest callRequest) {
        // Validate userTap
        if (callRequest.getUserTap() != 1 && callRequest.getUserTap() != 2) {
            return ResponseEntity.badRequest().body("Invalid input: tap must be 1 or 2");
        }

        // Validate templateName
        if (callRequest.getTemplateName() == null || callRequest.getTemplateName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input: templateName cannot be empty");
        }

        // Pass to service for transformation and sending
        String result = msg91Service.processAndSendMessage(callRequest);
        return ResponseEntity.ok(result);
    }
}