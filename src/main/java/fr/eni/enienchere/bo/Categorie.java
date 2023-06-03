package fr.eni.enienchere.bo;

import java.util.List;

public class Categorie {

    private int id;
    private String libelle;
    
    //private List<ArticleVendu> listeArticles; //Pour pouvoir afficher les éléments de chaque catégorie?

    public Categorie() {}

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

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

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
