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
	public static final int UPDATE_ECHEC = 10004;
	public static final int UPDATE_CREDIT_APRES_ENCHERE_ECHEC = 10005;

	//Catégorie
	public static final int SELECT_ALL_CATEGORIES_ECHEC = 11000;
	public static final int SELECT_CATEGORIE_BY_NAME_ECHEC = 11001;
	public static final int SELECT_CATEGORIE_BY_ID_ECHEC = 11002;
	
	// erreur sur base de données pour articles
	public static final int SELECT_ECHEC = 13000; 
	public static final int SELECT_BY_ETAT_MOT_CATEGORIE_ECHEC=13003;
	//Enchere
	public static final int SELECT_BY_NO_ARTICLE_ECHEC =12000 ;
	public static final int SELECT_ENCHERE_MAX_BY_ARTICLE_ECHEC = 12001;
	public static final int SELECT_BY_NO_ENCHERE_ECHEC = 12002;

	
	//Articles Vendus
	public static final int DECONNEXION_RESULTSET_ECHEC = 13000;
	public static final int DECONNEXION_PREPAREDSTATEMENT_ECHEC = 13001;
	public static final int SELECT_ALL_VENTE_EC_BY_CATEGORIE_ID_ECHEC = 13002;
	public static final int SELECT_ALL_VENTE_NC_BY_UTILISATEUR_ID_ECHEC = 13003;
	public static final int SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID_ECHEC = 13004;
	public static final int SELECT_ALL_VENTE_VE_BY_UTILISATEUR_ID_ECHEC = 13005;

}
