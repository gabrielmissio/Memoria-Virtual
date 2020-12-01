package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		/*
		 Cada processo ocupa uma pagina!
		 A memoria virtual È dividida em 5 paginas*
		 Pode haver atÈ 5 processos na memoria virtual
		 */
		final int quantum = 20;//numero maximo que um processo pode consumir sem voltar para a fila
		final int tamMaxMemoriaFisica = 20;//parametro n„o sera utilizado
		final int tamMaxMemoriaVirtual = 200;//parametro n„o sera utilizado
		final int numPaginas = 5;//numero maximo de paginas
		final int numMinCiclos = 100;//numero minumo de ciclos
		List<Processo> memoriaVirtual = new ArrayList<Processo>();//os processos que est„o armazanados na memoria fisica
		List<Processo> memoriaFisica = new ArrayList<Processo>();//os processos que est„o armazenados na memoria virtual
		Random gerador = new Random();
		
		for(int i = 0; i < numMinCiclos; i++) {
			int novoProcesso = gerador.nextInt((1 - 0) + 1) + 0;//((max - min) + 1) + min 
			if(novoProcesso == 1) {//verificar se deve ou n„o iniciar um novo processo
			
				int tamEmCiclos = Processo.getTamEmCiclos(1, 50, gerador);//receber o numero de ciclos de uma processo entre 1 e 50 que n„o seja multipo de quantum(definido como 20)
				Processo processo = new Processo(memoriaFisica.size()+1, tamEmCiclos);//gera um nuvo processo com id unico e numero de ciclos aleatorio
				memoriaFisica.add(processo);//salva o processo na memoria fisica
				System.out.println("Gerado novo processo com " + processo.getTamanho() + " ciclos!");
				
			}
			
			if(!memoriaFisica.isEmpty()) {
				if(memoriaVirtual.contains(memoriaFisica.get(0))) {// apenas vai executando o processo
					System.out.println("Processo encontrado na pagina N");
					//verificar em qual pagina(index da list memoriavirtual o processo se encontra e executalo)
					
					
				}else {
					System.out.println("Page fault, verificar se a espaÁo na memoria virtual para carregar uma nova pagina!");
					
					
					if(memoriaVirtual.size() <= numPaginas) {//h· espaÁo na memoria virtual, carregar processo para a pagina
						System.out.println("H· espaÁo na memoria virtual, processo carregado para a pagina N");
						//verificar em qual pagina o processo sera carregado
						
					}else {//n„o h· mais espaÁo na memoria virtual, subtituir pagina
						System.out.println("N„o h· mais espaÁo na memoria virtual, o processo sera carregado na pagina N");
						//Rodar algoritimo para esoclher pagina a ser substituida
						
					}
				}
				
				//chama funcao para executar o processo recebendo o processo e as listas, retornando true se o processo foi concluido e false se faltou
				//------->Geranciador.prcessar
				
			}else {
				System.out.println("Nenhum processo a ser executado no momento!");
			}
		
		}
		/*
		while(!memoriaFisica.isEmpty()) {
			//continuar processando atÈ finalizar todos os processos
			//----------->Gerenciador.processar
		}*/
				
	
		System.out.println("trabalho S.O. ");
	}

}

/*
//logica do escalonamento circular para gerenciar os processos
System.out.println("Executando processo " + memoriaFisica.get(0).getId());
	memoriaFisica.get(0).setTamanho(memoriaFisica.get(0).getTamanho() - 1);
	if(memoriaFisica.get(0).getTamanho() > 0) {
		System.out.println("Limite do quantum atindido o processo " + memoriaFisica.get(0).getId() + " dara espa√ßo para um novo processo...");
		Processo temp = memoriaFisica.get(0);
		memoriaFisica.remove(0);
		memoriaFisica.add(temp);
	}else {
		System.out.println("Finalizando execu√ß√£o do processo " + memoriaFisica.get(0).getId() + " removendo da memoria princial...");
		memoriaFisica.remove(0);
	}
 * */

class Gerenciador{
	public static boolean processar(List MemoriaVirtual, List memoriaFisica) {
		
		/*
			//se o processo for finalizado
			return true;
		*/
		
		//se o processo ainda n„o foi finalizado
		return false;
	}
}

class Processo{
	
	int id;
	int tamanho;
	String nome;
	int controleQuantum = 0;

	public Processo(int id, int tamanho) {
		this.id = id;
		this.tamanho = tamanho;
	}
	
	public Processo() {
		
	}
	
	public int getControleQuantum() {
		return controleQuantum;
	}

	public void setControleQuantum(int controleQuantum) {
		this.controleQuantum = controleQuantum;
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
		if(tamEmCiclo%20 == 0) {//20 = quantom, o tamanho do cilho n„o pode ser multiplo de 20
			getTamEmCiclos(min, max, gerador);
		}
		
		return tamEmCiclo;
	}
	
}
