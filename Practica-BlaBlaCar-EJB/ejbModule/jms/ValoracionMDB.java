package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: ValoracionMDB
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "adApartado"), 
							 @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic"
									)
		}, 
		mappedName = "topic/adApartado")
public class ValoracionMDB implements MessageListener {

    /**
     * Default constructor. 
     */
    public ValoracionMDB() {
    	System.out.println("TestMDB.TestMDB()");
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	if (message instanceof TextMessage) {
    		TextMessage mensajeTexto = (TextMessage)message;
    		System.out.println("OyenteSuscripcion.onMessage()");
    		try {
    			System.out.println(mensajeTexto.getText());
    		} catch (JMSException e) {
    			e.printStackTrace();
    		}
    	}

    		 }
        
    }



