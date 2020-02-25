import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import javax.swing.JOptionPane;

public class PlacaAquecimento {

	public static void main(String[] args) {
		
		int tam = Integer.parseInt(JOptionPane.showInputDialog(null, "Qual o tamanho da matriz?: "));
		
		double [][] matriz = new double [tam][tam];
		double [][] copia_matriz = new double [tam][tam];
		setar_bordas(matriz);
		setar_interno(matriz);

		//pra garantir que as funções tenham a mesma matriz
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				copia_matriz[i][j] = matriz[i][j];
			}
		}
		
		sem_thread(matriz);
		com_thread(copia_matriz);
	}


	private static void com_thread(double[][] copia_matriz) {
		
		LocalTime antes = LocalTime.now();
		//criar um método diferente para servir para a thread
		realizar_operacao(copia_matriz);
		
		LocalTime depois = LocalTime.now();
		imprimir(copia_matriz);

		long duracao = ChronoUnit.MILLIS.between(antes, depois);
		
		System.out.println("O processo sem thread durou: " + duracao);
	}


	private static void sem_thread(double[][] matriz) {
		
		LocalTime antes = LocalTime.now();
		
		realizar_operacao(matriz);
		
		LocalTime depois = LocalTime.now();
		imprimir(matriz);

		long duracao = ChronoUnit.MILLIS.between(antes, depois);
		
		System.out.println("O processo sem thread durou: " + duracao);
	}

	private static void realizar_operacao(double[][] matriz) {
		
		for (int i = 1; i < matriz.length-1; i++) {
			for (int j = 1; j < matriz.length-1; j++) {
				
				double diagonal_superior_esquerda = matriz[i-1][j-1];
				double esquerda = matriz[i][j-1];
				double diagonal_inferior_esquerda = matriz[i+1][j-1];
				double baixo = matriz[i+1][j];
				double diagonal_inferior_direita = matriz[i+1][j+1];
				double direita = matriz[i][j+1];
				double diagonal_superior_direita = matriz[i-1][j+1];
				double cima = matriz[i-1][j];
				
				//matriz[i][j] = não lembro o que eu faço com os vizinhos para colocar aqui
				
			}
		}
		
	}

	private static void setar_interno(double[][] matriz) {
		
		Random gerador = new Random(); 
		for (int i = 1; i < matriz.length-1; i++) {
			for (int j = 1; j < matriz.length-1; j++) {
				matriz [i][j] = gerador.nextInt(30) + gerador.nextDouble();
			}
		}
	}

	private static void imprimir(double[][] matriz) {
		
		DecimalFormat milhar = new DecimalFormat("0000.00");
		DecimalFormat centena = new DecimalFormat("000.00");
		DecimalFormat dezena = new DecimalFormat("00.00");
		DecimalFormat unidade = new DecimalFormat("0.00");
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (matriz[i][j] >= 1000) {
					System.out.print(milhar.format(matriz[i][j]));
					System.out.print("\t\t");
				}
				if (matriz[i][j] >= 100 && matriz[i][j] < 1000) {
					System.out.print(centena.format(matriz[i][j]));
					System.out.print("\t\t");
				}
				if (matriz[i][j] >= 10 && matriz[i][j] < 100) {
					System.out.print(dezena.format(matriz[i][j]));
					System.out.print("\t\t");
				}
				if (matriz[i][j] > 1 && matriz[i][j] < 10) {
					System.out.print(unidade.format(matriz[i][j]));
					System.out.print("\t\t");
				}
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	private static void setar_bordas(double matriz[][]) {
		
		for (int i = 0; i < matriz.length; i++) {
			
			matriz [0][i] = 1000;
			matriz [i][0] = 1000;
			matriz [matriz.length-1][i] = 1000;
			matriz [i][matriz.length-1] = 1000;
		}
	}
	
}
