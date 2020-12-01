package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.midi.Soundbank;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int quantum = 20;//numero maximo que um processo pode consumir sem voltar para a fila
		final int tamMaxMemoriaFisica = 20;
		final int tamMaxMemoriaVirtual = 200;
		final int numPagina = 5;
		final int numMinCiclos = 100;
		List<Processo> memoriaVirtual = new ArrayList<Processo>();
		List<Processo> memoriaFisica = new ArrayList<Processo>();
		Random gerador = new Random();
		
		for(int i = 0; i < numMinCiclos; i++) {
			int novoProcesso = gerador.nextInt((1 - 0) + 1) + 0;//((max - min) + 1) + min 
			if(novoProcesso == 1) {//verificar se deve ou n�o iniciar um novo processo
			
				int tamEmCiclos = Processo.getTamEmCiclos(1, 50, gerador);//receber o numero de ciclos de uma processo entre 1 e 50 que n�o seja multipo de quantum(definido como 20)
				Processo processo = new Processo(memoriaFisica.size()+1, tamEmCiclos);//gera um nuvo processo com id unico e numero de ciclos aleatorio
				memoriaFisica.add(processo);//salva o processo na memoria fisica
				System.out.println("Gerado novo processo com " + processo.getTamanho() + " ciclos!");
				
			}
			
			if(!memoriaFisica.isEmpty()) {
				if(memoriaVirtual.contains(memoriaFisica.get(0))) {// apenas vai executando o processo
					System.out.println("Processo encontrado na pagina N");
					//verificar em qual pagina(index da list memoriavirtual o processo se encontra e executalo)
					
					
				}else {
					System.out.println("Page fault, verificar se a espa�o na memoria virtual para carregar uma nova pagina!");
					
					
					if(memoriaVirtual.size() <= 5) {//h� espa�o na memoria virtual, carregar processo para a pagina
						System.out.println("H� espa�o na memoria virtual, processo carregado para a pagina N");
						//verificar em qual pagina o processo sera carregado
						
					}else {//n�o h� mais espa�o na memoria virtual, subtituir pagina
						System.out.println("N�o h� mais espa�o na memoria virtual, o processo sera carregado na pagina N");
						//Rodar algoritimo para esoclher pagina a ser substituida
						
					}
				}
				
				//chama funcao para executar o processo recebendo o processo e as listas, retornando true se o processo foi concluido e false se faltou
				
				
			}else {
				System.out.println("Nenhum processo a ser executado no momento!");
			}
		
		}
				
	
		System.out.println("trabalho S.O. ");
	}

}

/*
System.out.println("Executando processo " + memoriaFisica.get(0).getId());
	memoriaFisica.get(0).setTamanho(memoriaFisica.get(0).getTamanho() - 1);
	if(memoriaFisica.get(0).getTamanho() > 0) {
		System.out.println("Limite do quantum atindido o processo " + memoriaFisica.get(0).getId() + " dara espaço para um novo processo...");
		Processo temp = memoriaFisica.get(0);
		memoriaFisica.remove(0);
		memoriaFisica.add(temp);
	}else {
		System.out.println("Finalizando execução do processo " + memoriaFisica.get(0).getId() + " removendo da memoria princial...");
		memoriaFisica.remove(0);
	}
 * */

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
