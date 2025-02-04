package jms;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import controlador.ControladorBlaBlaCar;

public class PublicadorValoracion {


	public void enviar(int idViaje,String usuario,String puntuacion, String comentario,boolean isConductor) throws

	NamingException, JMSException {
		InitialContext iniCtx = new InitialContext();
		Object tmp = iniCtx.lookup("jms/TopicConnectionFactory");
		TopicConnectionFactory qcf = (TopicConnectionFactory) tmp;
		TopicConnection conn = qcf.createTopicConnection();
		Topic topic = (Topic) iniCtx.lookup("topic/adApartado");
		TopicSession session = conn.createTopicSession(false,TopicSession.AUTO_ACKNOWLEDGE);
		TextMessage message = session.createTextMessage();
		if (!isConductor){
			message.setText("Participante: "+usuario+" => Puntuacion: "+puntuacion+
					" Comentarios: "+comentario);
		}
		else{
			message.setText("Conductor: "+usuario+" => Puntuacion: "+puntuacion+
					" Comentarios: "+comentario);
		}
		String viaje = Integer.toString(idViaje);
		message.setStringProperty("tipo", viaje);
		TopicPublisher topicPublisher = session.createPublisher(topic);
		topicPublisher.publish(message, DeliveryMode.PERSISTENT,
				Message.DEFAULT_PRIORITY, 7*24*3600*1000L);
		conn.close();
	}
}