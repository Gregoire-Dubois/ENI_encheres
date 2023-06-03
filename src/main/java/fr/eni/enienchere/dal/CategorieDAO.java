/**
 * @author CCN
 * Creation le 02/06/23
 */
package fr.eni.enienchere.dal;

import java.util.List;
import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.Categorie;

/**
 * @author CCN
 *
 */
public interface CategorieDAO {
	List<Categorie> selectAllCategorie() throws BusinessException;
    Categorie selectCategorieById(int CategorieId) throws BusinessException;
    Categorie selectByName(String nomCategorie) throws BusinessException;
}
