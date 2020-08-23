package jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import servlet.VerSugerencias;

public class OyenteSugerencia implements MessageListener {
	public OyenteSugerencia() {
	}
	@Override
	public void onMessage(Message mensaje) {
		if (mensaje instanceof TextMessage) {
			TextMessage mensajeTexto = (TextMessage)mensaje;
			System.out.println("OyenteSugerencia.onMessage()");
			try {
				VerSugerencias.anyadir(mensajeTexto.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
