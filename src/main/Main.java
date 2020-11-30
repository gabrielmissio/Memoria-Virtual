package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.midi.Soundbank;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int quantum = 20;//numero maximo que um processo pode consumir sem voltar para a fila
		final int tamMaxMemoriaPrincipal = 200;
		final int tamMaxMemoriaVirtual = 20;
		final int tamMaxMemoriaSecundaria = 1000;
		final int numMinCiclos = 100;
		List<Integer> memoriaPrincipal = new ArrayList<>();
		List<Integer> memoriaSegundaria = new ArrayList<>();
		List<Integer> memoriaVirtual = new ArrayList<>();
		List<Processo> processos = new ArrayList<Processo>();
		Random gerador = new Random();
		
		for(int i = 0; i < numMinCiclos; i++) {
			int novoProcesso = gerador.nextInt((1 - 0) + 1) + 0;//((max - min) + 1) + min 
			if(novoProcesso == 1) {
			
				int tamEmCiclos = Processo.getTamEmCiclos(0, 50, gerador);
				Processo processo = new Processo(processos.size()+1, tamEmCiclos);
				processos.add(processo);
				System.out.println("Novo Processo iniciado com " + processo.getTamanho() + " ciclos!");
				
			}
			
			if(!processos.isEmpty()) {
				System.out.println("Executando processo " + processos.get(0).getId());
				processos.get(0).setTamanho(processos.get(0).getTamanho() - quantum);
				if(processos.get(0).getTamanho() > 0) {
					System.out.println("Limite do quantum atindido o processo " + processos.get(0).getId() + " dara espaço para um novo processo...");
					Processo temp = processos.get(0);
					processos.remove(0);
					processos.add(temp);
				}else {
					System.out.println("Finalizando execução do processo " + processos.get(0).getId() + " removendo da memoria princial...");
					processos.remove(0);
				}
			}else {
				System.out.println("Nenhum processo a ser executado no momento!");
			}
		
		}
				
	
		System.out.println("trabalho S.O. ");
	}

}

class Processo{
	
	int id;
	int tamanho;
	String nome;

	public Processo(int id, int tamanho) {
		this.id = id;
		this.tamanho = tamanho;
	}
	
	public Processo() {
		
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
	
	public static int getTamEmCiclos(int min, int max, Random gerador) {
		int tamEmCiclo = gerador.nextInt((max - min) + 1) + min;
		if(tamEmCiclo%20 == 0) {//20 = quantom
			getTamEmCiclos(min, max, gerador);
		}
		
		return tamEmCiclo;
	}
	
}
