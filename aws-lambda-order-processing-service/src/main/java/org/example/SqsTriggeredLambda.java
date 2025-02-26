package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqsTriggeredLambda implements RequestHandler<SQSEvent, String> {

    private static final Logger logger = LoggerFactory.getLogger(SqsTriggeredLambda.class);

    @Override
    public String handleRequest(SQSEvent event, Context context) {
        logger.info("SQS Event Received with {} messages", event.getRecords().size());

        for (SQSEvent.SQSMessage message : event.getRecords()) {
            logger.info("Processing message: {}", message.getBody());

            // Process the message (business logic)
            processMessage(message.getBody());

            // No need to delete manually, AWS Lambda handles deletion automatically
        }

        return "Processed " + event.getRecords().size() + " messages";
    }

    private void processMessage(String messageBody) {
        // Custom business logic (example: processing orders, notifications, etc.)
        logger.info("Business Logic Executed for Message: {}", messageBody);
    }
}
