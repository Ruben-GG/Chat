package Chat.Servidor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;

public class ChatServidor extends JFrame {
	public JTextField campoEscribir;
	public JTextArea campoChat;
	private JButton btnExit;
	private JScrollPane scrollPane;

	public ChatServidor() {

		setType(Type.POPUP);
		setTitle("Server");
		setLocation(800, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		campoEscribir = new JTextField();
		campoEscribir.setBounds(0, 0, 400, 26);
		campoEscribir.setEditable(false);
		getContentPane().add(campoEscribir);

		campoChat = new JTextArea();
		campoChat.setEditable(false);
		scrollPane = new JScrollPane(campoChat);
		scrollPane.setBounds(0, 26, 400, 323);
		getContentPane().add(scrollPane);
		campoChat.setBackground(Color.LIGHT_GRAY);

		btnExit = new JButton("Salir");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(301, 349, 99, 29);
		getContentPane().add(btnExit);

		setSize(400, 400);
		setVisible(true);
	}

	public void mostrarMensaje(String mensaje) {
		campoChat.append(mensaje + "\n");

	}

	public void habilitarTexto(boolean editable) {
		campoEscribir.setEditable(editable);
	}

	public static void main(String[] args) {
		ChatServidor chat = new ChatServidor();
		ExecutorService executor = Executors.newCachedThreadPool();

		try {
			ServerSocket server = new ServerSocket(42069, 50);

			chat.mostrarMensaje("Esperando Cliente...");

			while (true) {
				Socket conexion = server.accept();

				chat.mostrarMensaje("Conectado correctamente a: " + conexion.getInetAddress().getHostName());

				chat.habilitarTexto(true);

				executor.execute(new ThreadRecibe(conexion, chat));
				executor.execute(new ThreadEnvia(conexion, chat));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		executor.shutdown();
	}
}
