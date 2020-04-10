package ChatUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;

/*
 * Primero inicializar servidor, despues dos clientes para chatear entre ellos.
 */

public class Servidor {

	private static HashSet<Integer> portSet = new HashSet<Integer>();

	public static void main(String args[]) throws Exception {

		int puerto = 42069;

		System.out.println("Usando UDP.    Puerto: " + puerto);

		DatagramSocket serverSocket = new DatagramSocket(puerto);

		System.out.println("Server inicializado.");
		System.out.println();

		while (true) {
			byte[] datosRecibidos = new byte[25];

			DatagramPacket paqueteRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);

			serverSocket.receive(paqueteRecibido);
			/*
			 * Recibe datos de un cliente( mensaje, ip y puerto) se guarda en un hashset
			 * para despues donde estan todos los usuarios conectados y poder enviar los
			 * mensajes a todos
			 */
			String mensajeCliente = (new String(paqueteRecibido.getData())).trim();

			InetAddress ipCliente = paqueteRecibido.getAddress();

			int puertoCliente = paqueteRecibido.getPort();
			portSet.add(puertoCliente);

			String mensajeRespuesta = mensajeCliente.toUpperCase();
			System.out.println(mensajeRespuesta);

			byte[] enviarMensaje = new byte[25];

			enviarMensaje = mensajeRespuesta.getBytes();

			// Enviar el mensaje a todos los usuarios conectados al chat
			for (Integer port : portSet) {
				System.out.println(port != puertoCliente);
				if (port != puertoCliente) {
					DatagramPacket enviarPaquete = new DatagramPacket(enviarMensaje, enviarMensaje.length, ipCliente,
							port);
					serverSocket.send(enviarPaquete);
				}
			}
		}
	}
}