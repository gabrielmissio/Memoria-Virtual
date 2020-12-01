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
		final int numMinCiclos = 10;//numero minumo de ciclos
		int numeroDeCiclos = 0;
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
			System.out.println("**Executando processo " + memoriaFisica.get(0).getId());
			if(!memoriaFisica.isEmpty()) {
				if(memoriaVirtual.contains(memoriaFisica.get(0))) {// apenas vai executando o processo
					int numDaPage = 0;
					for(int j = 0; j < memoriaVirtual.size(); j++ ) {//verifica em qual pagina(index da list memoriavirtual o processo se encontra e executalo)
						if(memoriaVirtual.get(j).equals(memoriaFisica.get(0))) {
							numDaPage = j+1;
						}
					}
					System.out.println("Processo " +  memoriaFisica.get(0).getId() +" encontrado na pagina " + numDaPage);
					//setar atributos para chamada da funÁ„o posteriormente
					
					
				}else {
					System.out.println("Page fault, verificar se a espaÁo na memoria virtual para carregar uma nova pagina!");
					
					
					if(memoriaVirtual.size() < numPaginas) {//h· espaÁo na memoria virtual, carregar processo para a pagina
						int numDaPage = memoriaVirtual.size() + 1;
						System.out.println("H· espaÁo na memoria virtual, processo "+  memoriaFisica.get(0) + "carregado para a pagina " + numDaPage);//verificar em qual pagina o processo sera carregado
						// setar atributos para chamda da funÁ„o posteriormente
						memoriaVirtual.add(memoriaFisica.get(0));
						
						
					}else {//n„o h· mais espaÁo na memoria virtual, subtituir pagina mais antiga, escalonamento circular!!!
						memoriaVirtual.remove(0);
						memoriaVirtual.add(memoriaFisica.get(0));
						System.out.println("N„o h· mais espaÁo na memoria virtual, acionando bluffler circular e sobreescrevendo pagina mais antiga");
						//setar atributos para chamada da funÁ„o posteiriomente
						
					}
				}
				if(Gerenciador.processar(memoriaFisica.get(0))) {// se o processo foi finalizado
					//liberar processo da memoria fisica e virtual
					System.out.println("Processo " + memoriaFisica.get(0).getId() + " Finalizado, liberar espaÁo na memoria virtual de na memoria fisica...");
					memoriaVirtual.remove(memoriaFisica.get(0));
					memoriaFisica.remove(0);
				}else {
					if(memoriaFisica.get(0).getControleQuantum() < quantum) {
						//nada por hora
						System.out.println("Processo " + memoriaFisica.get(0).getId() + " ainda n„o finalizado, sendo executado a menos de 20 tempos");
					}else {
						System.out.println("Processo " + memoriaFisica.get(0).getId() + " ainda n„o finalizado, sendo executado a 20 tempos, volta para o final da fila");
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
			System.out.println("**Executando processo " + memoriaFisica.get(0).getId());
			if(memoriaVirtual.contains(memoriaFisica.get(0))) {// apenas vai executando o processo
				int numDaPage = 0;
				for(int j = 0; j < memoriaVirtual.size(); j++ ) {//verifica em qual pagina(index da list memoriavirtual o processo se encontra e executalo)
					if(memoriaVirtual.get(j).equals(memoriaFisica.get(0))) {
						numDaPage = j+1;
					}
				}
				System.out.println("Processo " +  memoriaFisica.get(0).getId() +" encontrado na pagina " + numDaPage);
				//setar atributos para chamada da funÁ„o posteriormente
				
				
			}else {
				System.out.println("Page fault, verificar se a espaÁo na memoria virtual para carregar uma nova pagina!");
				
				
				if(memoriaVirtual.size() < numPaginas) {//h· espaÁo na memoria virtual, carregar processo para a pagina
					int numDaPage = memoriaVirtual.size() + 1;
					System.out.println("H· espaÁo na memoria virtual, processo "+  memoriaFisica.get(0).getId() + "carregado para a pagina " + numDaPage);//verificar em qual pagina o processo sera carregado
					// setar atributos para chamda da funÁ„o posteriormente
					memoriaVirtual.add(memoriaFisica.get(0));
					
					
				}else {//n„o h· mais espaÁo na memoria virtual, subtituir pagina mais antiga, escalonamento circular!!!
					memoriaVirtual.remove(0);
					memoriaVirtual.add(memoriaFisica.get(0));
					System.out.println("N„o h· mais espaÁo na memoria virtual, acionando bluffler circular e sobreescrevendo pagina mais antiga");
					//setar atributos para chamada da funÁ„o posteiriomente
					
				}
			}
			if(Gerenciador.processar(memoriaFisica.get(0))) {// se o processo foi finalizado
				//liberar processo da memoria fisica e virtual
				System.out.println("Processo " + memoriaFisica.get(0).getId() + " Finalizado, liberar espaÁo na memoria virtual de na memoria fisica...");
				memoriaVirtual.remove(memoriaFisica.get(0));
				memoriaFisica.remove(0);
			}else {
				if(memoriaFisica.get(0).getControleQuantum() < quantum) {
					//nada por hora
					System.out.println("Processo " + memoriaFisica.get(0).getId() + " ainda n„o finalizado, sendo executado a menos de 20 tempos");
				}else {
					System.out.println("Processo " + memoriaFisica.get(0).getId() + " ainda n„o finalizado, sendo executado a 20 tempos, volta para o final da fila");
					Processo temp = memoriaFisica.get(0);
					temp.setControleQuantum(0);
					memoriaFisica.remove(0);
					memoriaFisica.add(temp);
					//recarrega controle de quantum do objeto e manda par ao fim da fila
					
				}
			}
			//------->Geranciador.prcessar
			
		
		}
				
	
		System.out.println("Todos os processos foram finalizados!!!");
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
	public static boolean processar(Processo p) {
		
		p.setControleQuantum(p.getControleQuantum() + 1);
		p.setTamanho(p.getTamanho() -1);
		if(p.getTamanho() == 0) {//se o processo foi finalizado
			return true;
		}
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
