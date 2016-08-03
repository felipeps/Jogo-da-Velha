import java.util.Scanner;

import controller.TableController;
import model.Play;
import model.Scan;
import model.Table;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		Scan scan;
		TableController tableCtrl;
		String playPiece, playerName;
		Integer plays = 1;
		Integer computerWins = 0;
		Integer playerWins = 0;
		String tableAux;

		System.out.print("Seu nome: ");
		playerName = sc.nextLine();
		
		do {
			System.out.print("Escolha X ou O: ");
			playPiece = sc.nextLine();
			
			playPiece = playPiece.toUpperCase();
			
			if ( (!(playPiece.equals("X")) && (!(playPiece.equals("O")))) )
				System.out.println("Valor digitado inválido.");
		
		} while((!(playPiece.equals("X")) && (!(playPiece.equals("O")))));
		
		tableCtrl = new TableController(new Table(3, 3), playPiece, false);
		
		System.out.println("\n" + playerName + " VERSUS Computer. FIGHT!\n");
		
		do {
			System.out.println("\nJOGO " + plays);
			System.out.println(tableCtrl.getGameTable());
			
			do {			
				boolean played = false;
				
				if ( (plays % 2) != 0) {		
					do {
						System.out.print("Jogada: ");
						scan = new Scan(sc.nextLine());
						
						if ( tableCtrl.doPlay(new Play(playPiece, playerName, scan.getRow(), scan.getCol())) )
							played = true;
						else
							System.out.println("Jogada inválida");
						
					} while(played == false);
					
					if ( tableCtrl.checkGame() )
						break;
					
					tableCtrl.doPCPlay();
					
					System.out.println(tableCtrl.getGameTable());
				}
				
				else {
					do {
						tableCtrl.doPCPlay();
						
						System.out.println(tableCtrl.getGameTable());
						
						if ( tableCtrl.checkGame() )
							break;
						
						System.out.print("Jogada: ");
						scan = new Scan(sc.nextLine());
						
						if ( tableCtrl.doPlay(new Play(playPiece, playerName, scan.getRow(), scan.getCol())))
							played = true;
						else
							System.out.println("Jogada inválida");
						
					} while(played == false);
				}
				
			} while (!tableCtrl.checkGame());
		
			if ( tableCtrl.isWinner() != null ) {
				if ( tableCtrl.isWinner() == true )
					playerWins++;
				
				if ( tableCtrl.isWinner() == false )
					computerWins++;
			}
			
			plays++;
			tableAux = tableCtrl.getGameTable();
			tableCtrl.resetTable();
			
			if ( plays == 2 ) tableCtrl.resetPC(playPiece, true);
			if ( plays == 3 ) tableCtrl.resetPC(playPiece, false);
			
		} while(plays != 4);
		
		if ( playerWins == computerWins ) {
			System.out.println("Deu velha!");
		}
		
		else if ( playerWins > computerWins ) 
			System.out.println("Você ganhou!");
		
		else
			System.out.println("Você perdeu!");
		
		System.out.println(tableAux);
	}
	
}