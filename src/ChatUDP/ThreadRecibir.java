package ChatUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

class ThreadRecibir extends Thread {

	private DatagramSocket udpSocketCliente;

	public ThreadRecibir(DatagramSocket ds) {
		this.udpSocketCliente = ds;
	}

	public void run() {

		byte[] datosRecibidos = new byte[25];

		while (true) {

			try {
				DatagramPacket paqueteRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);
				udpSocketCliente.receive(paqueteRecibido);
				String respuesta = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());

				if (respuesta.length() != 0) {
					System.out.println("User: " + respuesta.toLowerCase());
				}
				Thread.yield();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}