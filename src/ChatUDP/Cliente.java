package ChatUDP;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.*;

public class Cliente extends JFrame {

	public JTextField campoEscribir;
	public JTextArea campoChat;
	private JButton btnExit;
	private JLabel lblQuestion;
	private JScrollPane scrollPane;

	private ThreadEnviar hiloEnviar;
	private ThreadRecibir hiloRecibir;

	public Cliente() {

		setType(Type.POPUP);
		setTitle("Client");
		setLocation(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		campoEscribir = new JTextField();
		campoEscribir.setBounds(0, 26, 400, 26);
		campoEscribir.setEditable(true);
		getContentPane().add(campoEscribir);

		campoEscribir.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					campoEscribir.setText("");
				}
			}

		});

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

	public String enviar(String datos) {
		return datos;
	}

	public static void main(String args[]) throws UnknownHostException, SocketException {

		int puerto = 42069;
		String host = "localhost";

		System.out.println("Usando UDP...");
		System.out.println("Host: " + host + "   Puerto: " + puerto);
		System.out.println("\n");

		InetAddress ia = InetAddress.getByName(host);

		ThreadEnviar enviar = new ThreadEnviar(ia, puerto);
		enviar.start();
		ThreadRecibir recibir = new ThreadRecibir(enviar.getSocket());
		recibir.start();

	}

}