package ChatUDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class ThreadEnviar extends Thread {

	private InetAddress ipServer;
	private DatagramSocket udpSocket;
	private int serverport;

	public ThreadEnviar(InetAddress ip, int puerto) throws SocketException {
		this.ipServer = ip;
		this.serverport = puerto;
		this.udpSocket = new DatagramSocket();
		this.udpSocket.connect(ipServer, puerto);
	}

	public DatagramSocket getSocket() {
		return this.udpSocket;
	}

	public void run() {
		byte[] datos = new byte[25];

		try {

			// Paquete vacio para añadir el puerto del cliente al hashset
			datos = "".getBytes();
			DatagramPacket paqueteVacio = new DatagramPacket(datos, datos.length, ipServer, serverport);
			udpSocket.send(paqueteVacio);

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while (true) {

				byte[] datosEnviar = new byte[25];

				String mensajeCliente = in.readLine();

				datosEnviar = mensajeCliente.getBytes();

				DatagramPacket enviarPaquete = new DatagramPacket(datosEnviar, datosEnviar.length, ipServer,
						serverport);
				System.out.println("Me: " + mensajeCliente);
				udpSocket.send(enviarPaquete);

				Thread.yield();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
