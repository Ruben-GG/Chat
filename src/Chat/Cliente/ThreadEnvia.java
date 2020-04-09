package Chat.Cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreadEnvia implements Runnable {
	private final ChatCliente main;
	private ObjectOutputStream salida;
	private String mensaje;
	private Socket conexion;

	public ThreadEnvia(Socket conexion, final ChatCliente main) {
		this.conexion = conexion;
		this.main = main;

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
			salida.writeObject("- Cliente: " + mensaje);
			salida.flush();
			main.mostrarMensaje("- Cliente: " + mensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mostrarMensaje(String mensaje) {
		main.campoChat.append(mensaje);
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
