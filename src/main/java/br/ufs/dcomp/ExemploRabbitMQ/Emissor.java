package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Emissor {

  private final static String QUEUE_NAME1 = "f1";
  private final static String QUEUE_NAME2 = "f2";
  private final static String QUEUE_NAME3 = "f3";
  private final static String EXCHANGE_NAME = "ea";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("54.91.224.101"); // Alterar
    factory.setUsername("admin"); // Alterar
    factory.setPassword("password"); // Alterar
    factory.setVirtualHost("/");    
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

                      //(queue-name, durable, exclusive, auto-delete, params); 
    channel.queueDeclare(QUEUE_NAME1, false,   false,     false,       null);
    channel.queueDeclare(QUEUE_NAME2, false,   false,     false,       null);
    channel.queueDeclare(QUEUE_NAME3, false,   false,     false,       null);
    
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    
    channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME, "");
    channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "");
    channel.queueBind(QUEUE_NAME3, EXCHANGE_NAME, "");

    String message = "Boa tarde. a aula vai até que horas?";
    
                    //  (exchange,   routingKey, props, message-body             ); 
    channel.basicPublish(EXCHANGE_NAME,       "", null,  message.getBytes("UTF-8"));
    System.out.println(" [x] Mensagem enviada: '" + message + "'");

    channel.close();
    connection.close();
  }
}