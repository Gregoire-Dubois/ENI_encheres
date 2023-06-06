package fr.eni.enienchere.dal;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Retrait;

public interface RetraitDAO {
	public Retrait insert(ArticleVendu article) throws BusinessException;
}
