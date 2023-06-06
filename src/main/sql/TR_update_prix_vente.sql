ALTER TRIGGER [dbo].[TR_update_prix_vente]
ON [dbo].[ENCHERES]
AFTER INSERT, DELETE AS BEGIN 
  UPDATE ARTICLES_VENDUS
  SET prix_vente = (select max(e.montant_enchere) from ENCHERES e where a.no_article =  e.no_article)
  FROM ARTICLES_VENDUS a inner join ENCHERES e on a.no_article = e.no_article
END;