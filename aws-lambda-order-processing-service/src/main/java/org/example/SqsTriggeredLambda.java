package org.example;

//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.RequestHandler;
//import com.amazonaws.services.lambda.runtime.events.SQSEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//
//public class SqsTriggeredLambda implements RequestHandler<SQSEvent, String> {
//
//    private static final Logger logger = LoggerFactory.getLogger(SqsTriggeredLambda.class);
//    private static final String DELIVERY_SERVICE_URL = "https://8sj9qt306f.execute-api.ap-south-1.amazonaws.com/dev/delivery/processorder";
//
//    @Override
//    public String handleRequest(SQSEvent event, Context context) {
//        logger.info("SQS Event Received with {} messages", event.getRecords().size());
//
//        for (SQSEvent.SQSMessage message : event.getRecords()) {
//            logger.info("Processing message: {}", message.getBody());
//
//            // Process the message (business logic)
//            processMessage(message.getBody());
//
//            // No need to delete manually, AWS Lambda handles deletion automatically
//        }
//
//        return "Processed " + event.getRecords().size() + " messages";
//    }
//
//    private void processMessage(String messageBody) {
//        // Custom business logic (example: processing orders, notifications, etc.)
//        boolean isSent = sendOrderToDeliveryService(messageBody);
//
//        if (isSent) {
//            logger.info("Order successfully sent to Delivery Service: {}", messageBody);
//        } else {
//            logger.error("Failed to send order to Delivery Service: {}", messageBody);
//        }
//    }
//
//
//    private boolean sendOrderToDeliveryService(String orderJson) {
//        try {
//            URL url = new URL(DELIVERY_SERVICE_URL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setDoOutput(true);
//
//            // Send request
//            try (OutputStream os = conn.getOutputStream()) {
//                byte[] input = orderJson.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            // Check response code
//            int responseCode = conn.getResponseCode();
//            logger.info("Response Code from Delivery Service: {}", responseCode);
//
//            return responseCode == 200 || responseCode == 201;
//        } catch (Exception e) {
//            logger.error("Error sending order to Delivery Service", e);
//            return false;
//        }
//    }
//}


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SqsTriggeredLambda implements RequestHandler<SQSEvent, String> {

    private static final Logger logger = LoggerFactory.getLogger(SqsTriggeredLambda.class);
    private static final String DELIVERY_SERVICE_URL = "https://8sj9qt306f.execute-api.ap-south-1.amazonaws.com/dev/delivery/processorder";

    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate instance

    @Override
    public String handleRequest(SQSEvent event, Context context) {
        logger.info("SQS Event Received with {} messages", event.getRecords().size());

        for (SQSEvent.SQSMessage message : event.getRecords()) {
            logger.info("Processing message: {}", message.getBody());

            // Send order details to Delivery Service API
            boolean isSent = sendOrderToDeliveryService(message.getBody());

            if (isSent) {
                logger.info("Order successfully sent to Delivery Service: {}", message.getBody());
            } else {
                logger.error("Failed to send order to Delivery Service: {}", message.getBody());
            }
        }

        return "Processed " + event.getRecords().size() + " messages";
    }

    private boolean sendOrderToDeliveryService(String orderJson) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> requestEntity = new HttpEntity<>(orderJson, headers);

            // Send POST request
            ResponseEntity<String> response = restTemplate.postForEntity(DELIVERY_SERVICE_URL, requestEntity, String.class);

            logger.info("Response from Delivery Service: Status Code: {}, Body: {}", response.getStatusCodeValue(), response.getBody());

            return response.getStatusCode().is2xxSuccessful(); // Returns true if response is 200-299
        } catch (Exception e) {
            logger.error("Error sending order to Delivery Service", e);
            return false;
        }
    }
}

