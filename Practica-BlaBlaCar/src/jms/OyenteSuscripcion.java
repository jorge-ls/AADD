package jms;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import beans.BeanValoracion;

public class OyenteSuscripcion implements MessageListener {
	private BeanValoracion beanValoraciones;
	public OyenteSuscripcion() {
		Map<String, Object> session =
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		beanValoraciones = (BeanValoracion) session.get("beanValoracion");
	}
	@Override
	public void onMessage(Message mensaje) {
		if (mensaje instanceof TextMessage) {
			TextMessage mensajeTexto = (TextMessage)mensaje;
			System.out.println("OyenteSuscripcion.onMessage");
			try {
				beanValoraciones.getMensajesRecibidos().put(mensajeTexto.getText(),
						mensajeTexto.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
