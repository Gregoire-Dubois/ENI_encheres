package fr.eni.enienchere.dal;
/*
 *Contient uniquement des codes d’erreurs correspondants aux erreurs possibles 
 *identifiées au niveau des méthodes de la classe UtilisateurDAO 
 * 
 *Plage erreur de 10000 à 19999
 */
public abstract class CodesResultatDAL {
	public static final int DECONNEXION_ECHEC = 10000;
	public static final int SELECT_BY_ID_ECHEC = 10001;
	public static final int SELECT_BY_PSEUDO_ECHEC=10002;
	public static final int SELECT_BY_EMAIL_ECHEC=10003;
	public static final int SELECT_ALL_CATEGORIES_ECHEC = 11000;
}
