package org.example.publisher;
import java.io.Console;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.jms.JmsConnectionFactory;

public class Publisher {
    public static void main(String[] args) throws Exception {
        // Create a connection to ActiveMQ JMS broker using AMQP protocol
        JmsConnectionFactory factory = new JmsConnectionFactory("amqp://localhost:5672");
        Connection connection = factory.createConnection("admin", "password");
        connection.start();

        // Create a session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create a topic
        Destination destination = session.createTopic("MyFirstTopic");

        // Create a publisher specific to topic
        MessageProducer publisher = session.createProducer(destination);

        Scanner input = new Scanner(System.in);
        String response;
        do {
            System.out.println("Enter message: ");
            response = input.nextLine();
            // Create a message object
            TextMessage msg = session.createTextMessage(response);

            // Send the message to the topic
            publisher.send(msg);

        } while (!response.equalsIgnoreCase("Quit"));
        input.close();

        // Close the connection
        connection.close();
    }
}
