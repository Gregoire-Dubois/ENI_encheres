package fr.eni.enienchere.bo;

import java.util.ArrayList;
import java.util.List;

public class Categorie {

    private int id;
    private String libelle;
    
    private List<ArticleVendu> listeArticles; //Pour pouvoir afficher les éléments de chaque catégorie? 
    //CCN : toutes les listes  / filtres obtenues par sélection de critères seront gérées dans Article. Non ?  
    
    public Categorie() {
		listeArticles=new ArrayList<>();
	}

    public Categorie(String libelle) {
        this.libelle = libelle;
        listeArticles=new ArrayList<>();
    }
//    
//    public Categorie() {}
//
//    public Categorie(String libelle) {
//        this.libelle = libelle;
//    }

    public Categorie(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    public List<ArticleVendu> getListeArticles() {
		return listeArticles;
	}
    
    public void ajouterArticle(ArticleVendu article) {
    	if(article.getCategorie().equals(this) && !listeArticles.contains(article))
		{
			this.listeArticles.add(article);
		}
		else
		{
			System.out.println("ajout impossible");
		}
    }
    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
