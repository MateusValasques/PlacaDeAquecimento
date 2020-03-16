package placa;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class PlacaAquecimentoComThread implements Runnable{
	static double [][] matriz_original;
	static double [][] matriz_alterada;
	static Object trava = new Object();
	
	static int inicio;
	static int fim;
	static int flag = 0;
	
	
	public PlacaAquecimentoComThread () {
		
	}
	public PlacaAquecimentoComThread(int tam){
		matriz_original = new double [tam][tam];
		matriz_alterada = new double [tam][tam];
		setar_bordas();
		setar_interno();

		for (int i = 0; i < matriz_original.length; i++) {
			for (int j = 0; j < matriz_original.length; j++) {
				matriz_alterada[i][j] = matriz_original[i][j];
			}
		}
		
		Runnable runnable = new PlacaAquecimentoComThread();
		
		LocalTime antes = LocalTime.now();
		
		inicio = 1;
		fim = tam/2;
		Thread thread1 = new Thread(runnable);
		thread1.start();

		inicio = tam/2;
		fim = tam;
		Thread thread2 = new Thread(runnable);
		thread2.start();
		
		LocalTime depois = LocalTime.now();
		System.out.println();
		imprimir();

		long duracao_milli = ChronoUnit.MILLIS.between(antes, depois);
		long duracao_segundos= ChronoUnit.SECONDS.between(antes, depois);
		long duracao_minutos= ChronoUnit.MINUTES.between(antes, depois);
		System.out.print("O processo com thread durou: " + duracao_minutos + " minutos, ");
		System.out.print(duracao_segundos + " segundos e ");
		System.out.println(duracao_milli + " millisegundos");
		
	}

	private static double verifica_menor(double menor, int finale) {
		for (int i = 0; i < finale; i++) {
			for (int j = 0; j < finale; j++) {
				if (matriz_alterada[i][j] < menor) {
					menor = matriz_alterada[i][j];
					
				}
			}
		}
		return menor;
		
	}

	private static void setar_interno() {
		
		Random gerador = new Random(); 
		for (int i = 1; i < matriz_original.length-1; i++) {
			for (int j = 1; j < matriz_original.length-1; j++) {
				matriz_original [i][j] = gerador.nextInt(100) + gerador.nextDouble();
			}
		}
	}

	private static void imprimir() {
		
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

	private static void setar_bordas() {
		
		for (int i = 0; i < matriz_original.length; i++) {
			
			matriz_original [0][i] = 1000;
			matriz_original [i][0] = 1000;
			matriz_original [matriz_original.length-1][i] = 1000;
			matriz_original [i][matriz_original.length-1] = 1000;
		}
	}

	@Override
	public void run() {
		
		int ini = inicio;
		int finale = fim;
		
		double menor = 100;
		
		while (menor < 500) {
			
			while (ini < finale-1) {
				for (int j = 1; j < matriz_original.length-1; j++) {
					
					System.out.println(menor);
					double cima = matriz_original[ini][j-1];
					double baixo = matriz_original[ini][j+1];
					double esquerda = matriz_original[ini-1][j];
					double direita = matriz_original[ini+1][j];
					
					matriz_alterada [ini][j] = (esquerda + direita + baixo + cima  + matriz_original[ini][j])/5;
					
					menor = verifica_menor(menor, finale);
					
					if (menor >= 500) {
				
						break;
					}
				}
				
				inicio++;
				flag++;
				
				if (flag % 2 == 0) {
					for (int i = 1; i < matriz_alterada.length; i++) {
						for (int j = 1; j < matriz_alterada.length; j++) {
							matriz_original[i][j] = matriz_alterada[i][j];
						}
					}
				}
				if (menor >= 500) {
					
					break;
				}
				
			}
		}
		
	}
	
}

