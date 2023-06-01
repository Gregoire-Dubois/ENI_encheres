package fr.eni.enienchere.dal;

public abstract class DAOFactory {
	
	public static UtilisateurDAO  getUtilisateurDAO() {
		return new UtilisateurDAOJdbcImpl();
	}
	
	public static EnchereDAO getEnchereDAO() {
		return new EnchereDAOJdbcImpl();
	}

}
