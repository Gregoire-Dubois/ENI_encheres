package fr.eni.enienchere;

import java.util.ArrayList;
import java.util.List;
/*
 * Cette classe recense tous les codes erreurs pouvant survenir. 
 */

public class BusinessException extends Exception{
	private static final long serialVersionUID =1L;
	
	private List<Integer> listeCodesErreur;
	
	public BusinessException() {
		super();
		this.listeCodesErreur=new ArrayList<>();
	}
	
	
	//Ajouter un code erreur à la liste
	
	public void ajouterErreur(int code) {
		if(!this.listeCodesErreur.contains(code)) {
			this.listeCodesErreur.add(code);
		}
	}
	
//	public boolean hasErreur(int code) {
//		if(this.listeCodesErreur.size()>0) {
//			return true;
//		}else {
//			return false;
//		}
//	}
	
	//Nous retourne vrai si la liste contient une erreur
	//Meme chose que :
	
	public boolean hasErreur() {
		return this.listeCodesErreur.size()>0;
	}
	
	public List<Integer> getListeCodesErreur() {
		return listeCodesErreur;
	}

	
	
}
