package Chat.Cliente;

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatCliente extends JFrame {
	public JTextField campoEscribir;
	public JTextArea campoChat;
	private JButton btnExit;
	private JLabel lblQuestion;
	private JScrollPane scrollPane;

	public ChatCliente() {

		setType(Type.POPUP);
		setTitle("Client");
		setLocation(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		campoEscribir = new JTextField();
		campoEscribir.setBounds(0, 26, 400, 26);
		campoEscribir.setEditable(false);
		getContentPane().add(campoEscribir);

		lblQuestion = new JLabel("¿Qué necesita?");
		lblQuestion.setBounds(6, 6, 91, 16);
		getContentPane().add(lblQuestion);

		campoChat = new JTextArea();
		campoChat.setEditable(false);
		scrollPane = new JScrollPane(campoChat);
		scrollPane.setBounds(0, 53, 400, 296);
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
		ChatCliente chat = new ChatCliente();
		ExecutorService executor = Executors.newCachedThreadPool();

		chat.mostrarMensaje("Buscando Servidor...");

		try {
			Socket cliente = new Socket(InetAddress.getByName("localhost"), 42069);
			chat.mostrarMensaje("Conectado correctamente a: " + cliente.getInetAddress().getHostName());

			chat.habilitarTexto(true);
			executor.execute(new ThreadRecibe(cliente, chat));
			executor.execute(new ThreadEnvia(cliente, chat));
		} catch (IOException e) {
			e.printStackTrace();
		}

		executor.shutdown();
	}
}
