package fr.eni.enienchere.bll;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Enchere;
import fr.eni.enienchere.bo.Utilisateur;
import fr.eni.enienchere.dal.DAOFactory;
import fr.eni.enienchere.dal.EnchereDAO;

public class EnchereManager {
	private static EnchereManager instance = null;
	private EnchereDAO enchereDAO ;

	//Constructeur privé
	public EnchereManager() {
		this.enchereDAO = DAOFactory.getEnchereDAO();
	}

	//SINGLETON
	public static EnchereManager getInstance() {
		if (instance == null) {
			instance = new EnchereManager();
		}
		return instance;
	}

public Enchere selectEnchereMaxByArticle(int noArticle) throws BusinessException {
	Enchere enchere = this.enchereDAO.selectEnchereMaxByArticle(noArticle);
	return enchere;
	}

public Enchere insertNewEnchere(Utilisateur acheteur, int noArticle, int montantEnchere) throws BusinessException {
	Enchere enchere = this.enchereDAO.insertNewEnchere(acheteur, noArticle, montantEnchere);
	return enchere;
	}

public Enchere selectEnchereByNoEnchere(int noEnchere) throws BusinessException {
	Enchere enchere = this.enchereDAO.selectEnchereByNoEnchere(noEnchere);
	return enchere;
	}
}

