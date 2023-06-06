package fr.eni.enienchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import fr.eni.enienchere.BusinessException;
import fr.eni.enienchere.bo.ArticleVendu;
import fr.eni.enienchere.bo.Categorie;
import fr.eni.enienchere.bo.Retrait;
import fr.eni.enienchere.bo.Utilisateur;


public class ArticleDAOJdbcImpl implements ArticleDAO {
	
	private static final String INSERT_ARTICLE="INSERT INTO ARTICLES_VENDUS(nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie) VALUES(?,?,?,?,?,?,?)";
	
	private static final String SELECT_ALL_ARTICLE = "SELECT * FROM articles_vendus;";
	
	private static final String SELECT_ARTICLE_BY_ID = 					"SELECT"
																		+ "a.nomArticle, a.description, c.libelle AS categorie, e.montant_enchere, ua.pseudo AS acquereur, a.prix_initial, "
																		+ "a.date_fin_encheres, r.rue AS rue_retrait, r.code_postal AS code_postal_retrait, r.ville AS ville_retrait, "
																		+ "UV.pseudo AS pseudo_vendeur, "
																		+ "ROW_NUMBER() OVER (ORDER BY e.montant_enchere DESC) AS row_number"
																		+ "FROM"
																		+ "ARTICLES_VENDUS a INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
																		+ "LEFT JOIN ENCHERES e ON a.no_article = e.no_article"
																		+ "LEFT JOIN UTILISATEURS ua ON e.no_utilisateur = ua.no_utilisateur"
																		+ "LEFT JOIN UTILISATEURS uv ON a.no_utilisateur = uv.no_utilisateur"
																		+ "LEFT JOIN RETRAITS r ON a.no_article = r.no_article"
																		+ "WHERE"
																		+ "AV.no_article = ?) AS subquery WHERE row_number = 1;";
	
	private static final String SELECT_ALL_VENTE_EC = 					"SELECT "
																		+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur, vendeur.pseudo as vendeur, a.no_categorie as id_categorie, "
																		+ "c.libelle as categorie_libelle, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, "
																		+ "e.no_enchere as id_enchere_max, e.no_article as ench_idArticle, e.no_utilisateur as id_meilleur_encherisseur, "
																		+ "encherisseur.pseudo as pseudo_encherisseur, date_enchere, montant_enchere, etat_vente"
																		+ "FROM "
																		+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON  a.no_article =  e.no_article AND "
																		+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article = e.no_article)"
																		+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
																		+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
																		+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
																		+ "WHERE "
																		+ " a.etat_vente = 'EC';";
	
	private static final String SELECT_ALL_VENTE_EC_BY_CATEGORIE_ID = 	"SELECT "
																		+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur, vendeur.pseudo as vendeur,"
																		+ "a.no_categorie as id_categorie, c.libelle as categorie_libelle, nom_article, description,"
																		+ "date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, e.no_enchere as id_enchere_max,"
																		+ "e.no_article as ench_idArticle, e.no_utilisateur as id_meilleur_encherisseur, encherisseur.pseudo as pseudo_encherisseur,"
																		+ "date_enchere, montant_enchere, etat_vente"
																		+ "FROM"
																		+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON a.no_article = e.no_article AND "
																		+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article =  e.no_article)"
																		+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
																		+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
																		+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
																		+ "WHERE "
																		+ "a.etat_vente = 'EC' AND a.no_categorie  = ?;";
	
	private static final String SELECT_ALL_VENTE_NC_BY_UTILISATEUR_ID = "SELECT "
																		+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur,vendeur.pseudo as vendeur,a.no_categorie as id_categorie, "
																		+ "c.libelle as categorie_libelle, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, "
																		+ "prix_vente, e.no_enchere as id_enchere_max, e.no_article as ench_idArticle,e.no_utilisateur as id_meilleur_encherisseur, "
																		+ "encherisseur.pseudo as pseudo_encherisseur, date_enchere, montant_enchere, etat_vente"
																		+ "FROM "
																		+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON  a.no_article =  e.no_article AND "
																		+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article =  e.no_article)"
																		+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
																		+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
																		+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
																		+ "WHERE "
																		+ "a.etat_vente = 'NC' AND vendeur.no_utilisateur = ?;";
	
	private static final String SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID = "SELECT "
																		+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur,vendeur.pseudo as vendeur,a.no_categorie as id_categorie, "
																		+ "c.libelle as categorie_libelle, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, "
																		+ "prix_vente, e.no_enchere as id_enchere_max, e.no_article as ench_idArticle,e.no_utilisateur as id_meilleur_encherisseur, "
																		+ "encherisseur.pseudo as pseudo_encherisseur, date_enchere, montant_enchere, etat_vente"
																		+ "FROM "
																		+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON  a.no_article =  e.no_article AND "
																		+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article =  e.no_article)"
																		+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
																		+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
																		+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
																		+ "WHERE "
																		+ "a.etat_vente = 'EC' AND vendeur.no_utilisateur = ?;";
	
	private static final String SELECT_ALL_VENTE_VE_BY_UTILISATEUR_ID = "SELECT "
																		+ "a.no_article as id_article, a.no_utilisateur as id_utilisateur,vendeur.pseudo as vendeur,a.no_categorie as id_categorie, "
																		+ "c.libelle as categorie_libelle, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, "
																		+ "prix_vente, e.no_enchere as id_enchere_max, e.no_article as ench_idArticle,e.no_utilisateur as id_meilleur_encherisseur, "
																		+ "encherisseur.pseudo as pseudo_encherisseur, date_enchere, montant_enchere, etat_vente"
																		+ "FROM "
																		+ "ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON  a.no_article =  e.no_article AND "
																		+ "e.no_enchere = ( SELECT max(e.no_enchere) FROM ENCHERES e WHERE a.no_article =  e.no_article)"
																		+ "LEFT JOIN CATEGORIES c ON a.no_categorie = c.no_categorie"
																		+ "LEFT JOIN UTILISATEURS vendeur ON a.no_utilisateur = vendeur.no_utilisateur"
																		+ "LEFT JOIN UTILISATEURS encherisseur ON e.no_utilisateur = encherisseur.no_utilisateur"
																		+ "WHERE "
																		+ "a.etat_vente = 'VE' AND vendeur.no_utilisateur = ?;";
	
	

	@Override
	public ArticleVendu selectArticleById(int articleId) throws BusinessException {
		ArticleVendu article = null;
		Connection cnx = null;
		PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
    		cnx = ConnectionProvider.getConnection();
    		pstmt = cnx.prepareStatement(SELECT_ARTICLE_BY_ID);

    		pstmt.setInt(1, articleId);
    		
    		rs = pstmt.executeQuery();

	        if (rs.next()) {
	        	String nomArticle = rs.getString("nomArticle");
                String description = rs.getString("description");
                String categorie = rs.getString("categorie");
                int prixVente = rs.getInt("montant_enchere");
                String pseudoAcquereur = rs.getString("pseudo_acquereur");
                int prixInitial = rs.getInt("prix_initial");
                LocalDate dateFinEncheres = rs.getDate("date_fin_encheres").toLocalDate();
                String rueRetrait = rs.getString("rue_retrait");
                String codePostalRetrait = rs.getString("code_postal_retrait");
                String villeRetrait = rs.getString("ville_retrait");
                String pseudoVendeur = rs.getString("pseudo_vendeur");

                // Récupérer les objets Utilisateur correspondants aux pseudos
                Utilisateur acquereur = UtilisateurDAO.selectByPseudo(pseudoAcquereur);
                Utilisateur vendeur = UtilisateurDAO.selectByPseudo(pseudoVendeur);

                // Créer l'objet Retrait
                Retrait retrait = new Retrait(rueRetrait, codePostalRetrait, villeRetrait);

                // Créer l'objet Categorie
                Categorie categorieArt = new Categorie();
                categorieArt.setLibelle(categorie);

                // Créer l'objet ArticleVendu
                article = new ArticleVendu(nomArticle, description, categorieArt, prixVente, acquereur,
	                        prixInitial, dateFinEncheres, retrait, vendeur);
	            }
	    } catch (SQLException e) {
			e.printStackTrace();
	    } finally {
	    	if (rs != null) {
	    		try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}		
	    	}if (pstmt != null) {
	        	try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    	}if (cnx != null) {
	        	try {
					cnx.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}}	
			}
	    return article;
	    }


	@Override
	public List<ArticleVendu> selectAllArticles() throws BusinessException {

		// code Greg 
		
		ArrayList<ArticleVendu> listeArticlesEnVente = new ArrayList<>(); 
			
		ArticleVendu articlesVendus= null; 
		Connection cnx = null;
		
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_ARTICLE);
			ResultSet rst = pstmt.executeQuery();
			while (rst.next()) {
				articlesVendus = new ArticleVendu(
						rst.getInt(1), //noArticle
						rst.getString(2), //nom article
						rst.getString(3), // description
						rst.getDate(4).toLocalDate(), // datedebut
						rst.getDate(5).toLocalDate(), //datefin
						rst.getInt(6), //prix
						rst.getInt(7), // prix 
						rst.getString(10) // état vente 
						//(Categorie)rst.getObject(9)

						);
												
				listeArticlesEnVente.add(articlesVendus);
			}
			System.out.println("-------->"+listeArticlesEnVente);

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(cnx !=null) {
				try {
					cnx.close();
				} catch (SQLException e) {
					e.printStackTrace();
					BusinessException businessException = new BusinessException();
					businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_ECHEC);
				}
			}
			
		}
		return listeArticlesEnVente;

	}
		


	@Override
	public List<ArticleVendu> selectAllArticlesByUtilisateur(Utilisateur utilisateur) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ArticleVendu> selectAllArticlesByCategorie(Utilisateur utilisateur) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArticleVendu insertArticle(ArticleVendu article) throws BusinessException {
		Connection cnt;
		
		try {
			cnt=ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnt.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1,article.getNomArticle());
			pstmt.setString(2,article.getDescription());
			pstmt.setDate(3,java.sql.Date.valueOf(article.getDateDebutEncheres()));
			pstmt.setDate(4,java.sql.Date.valueOf(article.getDateFinEncheres()));
			pstmt.setInt(5,article.getPrixInitial());
			pstmt.setInt(6, article.getVendeur().getNoUtilisateur());
			pstmt.setInt(7,article.getCategorie().getId());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				article.setNoArticle(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return article;
	}

	@Override
	public void updateArticle(ArticleVendu updateArticle) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteArticleById(ArticleVendu article) throws BusinessException {
		// TODO Auto-generated method stub
		
	}
	
	//A faire
	@Override
	public List<ArticleVendu> selectAllArticlesByNoCategorie(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleVendu> selectAllArticlesByNoUtilisateur(int noUtilisateur) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ArticleVendu> selectAllVentesEnCoursByNoCategorie(int id) throws BusinessException {
	    List<ArticleVendu> ventes = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_EC_BY_CATEGORIE_ID);
	        pstmt.setInt(1, id);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            ArticleVendu vente = new ArticleVendu();
	            vente.setNoArticle(rs.getInt("id_article"));
	            vente.setNomArticle(rs.getString("nom_article"));
	            vente.setDescription(rs.getString("description"));
	            vente.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
	            vente.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
	            vente.setPrixInitial(rs.getInt("prix_initial"));
	            vente.setPrixVente(rs.getInt("prix_vente"));
	            vente.setEtatVente(rs.getString("etat_vente"));

	            // Récupérer le vendeur
	            Utilisateur vendeur = new Utilisateur();
	            vendeur.setPseudo(rs.getString("vendeur"));
	            vente.setVendeur(vendeur);

	            // Récupérer le meilleur enchérisseur
	            if (rs.getInt("id_enchere_max") != 0) {
	                Utilisateur encherisseur = new Utilisateur();
	                encherisseur.setPseudo(rs.getString("pseudo_encherisseur"));
	                vente.setVendeur(encherisseur);
	            }

	            // Récupérer la catégorie
	            Categorie categorie = new Categorie();
	            categorie.setId(rs.getInt("id_categorie"));
	            categorie.setLibelle(rs.getString("categorie_libelle"));
	            vente.setCategorie(categorie);

	            ventes.add(vente);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_VENTE_EC_BY_CATEGORIE_ID_ECHEC);
	    } finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_RESULTSET_ECHEC);
	            }
	        }
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_PREPAREDSTATEMENT_ECHEC);
	            }
	        }
	        if (cnx != null) {
	            try {
	                cnx.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_ECHEC);
	            }
	        }
	    }
	    return ventes;
	}
	
	@Override
	public List<ArticleVendu> selectAllVentesNonCommenceesByNoUtilisateur(int id) throws BusinessException {
	    List<ArticleVendu> articles = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_NC_BY_UTILISATEUR_ID);
	        pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int articleId = rs.getInt("id_article");
	            int utilisateurId = rs.getInt("id_utilisateur");
	            String vendeurPseudo = rs.getString("vendeur");
	            int categorieId = rs.getInt("id_categorie");
	            String categorieLibelle = rs.getString("categorie_libelle");
	            String nomArticle = rs.getString("nom_article");
	            String description = rs.getString("description");
	            LocalDate dateDebutEncheres = rs.getDate("date_debut_encheres").toLocalDate();
	            LocalDate dateFinEncheres = rs.getDate("date_fin_encheres").toLocalDate();
	            int prixInitial = rs.getInt("prix_initial");
	            int meilleurEncherisseurId = rs.getInt("id_meilleur_encherisseur");
	            String pseudoEncherisseur = rs.getString("pseudo_encherisseur");
	            int montant_enchere = rs.getInt("montant_enchere");
	            String etatVente = rs.getString("etat_vente");

	            // Créer l'objet Vendeur
	            Utilisateur vendeur = new Utilisateur();
	            vendeur.setNoUtilisateur(utilisateurId);
	            vendeur.setPseudo(vendeurPseudo);

	            // Créer l'objet Encherisseur
	            Utilisateur encherisseur = new Utilisateur();
	            encherisseur.setNoUtilisateur(meilleurEncherisseurId);
	            encherisseur.setPseudo(pseudoEncherisseur);

	            // Créer l'objet Categorie
	            Categorie categorie = new Categorie();
	            categorie.setId(categorieId);
	            categorie.setLibelle(categorieLibelle);

	            // Créer l'objet ArticleVendu
	            ArticleVendu article = new ArticleVendu(articleId, nomArticle, description, dateDebutEncheres, dateFinEncheres,
	                    prixInitial, montant_enchere, encherisseur, categorie, etatVente, vendeur);
	            articles.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_VENTE_NC_BY_UTILISATEUR_ID_ECHEC);
	    } finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_RESULTSET_ECHEC);
	            }
	        }
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_PREPAREDSTATEMENT_ECHEC);
	            }
	        }
	        if (cnx != null) {
	            try {
	                cnx.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_ECHEC);
	            }
	        }
	    }

	    return articles;
	}
	
	@Override
	public List<ArticleVendu> selectAllVentesEnCoursByNoUtilisateur(int id) throws BusinessException {
	    List<ArticleVendu> articles = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID);
	        pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int articleId = rs.getInt("id_article");
	            int utilisateurId = rs.getInt("id_utilisateur");
	            String vendeurPseudo = rs.getString("vendeur");
	            int categorieId = rs.getInt("id_categorie");
	            String categorieLibelle = rs.getString("categorie_libelle");
	            String nomArticle = rs.getString("nom_article");
	            String description = rs.getString("description");
	            LocalDate dateDebutEncheres = rs.getDate("date_debut_encheres").toLocalDate();
	            LocalDate dateFinEncheres = rs.getDate("date_fin_encheres").toLocalDate();
	            int prixInitial = rs.getInt("prix_initial");
	            int meilleurEncherisseurId = rs.getInt("id_meilleur_encherisseur");
	            String pseudoEncherisseur = rs.getString("pseudo_encherisseur");
	            int montant_enchere = rs.getInt("montant_enchere");
	            String etatVente = rs.getString("etat_vente");

	            // Créer l'objet Vendeur
	            Utilisateur vendeur = new Utilisateur();
	            vendeur.setNoUtilisateur(utilisateurId);
	            vendeur.setPseudo(vendeurPseudo);

	            // Créer l'objet Encherisseur
	            Utilisateur encherisseur = new Utilisateur();
	            encherisseur.setNoUtilisateur(meilleurEncherisseurId);
	            encherisseur.setPseudo(pseudoEncherisseur);

	            // Créer l'objet Categorie
	            Categorie categorie = new Categorie();
	            categorie.setId(categorieId);
	            categorie.setLibelle(categorieLibelle);

	            // Créer l'objet ArticleVendu
	            ArticleVendu article = new ArticleVendu(articleId, nomArticle, description, dateDebutEncheres, dateFinEncheres,
	                    prixInitial, montant_enchere, encherisseur, categorie, etatVente, vendeur);
	            articles.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID_ECHEC);
	    } finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_RESULTSET_ECHEC);
	            }
	        }
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_PREPAREDSTATEMENT_ECHEC);
	            }
	        }
	        if (cnx != null) {
	            try {
	                cnx.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_ECHEC);
	            }
	        }
	    }

	    return articles;
	}
	
	@Override
	public List<ArticleVendu> selectAllVentesTermineesByNoUtilisateur(int id) throws BusinessException {
	    List<ArticleVendu> articles = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_VE_BY_UTILISATEUR_ID);
	        pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int articleId = rs.getInt("id_article");
	            int utilisateurId = rs.getInt("id_utilisateur");
	            String vendeurPseudo = rs.getString("vendeur");
	            int categorieId = rs.getInt("id_categorie");
	            String categorieLibelle = rs.getString("categorie_libelle");
	            String nomArticle = rs.getString("nom_article");
	            String description = rs.getString("description");
	            LocalDate dateDebutEncheres = rs.getDate("date_debut_encheres").toLocalDate();
	            LocalDate dateFinEncheres = rs.getDate("date_fin_encheres").toLocalDate();
	            int prixInitial = rs.getInt("prix_initial");
	            int meilleurEncherisseurId = rs.getInt("id_meilleur_encherisseur");
	            String pseudoEncherisseur = rs.getString("pseudo_encherisseur");
	            int montant_enchere = rs.getInt("montant_enchere");
	            String etatVente = rs.getString("etat_vente");

	            // Créer l'objet Vendeur
	            Utilisateur vendeur = new Utilisateur();
	            vendeur.setNoUtilisateur(utilisateurId);
	            vendeur.setPseudo(vendeurPseudo);

	            // Créer l'objet Encherisseur
	            Utilisateur encherisseur = new Utilisateur();
	            encherisseur.setNoUtilisateur(meilleurEncherisseurId);
	            encherisseur.setPseudo(pseudoEncherisseur);

	            // Créer l'objet Categorie
	            Categorie categorie = new Categorie();
	            categorie.setId(categorieId);
	            categorie.setLibelle(categorieLibelle);

	            // Créer l'objet ArticleVendu
	            ArticleVendu article = new ArticleVendu(articleId, nomArticle, description, dateDebutEncheres, dateFinEncheres,
	                    prixInitial, montant_enchere, encherisseur, categorie, etatVente, vendeur);
	            articles.add(article);
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
    		BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_VENTE_EC_BY_UTILISATEUR_ID_ECHEC);
	    } finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_RESULTSET_ECHEC);
	            }
	        }
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_PREPAREDSTATEMENT_ECHEC);
	            }
	        }
	        if (cnx != null) {
	            try {
	                cnx.close();
	            } catch (SQLException e) {
	            	e.printStackTrace();
	        		BusinessException businessException = new BusinessException();
	    			businessException.ajouterErreur(CodesResultatDAL.DECONNEXION_ECHEC);
	            }
	        }
	    }

	    return articles;
	}

	@Override
	public List<ArticleVendu> selectAllVentesEnCours() throws BusinessException {
		List<ArticleVendu> articles = new ArrayList<>();
	    Connection cnx = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        cnx = ConnectionProvider.getConnection();
	        pstmt = cnx.prepareStatement(SELECT_ALL_VENTE_EC);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int noArticle = rs.getInt("id_article");
	            int idUtilisateur = rs.getInt("id_utilisateur");
	            int idCategorie = rs.getInt("id_categorie");
	            String categorieLibelle = rs.getString("categorie_libelle");
	            String nomArticle = rs.getString("nom_article");
	            String description = rs.getString("description");
	            LocalDate dateDebutEncheres = rs.getDate("date_debut_encheres").toLocalDate();
	            LocalDate dateFinEncheres = rs.getDate("date_fin_encheres").toLocalDate();
	            int prixInitial = rs.getInt("prix_initial");
	            int prixVente = rs.getInt("prix_vente");
	            int idEnchereMax = rs.getInt("id_enchere_max");
	            int enchIdArticle = rs.getInt("ench_idArticle");
	            int idMeilleurEncherisseur = rs.getInt("id_meilleur_encherisseur");
	            String pseudoEncherisseur = rs.getString("pseudo_encherisseur");
	            LocalDate dateEnchere = rs.getDate("date_enchere").toLocalDate();
	            int montantEnchere = rs.getInt("montant_enchere");
	            String etatVente = rs.getString("etat_vente");

	         // Créer l'objet Vendeur
	            Utilisateur vendeur = new Utilisateur();
	            vendeur.setNoUtilisateur(idUtilisateur);
	            vendeur.setPseudo("vendeur");

	            // Créer l'objet Encherisseur
	            Utilisateur encherisseur = new Utilisateur();
	            encherisseur.setNoUtilisateur(idMeilleurEncherisseur);
	            encherisseur.setPseudo(pseudoEncherisseur);

	            // Créer l'objet Categorie
	            Categorie categorie = new Categorie();
	            categorie.setId(idCategorie);
	            categorie.setLibelle(categorieLibelle);

	            ArticleVendu venteEnCours = new ArticleVendu(noArticle, nomArticle, description, dateDebutEncheres,
	                    dateFinEncheres, prixInitial, prixVente, encherisseur, categorie, etatVente, vendeurUtilisateur);
	            venteEnCours.setIdEnchereMax(idEnchereMax);
	            venteEnCours.setEnchIdArticle(enchIdArticle);
	            venteEnCours.setDateEnchere(dateEnchere);
	            venteEnCours.setMontantEnchere(montantEnchere);

	            ventesEnCours.add(venteEnCours);
	        }
	    } catch (SQLException e) {
	        // Gérer les exceptions ou les propager avec throws BusinessException
	    }

	    return ventesEnCours;
	}

	private ArticleVendu BuilderArticleVenduPourListe (ResultSet rs) throws SQLException {
		
	    ArticleVendu article = new ArticleVendu();

	    article.setNoArticle(rs.getInt("no_article"));
	    article.setNomArticle(rs.getString("nom_article"));
	    article.setDescription(rs.getString("description"));
	    article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
	    article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
	    article.setPrixInitial(rs.getInt("prix_initial"));
	    article.setPrixVente(rs.getInt("prix_vente"));
	 // Récupérer l'utilisateur (vendeur) à partir de la base de données
	    int noUtilisateur = rs.getInt("no_utilisateur");
	    Utilisateur vendeur = utilisateurDAO.selectById(noUtilisateur);
	    article.setVendeur(vendeur);
	    article.setNoCategorie(rs.getInt("no_categorie"));

	    return article;
	}
}
