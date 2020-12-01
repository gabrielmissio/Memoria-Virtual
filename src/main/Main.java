package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		/*
		 Cada processo ocupa uma pagina!
		 A memoria virtual é dividida em 5 paginas*
		 Pode haver até 5 processos na memoria virtual
		 */
		final int quantum = 20;//numero maximo que um processo pode consumir sem voltar para a fila
		final int tamMaxMemoriaFisica = 20;//parametro não sera utilizado
		final int tamMaxMemoriaVirtual = 200;//parametro não sera utilizado
		final int numPaginas = 5;//numero maximo de paginas
		final int numMinCiclos = 30;//numero minumo de ciclos
		int numeroDeCiclos = 0;
		List<Processo> memoriaVirtual = new ArrayList<Processo>();//os processos que estão armazanados na memoria fisica
		List<Processo> memoriaFisica = new ArrayList<Processo>();//os processos que estão armazenados na memoria virtual
		Random gerador = new Random();
		
		for(int i = 0; i < numMinCiclos; i++) {
			numeroDeCiclos++;
			int novoProcesso = gerador.nextInt((1 - 0) + 1) + 0;//((max - min) + 1) + min 
			if(novoProcesso == 1) {//verificar se deve ou não iniciar um novo processo
			
				int tamEmCiclos = Processo.getTamEmCiclos(1, 50, gerador);//receber o numero de ciclos de uma processo entre 1 e 50 que não seja multipo de quantum(definido como 20)
				Processo processo = new Processo(memoriaFisica.size()+1, tamEmCiclos);//gera um nuvo processo com id unico e numero de ciclos aleatorio
				memoriaFisica.add(processo);//salva o processo na memoria fisica
				System.out.println("Gerado novo processo com " + processo.getTamanho() + " ciclos!");
				
			}
			if(!memoriaFisica.isEmpty()) {
				System.out.println("**Executando processo " + memoriaFisica.get(0).getId());
				if(memoriaVirtual.contains(memoriaFisica.get(0))) {// apenas vai executando o processo
					int numDaPage = 0;
					for(int j = 0; j < memoriaVirtual.size(); j++ ) {//verifica em qual pagina(index da list memoriavirtual o processo se encontra e executalo)
						if(memoriaVirtual.get(j).equals(memoriaFisica.get(0))) {
							numDaPage = j+1;
						}
					}
					System.out.println("Processo " +  memoriaFisica.get(0).getId() +" encontrado na pagina " + numDaPage);
					//setar atributos para chamada da função posteriormente
					
					
				}else {
					System.out.println("Page fault, verificar se a espaço na memoria virtual para carregar uma nova pagina!");
					
					
					if(memoriaVirtual.size() < numPaginas) {//há espaço na memoria virtual, carregar processo para a pagina
						int numDaPage = memoriaVirtual.size() + 1;
						System.out.println("Há espaço na memoria virtual, processo "+  memoriaFisica.get(0).getId() + " carregado para a pagina " + numDaPage);//verificar em qual pagina o processo sera carregado
						// setar atributos para chamda da função posteriormente
						memoriaVirtual.add(memoriaFisica.get(0));
						
						
					}else {//não há mais espaço na memoria virtual, subtituir pagina mais antiga, escalonamento circular!!!
						memoriaVirtual.remove(0);
						memoriaVirtual.add(memoriaFisica.get(0));
						System.out.println("Não há mais espaço na memoria virtual, acionando bluffler circular e sobreescrevendo pagina mais antiga");
						//setar atributos para chamada da função posteiriomente
						
					}
				}
				if(Gerenciador.processar(memoriaFisica.get(0))) {// se o processo foi finalizado
					//liberar processo da memoria fisica e virtual
					System.out.println("Processo " + memoriaFisica.get(0).getId() + " Finalizado, liberar espaço na memoria virtual de na memoria fisica...");
					memoriaVirtual.remove(memoriaFisica.get(0));
					memoriaFisica.remove(0);
				}else {
					if(memoriaFisica.get(0).getControleQuantum() < quantum) {
						//nada por hora
						System.out.println("Processo " + memoriaFisica.get(0).getId() + " ainda não finalizado, sendo executado a menos de 20 tempos");
					}else {
						System.out.println("Processo " + memoriaFisica.get(0).getId() + " ainda não finalizado, sendo executado a 20 tempos, volta para o final da fila");
						Processo temp = memoriaFisica.get(0);
						temp.setControleQuantum(0);
						memoriaFisica.remove(0);
						memoriaFisica.add(temp);
						//recarrega controle de quantum do objeto e manda par ao fim da fila
						
					}
				}
				//------->Geranciador.prcessar
				
			}else {
				System.out.println("Nenhum processo a ser executado no momento!");
			}
		
		}
		
		while(!memoriaFisica.isEmpty()) {
			numeroDeCiclos++;
			System.out.println("**Executando processo " + memoriaFisica.get(0).getId());
			if(memoriaVirtual.contains(memoriaFisica.get(0))) {// apenas vai executando o processo
				int numDaPage = 0;
				for(int j = 0; j < memoriaVirtual.size(); j++ ) {//verifica em qual pagina(index da list memoriavirtual o processo se encontra e executalo)
					if(memoriaVirtual.get(j).equals(memoriaFisica.get(0))) {
						numDaPage = j+1;
					}
				}
				System.out.println("Processo " +  memoriaFisica.get(0).getId() +" encontrado na pagina " + numDaPage);
				//setar atributos para chamada da função posteriormente
				
				
			}else {
				System.out.println("Page fault, verificar se a espaço na memoria virtual para carregar uma nova pagina!");
				
				
				if(memoriaVirtual.size() < numPaginas) {//há espaço na memoria virtual, carregar processo para a pagina
					int numDaPage = memoriaVirtual.size() + 1;
					System.out.println("Há espaço na memoria virtual, processo "+  memoriaFisica.get(0).getId() + " carregado para a pagina " + numDaPage);//verificar em qual pagina o processo sera carregado
					// setar atributos para chamda da função posteriormente
					memoriaVirtual.add(memoriaFisica.get(0));
					
					
				}else {//não há mais espaço na memoria virtual, subtituir pagina mais antiga, escalonamento circular!!!
					memoriaVirtual.remove(0);
					memoriaVirtual.add(memoriaFisica.get(0));
					System.out.println("Não há mais espaço na memoria virtual, acionando bluffler circular e sobreescrevendo pagina mais antiga");
					//setar atributos para chamada da função posteiriomente
					
				}
			}
			if(Gerenciador.processar(memoriaFisica.get(0))) {// se o processo foi finalizado
				//liberar processo da memoria fisica e virtual
				System.out.println("Processo " + memoriaFisica.get(0).getId() + " Finalizado, liberar espaço na memoria virtual de na memoria fisica... (removida pagina em que se encontrava o processo)");
				memoriaVirtual.remove(memoriaFisica.get(0));
				memoriaFisica.remove(0);
			}else {
				if(memoriaFisica.get(0).getControleQuantum() < quantum) {
					//nada por hora
					System.out.println("Processo " + memoriaFisica.get(0).getId() + " ainda não finalizado, sendo executado a menos de 20 tempos");
				}else {
					System.out.println("Processo " + memoriaFisica.get(0).getId() + " ainda não finalizado, sendo executado a 20 tempos, volta para o final da fila");
					Processo temp = memoriaFisica.get(0);
					temp.setControleQuantum(0);
					memoriaFisica.remove(0);
					memoriaFisica.add(temp);
					//recarrega controle de quantum do objeto e manda par ao fim da fila
					
				}
			}
			//------->Geranciador.prcessar
			
		
		}
				
		System.out.println("\n***********************************************");
		System.out.println("*Todos os processos foram finalizados em " + numeroDeCiclos + " ciclos!!!");
		System.out.println("***********************************************");
	}

}

//classe para gerenciar o processo em execução
class Gerenciador{
	public static boolean processar(Processo p) {
		
		p.setControleQuantum(p.getControleQuantum() + 1);
		p.setTamanho(p.getTamanho() -1);
		if(p.getTamanho() == 0) {//se o processo foi finalizado
			return true;
		}
		//se o processo ainda não foi finalizado
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
		if(tamEmCiclo%20 == 0) {//20 = quantom, o tamanho do cilho não pode ser multiplo de 20
			getTamEmCiclos(min, max, gerador);
		}
		
		return tamEmCiclo;
	}
	
}
