package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int quantum = 20;//numero maximo que um processo pode consumir sem voltar para a fila
		final int tamMaxMemoriaPrincipal = 20;
		final int tamMaxMemoriaVirtual = 200;
		final int tamMaxMemoriaSecundaria = 1000;
		final int numMinCiclos = 100;
		List<Integer> memoriaPrincipal = new ArrayList<>();
		List<Integer> memoriaSegundaria = new ArrayList<>();
		List<Integer> memoriaVirtual = new ArrayList<>();
		List<Processo> processos = new ArrayList<Processo>();
		Random gerador = new Random();
		
		for(int i = 0; i < numMinCiclos; i++) {
			int a = gerador.nextInt((200 - 100) + 1) + 100;//((max - min) + 1) + min -> formula para setar o range do numero aleatorio
		}
				
	
		System.out.println("trabalho S.O.");
	}

}

class Processo{
	
	int id;
	int tamanho;
	
	public Processo(int id, int tamanho) {
		this.id = id;
		this.tamanho = tamanho;
	}
	
	public Processo() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTamanho() {
		return tamanho;
	}
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	
}
