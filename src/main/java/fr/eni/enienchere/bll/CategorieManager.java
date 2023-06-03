/*
 * @author CCN
 * CategorieManager v1
 */


package fr.eni.enienchere.bll;

import fr.eni.enienchere.dal.DAOFactory;

import java.util.ArrayList;
import java.util.List;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Categorie;
import fr.eni.enienchere.dal.CategorieDAO;

public class CategorieManager {
	private static CategorieManager instance = null;
	private CategorieDAO categorieDAO ;

	//Constructeur privé
	public CategorieManager() {
		this.categorieDAO = DAOFactory.getCategorieDAO();
	}

	//SINGLETON
	public static CategorieManager getInstance() {
		if (instance == null) {
			instance = new CategorieManager();
		}
		return instance;
	}

public Categorie selectionner(int id) throws BusinessException {
	Categorie categorie = this.categorieDAO.selectCategorieById(id);
	return categorie;
	}

public List<Categorie> selectAll() throws BusinessException {
	List<Categorie> listCategorie = new ArrayList<>();
	listCategorie = this.categorieDAO.selectAllCategorie();
	return listCategorie;
	}
}