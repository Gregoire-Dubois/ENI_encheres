package fr.eni.enienchere.dal;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Enchere;
import fr.eni.enienchere.bo.Utilisateur;

public interface EnchereDAO {
	//Liste de toutes les enchères
	List<Enchere> selectAllEnchere() throws BusinessException;

	//A faire
	List<Enchere> selectAllEnchereByNoArticle(int noArticle) throws BusinessException;	
	List<Enchere> selectAllEnchereByNoUtilisateur(int noUtilisateur);
	
	//Liste des enchères d'un utilisateur
	
	//Liste des enchères selon article
	
	//Enchère Maximum pour un article donné
	public Enchere selectEnchereMaxByArticle(int noArticle) throws BusinessException;
	
	
	
	//Ajout d'une enchère 
	public Enchere insertNewEnchere(Utilisateur acheteur, int noArticle, int montantEnchere) throws BusinessException;
    
	//Sélection d'une enchère selon id
	
}
