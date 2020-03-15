package placa;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class PlacaAquecimentoSemThread {

	public PlacaAquecimentoSemThread(int tam){
		
		double [][] matriz_original = new double [tam][tam];
		double [][] matriz_alterada = new double [tam][tam];
		
		setar_bordas(matriz_original);
		setar_interno(matriz_original);

		for (int i = 0; i < matriz_original.length; i++) {
			for (int j = 0; j < matriz_original.length; j++) {
				matriz_alterada[i][j] = matriz_original[i][j];
			}
		}
		
		LocalTime antes = LocalTime.now();
		
		realizar_operacao_sem_thread(matriz_original, matriz_alterada);
		
		LocalTime depois = LocalTime.now();
		System.out.println();
		imprimir(matriz_alterada);
		imprimir(matriz_original);

		long duracao_milli = ChronoUnit.MILLIS.between(antes, depois);
		long duracao_segundos= ChronoUnit.SECONDS.between(antes, depois);
		long duracao_minutos= ChronoUnit.MINUTES.between(antes, depois);
		System.out.print("O processo sem thread durou: " + duracao_minutos + " minutos, ");
		System.out.print(duracao_segundos + " segundos e ");
		System.out.println(duracao_milli + " millisegundos");
		
	}

	private static void realizar_operacao_sem_thread(double matriz_original [][], double matriz_alterada [][]) {
		//isso aqui da bug
		double menor = 100;
		
		while (menor < 500) {
			for (int i = 1; i < matriz_original.length-1; i++) {
				
				for (int j = 1; j < matriz_original.length-1; j++) {
					
					double cima = matriz_original[i][j-1];
					double baixo = matriz_original[i][j+1];
					double esquerda = matriz_original[i-1][j];
					double direita = matriz_original[i+1][j];
					
					matriz_alterada [i][j] = (esquerda + direita + baixo + cima  + matriz_alterada[i][j])/5;
					
					menor = verifica_menor(matriz_alterada, matriz_alterada[i][j]);
					System.out.println(menor);
					
					if (menor >= 500) {
						
						break;
					}
				}
				if (menor >= 500) {
					
					break;
				}
			}
			for (int i = 1; i < matriz_original.length - 1; i++) {
				  for (int j = 1; j < matriz_original.length - 1; j++) {
				   matriz_original[i][j] = matriz_alterada[i][j];
				   
				  }
			}
		}
	}

	private static double verifica_menor(double matriz_alterada [][], double menor) {
		for (int i = 0; i < matriz_alterada.length; i++) {
			for (int j = 0; j < matriz_alterada.length; j++) {
				if (matriz_alterada[i][j] < menor) {
					menor = matriz_alterada[i][j];
				}
			}
		}
		return menor;
		
	}

	private static void setar_interno(double matriz_original [][]) {
		
		Random gerador = new Random(); 
		for (int i = 1; i < matriz_original.length-1; i++) {
			for (int j = 1; j < matriz_original.length-1; j++) {
				matriz_original [i][j] = gerador.nextInt(100) + gerador.nextDouble();
			}
		}
	}

	private static void imprimir(double matriz_original [][]) {
		
		DecimalFormat milhar = new DecimalFormat("0000.00");
		DecimalFormat centena = new DecimalFormat("000.00");
		DecimalFormat dezena = new DecimalFormat("00.00");
		DecimalFormat unidade = new DecimalFormat("0.00");
		
		for (int i = 0; i < matriz_original.length; i++) {
			for (int j = 0; j < matriz_original.length; j++) {
				if (matriz_original[i][j] >= 1000) {
					System.out.print(milhar.format(matriz_original[i][j]));
					System.out.print("\t\t");
				}
				if (matriz_original[i][j] >= 100 && matriz_original[i][j] < 1000) {
					System.out.print(centena.format(matriz_original[i][j]));
					System.out.print("\t\t");
				}
				if (matriz_original[i][j] >= 10 && matriz_original[i][j] < 100) {
					System.out.print(dezena.format(matriz_original[i][j]));
					System.out.print("\t\t");
				}
				if (matriz_original[i][j] > 1 && matriz_original[i][j] < 10) {
					System.out.print(unidade.format(matriz_original[i][j]));
					System.out.print("\t\t");
				}
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	private static void setar_bordas(double matriz_original[][]) {
		
		for (int i = 0; i < matriz_original.length; i++) {
			
			matriz_original [0][i] = 1000;
			matriz_original [i][0] = 1000;
			matriz_original [matriz_original.length-1][i] = 1000;
			matriz_original [i][matriz_original.length-1] = 1000;
		}
	}
	
}
