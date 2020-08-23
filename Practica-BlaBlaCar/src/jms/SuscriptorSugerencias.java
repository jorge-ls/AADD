package jms;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SuscriptorSugerencias {
	private static TopicSubscriber topicSubscriber = null;
	private static OyenteSugerencia oyenteSugerencia = null;
	public static void registrarApartado() throws NamingException,
	JMSException {
		if (topicSubscriber == null) {
			InitialContext iniCtx = new InitialContext();
			Object tmp = iniCtx.lookup("jms/TopicConnectionFactory");
			TopicConnectionFactory qcf = (TopicConnectionFactory) tmp;
			TopicConnection conn = qcf.createTopicConnection();
			Topic topic = (Topic) iniCtx.lookup("topic/apartadoSugerencias");
			TopicSession session = conn.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			conn.start();
			topicSubscriber = session.createSubscriber(topic);
			oyenteSugerencia =new OyenteSugerencia();
			topicSubscriber.setMessageListener(oyenteSugerencia);
		}
	}
}