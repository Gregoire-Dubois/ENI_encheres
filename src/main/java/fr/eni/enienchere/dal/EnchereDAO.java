package fr.eni.enienchere.dal;

import java.util.List;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Enchere;

public interface EnchereDAO {
	//Liste de toutes les enchères
	List<Enchere> selectAllEnchere() throws BusinessException;

	//A faire
	List<Enchere> selectAllEnchereByNoArticle(int noArticle) throws BusinessException;
	
	

	List<Enchere> selectAllEnchereByNoUtilisateur(int noUtilisateur);
	
	//Liste des enchères d'un utilisateur
	
	//Liste des enchères selon article
	
	//Ajout d'une enchère 
	
	//Sélection d'une enchère selon id
	
	//
}
