package org.example.consumer;
import java.io.Console;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.jms.JmsConnectionFactory;

public class Consumer {
    public static void main(String[] args) throws Exception {
        // Create a connection to ActiveMQ JMS broker using AMQP protocol
        JmsConnectionFactory factory = new JmsConnectionFactory("amqp://localhost:5672");
        Connection connection = factory.createConnection("admin", "password");
        connection.start();

        // Create a session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create a queue
        Destination destination = session.createQueue("MyFirstQueue");

        // Create a consumer specific to queue
        MessageConsumer consumer = session.createConsumer(destination);

        Console c = System.console();
        String response;
        do {
            // Receive the message
            Message msg = consumer.receive();
            response = ((TextMessage) msg).getText();

            System.out.println("Received = "+response);

        } while (!response.equalsIgnoreCase("Quit"));

        // Close the connection
        connection.close();
    }
}
