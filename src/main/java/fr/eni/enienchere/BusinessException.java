package fr.eni.enienchere;

import java.util.ArrayList;
import java.util.List;
/*
 * Cette classe recense tous les codes erreurs pouvant survenir. 
 */

public class BusinessException extends Exception{
private static final long serialVersionUID =1L;
	
	private List<Integer> listeCodesErreur;
	private static BusinessException instance;
	
	private BusinessException() {
		super();
		this.listeCodesErreur=new ArrayList<>();
	}
	
	public static synchronized BusinessException getInstance() {
		if(instance==null) {
			instance = new BusinessException();
		}
		return instance;
	}
	
	//Ajouter un code erreur à la liste
	
	public void ajouterErreur(int code) {
		if(!listeCodesErreur.contains(code)) {
			listeCodesErreur.add(code);
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
