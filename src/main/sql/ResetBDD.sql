DELETE ENCHERES WHERE ID>0;
DBCC CHECKIDENT(ENCHERES,RESEED,0);

DELETE ARTICLES_VENDUS WHERE ID>0;
DBCC CHECKIDENT(ARTICLES,RESEED,0);

DELETE RETRAITS WHERE ID>0;
DBCC CHECKIDENT(RETRAITS,RESEED,0);

DELETE CATEGORIES WHERE ID>0;
DBCC CHECKIDENT(CATEGORIES,RESEED,0);

DELETE UTILISATEURS WHERE ID >0;
DBCC CHECKIDENT(UTILISATEURS,RESEED,0);