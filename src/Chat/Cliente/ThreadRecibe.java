package Chat.Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ThreadRecibe implements Runnable {
	private final ChatCliente chat;
	private String mensaje;
	private ObjectInputStream entrada;
	private Socket cliente;

	public ThreadRecibe(Socket cliente, ChatCliente main) {
		this.cliente = cliente;
		this.chat = main;
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
