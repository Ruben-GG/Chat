package Chat.Servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreadEnvia implements Runnable {
	private Socket conexion;
	private ObjectOutputStream salida;

	private final ChatServidor chat;
	private String mensaje;

	public ThreadEnvia(Socket conexion, final ChatServidor main) {
		this.conexion = conexion;
		this.chat = main;

		main.campoEscribir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mensaje = event.getActionCommand();
				enviarDatos(mensaje);
				main.campoEscribir.setText("");
			}
		});
	}

	private void enviarDatos(String mensaje) {
		try {
			salida.writeObject("- Servidor: " + mensaje);
			salida.flush();
			chat.mostrarMensaje("- Servidor: " + mensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mostrarMensaje(String mensaje) {
		chat.campoChat.append(mensaje);
	}

	public void run() {
		try {
			salida = new ObjectOutputStream(conexion.getOutputStream());
			salida.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
