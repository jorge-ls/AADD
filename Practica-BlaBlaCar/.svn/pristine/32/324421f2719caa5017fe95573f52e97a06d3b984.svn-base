package jms;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.ViajeJPA;

public class SuscriptorApartado {
	private TopicSubscriber topicSubscriber = null;
	private OyenteSuscripcion oyenteSuscripcion = null;
	public void registrarApartado(List<ViajeJPA> viajes) throws NamingException,
	JMSException {
		if (topicSubscriber == null) {
			InitialContext iniCtx = new InitialContext();
			Object tmp = iniCtx.lookup("jms/TopicConnectionFactory");
			TopicConnectionFactory qcf = (TopicConnectionFactory) tmp;
			TopicConnection conn = qcf.createTopicConnection();
			Topic topic = (Topic) iniCtx.lookup("topic/adApartado");
			TopicSession session = conn.createTopicSession(true,
					TopicSession.AUTO_ACKNOWLEDGE);
			conn.start();
			//String viaje = Integer.toString(idViaje);
			//String tipo = "tipo LIKE '" + viaje + "'";
			String tipo = "";
			for (int i = 0; i < viajes.size(); i++) {
				if (i != 0) tipo += " OR ";
				tipo += "tipo LIKE '" + viajes.get(i).getId() + "'";
			}
			topicSubscriber = session.createSubscriber(topic, tipo, false);
			oyenteSuscripcion = new OyenteSuscripcion();
			topicSubscriber.setMessageListener(oyenteSuscripcion);
		}
	}
}