package Chat.Servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ThreadRecibe implements Runnable {
	private ObjectInputStream entrada;
	private Socket cliente;

	private final ChatServidor chat;
	private String mensaje;

	public ThreadRecibe(Socket cliente, ChatServidor chat) {
		this.cliente = cliente;
		this.chat = chat;
	}

	public void mostrarMensaje(String mensaje) {
		chat.campoChat.append(mensaje);
	}

	public void run() {
		try {
			entrada = new ObjectInputStream(cliente.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				mensaje = (String) entrada.readObject();
				chat.mostrarMensaje(mensaje);
			} catch (ClassNotFoundException | IOException e) {
			}
		}
	}
}
