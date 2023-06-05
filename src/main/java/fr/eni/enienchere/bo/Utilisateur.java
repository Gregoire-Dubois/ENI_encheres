/* BO Utilisateur
 * v1 Priscillia
 * v2 Ced : correction sur les types int et boolean (anc. Integer et Boolean)
 * 
 */


package fr.eni.enienchere.bo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.dal.DAOFactory;

public class Utilisateur {
	
	/* Bout de code inutile ici ? 
	private static final long serialVersionUID = 1L;
	*/
	private int noUtilisateur;
	private String pseudo;
	private String nom; 
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal; 
	private String ville; 
	private String motDePasse; 
	private int credit; 
	private boolean administrateur;
	
//	private List<ArticleVendu> listeAchats;
	private List<ArticleVendu> listeVentes; // = new ArrayList<>();
	private List<Enchere> listeEncheres;
	//CCN : commentaire idem que sur catégorie : ces listes sont des select qu'on gèrera dans l'objet Article. Mais elle ne définissent pas l'objet Utilisateur. 
	//Il faut coller au diagramme de classe mais je me trompe peut-ête. 
	
	
	public Utilisateur() {
		super();
		//listeVentes = new ArrayList<>();
	}
	
	// constructeur sans le numéro utilisateur

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String code_postal, String ville, String mot_de_passe, int credit, boolean administrateur) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = code_postal;
		this.ville = ville;
		this.motDePasse = mot_de_passe;
		this.credit = credit;
		this.administrateur = administrateur;
	}


	// constructeur avec toutes les variables utilisateur

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String mot_de_passe, int credit, boolean administrateur) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = code_postal;
		this.ville = ville;
		this.motDePasse = mot_de_passe;
		this.credit = credit;
		this.administrateur = administrateur;
	}

	
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}
	
//	public void ajouterEnchere(Enchere enchere) {
//		if(enchere.getUtilisateur().equals(this) && !listeEncheres.contains(enchere)){
//			this.listeEncheres.add(enchere);
//		}
//		else
//		{
//			//Message d'erreurs?
//			System.out.println("ajout impossible");
//		}
//	}
	
//	public void ajouterArticleAchat(ArticleVendu article) {
//		if(article.getAcquereur().equals(this) && !listeAchats.contains(article)){
//			this.listeAchats.add(article);
//		}
//		else
//		{
//			//Message d'erreurs?
//			System.out.println("ajout impossible");
//		}
//	}
//	public void ajouterArticleVente(ArticleVendu article) {
//		if(article.getVendeur().equals(this) && (!listeVentes.contains(article))){
//			this.listeVentes.add(article);
//		}
//		else
//		{
//			//Message d'erreurs?
//			System.out.println("ajout impossible");
//		}
//	}
	
//	public void ajouterArticleVente(ArticleVendu article) {
//		if(article.getVendeur().equals(this)) {
//			if(listeVentes!=null && (!listeVentes.contains(article))){
//				this.listeVentes.add(article);
//			}
//			if(listeVentes==null) {
//				listeVentes = new ArrayList<>();
//				this.listeVentes.add(article);
//			}
//			
//		}
//		else
//		{
//			//Message d'erreurs?
//			System.out.println("ajout impossible");
//		}
//	}

//	public List<ArticleVendu> getListeAchats() {
//		return listeAchats;
//	}

	public List<ArticleVendu> getListeVentes() throws BusinessException {
		if(listeVentes==null) {
			listeVentes=DAOFactory.getArticleDAO().selectAllArticlesByNoUtilisateur(noUtilisateur);
		}
		return listeVentes;
	}

	public List<Enchere> getListeEncheres() {
		if(listeEncheres==null) {
			listeEncheres=DAOFactory.getEnchereDAO().selectAllEnchereByNoUtilisateur(noUtilisateur);
		}
		return listeEncheres;
	}
	@Override
	public String toString() {
		return "Utilisateur [no_utilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", code_postal="
				+ codePostal + ", ville=" + ville + ", mot_de_passe=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + "]";
	}

	
	/*
	 * 01/06/2023
	 * Ajout de CCN pour la hash du mot de passe
	 */
	
	public static String hashPwd(String pwd)
	{
		//Instance de MessageDigest et de StringBuffer
		MessageDigest md=null;
		StringBuffer sb=new StringBuffer();
		byte[] reponse;
		
		try {
			md=MessageDigest.getInstance("MD5");
			reponse=md.digest(pwd.getBytes());
			for(int i:reponse)
			{
				sb.append((Integer.toString((i&0xff)+0x100, 16).substring(1)));
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
}
