package placa;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {

		int tam = Integer.parseInt(JOptionPane.showInputDialog(null, "Qual o tamanho da matriz?: "));
		
		PlacaAquecimentoSemThread placa_semthread = new PlacaAquecimentoSemThread(tam);
		PlacaAquecimentoComThread placa_comthread = new PlacaAquecimentoComThread(tam);
	}
}
