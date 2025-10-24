package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Emissor {

  private final static String QUEUE_NAME1 = "tarcisio";
  private final static String QUEUE_NAME2 = "alberto";
  private final static String QUEUE_NAME3 = "marta";
  private final static String QUEUE_NAME4 = "pedro";
  private final static String QUEUE_NAME5 = "kalil";
  
  private final static String EXCHANGE_NAME = "dcomp";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("98.93.223.159"); // Alterar
    factory.setUsername("admin"); // Alterar
    factory.setPassword("password"); // Alterar
    factory.setVirtualHost("/");    
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

                      //(queue-name, durable, exclusive, auto-delete, params); 
    channel.queueDeclare(QUEUE_NAME1, false,   false,     false,       null);
    channel.queueDeclare(QUEUE_NAME2, false,   false,     false,       null);
    channel.queueDeclare(QUEUE_NAME3, false,   false,     false,       null);
    channel.queueDeclare(QUEUE_NAME4, false,   false,     false,       null);
    channel.queueDeclare(QUEUE_NAME5, false,   false,     false,       null);
    
    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
    
    //               (  queue,      exchange,     routingKey);
    channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "cc");
    channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "ec");
    channel.queueBind(QUEUE_NAME3, EXCHANGE_NAME, "cc");
    channel.queueBind(QUEUE_NAME4, EXCHANGE_NAME, "ec");
    channel.queueBind(QUEUE_NAME5, EXCHANGE_NAME, "si");

    String messageCC = "Boa tarde. reunião ordinária dia 29/10/2025 às 14h.";
    
                    //  (exchange, routingKey, props, message-body         ); 
    channel.basicPublish(EXCHANGE_NAME, "cc", null, messageCC.getBytes("UTF-8"));
    System.out.println(" [x] Mensagem enviada: '" + messageCC + "'");
    
    String messageEC = "Boa tarde. reunião ordinária dia 30/10/2025 às 14h.";
    
                    //  (exchange, routingKey, props, message-body         ); 
    channel.basicPublish(EXCHANGE_NAME, "ec", null, messageEC.getBytes("UTF-8"));
    System.out.println(" [x] Mensagem enviada: '" + messageEC + "'");
    
    String messageSI = "Boa tarde. reunião ordinária dia 31/10/2025 às 14h.";
    
                    //  (exchange, routingKey, props, message-body         ); 
    channel.basicPublish(EXCHANGE_NAME, "si", null, messageSI.getBytes("UTF-8"));
    System.out.println(" [x] Mensagem enviada: '" + messageSI + "'");

    channel.close();
    connection.close();
  }
}