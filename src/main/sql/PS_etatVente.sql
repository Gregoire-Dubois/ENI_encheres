-- PS pour passer l'état de la vente à Non Commencé si enregistrement à NULL (uniquement en phase de test) 
CREATE OR ALTER PROCEDURE etatNonCommence
AS
DECLARE @date_debut date
DECLARE date_begin_cursor CURSOR FOR
SELECT date_debut_encheres
FROM ARTICLES_VENDUS
WHERE etat_vente is NULL ;

OPEN date_begin_cursor
FETCH NEXT FROM date_begin_cursor
    INTO @date_debut

WHILE @@FETCH_STATUS = 0
BEGIN
    IF @date_debut >= GETDATE()
        BEGIN
            UPDATE ARTICLES_VENDUS SET etat_vente = 'NC' WHERE date_debut_encheres = @date_debut
        END
    FETCH NEXT FROM date_begin_cursor
        INTO @date_debut
END
CLOSE date_begin_cursor
DEALLOCATE date_begin_cursor
GO

-- PS pour passer l'état de la vente à en cours
CREATE OR ALTER PROCEDURE etatCommence
AS
DECLARE @date_debut date
DECLARE date_begin_cursor CURSOR FOR
SELECT date_debut_encheres
FROM ARTICLES_VENDUS
WHERE etat_vente = 'NC' or etat_vente is NULL ;

OPEN date_begin_cursor
FETCH NEXT FROM date_begin_cursor
    INTO @date_debut

WHILE @@FETCH_STATUS = 0
BEGIN
    IF @date_debut <= GETDATE()
        BEGIN
            UPDATE ARTICLES_VENDUS SET etat_vente = 'EC' WHERE date_debut_encheres = @date_debut
        END
    FETCH NEXT FROM date_begin_cursor
        INTO @date_debut
END
CLOSE date_begin_cursor
DEALLOCATE date_begin_cursor
GO


--PS pour passer le statut à vendu lorsque la date de fin d'enchères est arrivée
CREATE OR ALTER PROCEDURE etatVendu
AS
DECLARE @date_fin date
DECLARE date_end_cursor CURSOR FOR
    SELECT date_fin_encheres
    FROM ARTICLES_VENDUS
    WHERE etat_vente = 'EC';

    OPEN date_end_cursor
    FETCH NEXT FROM date_end_cursor
        INTO @date_fin

    WHILE @@FETCH_STATUS = 0
    BEGIN
        IF @date_fin <= GETDATE()
            BEGIN
                UPDATE ARTICLES_VENDUS SET etat_vente = 'VE' WHERE date_fin_encheres = @date_fin
            END
        FETCH NEXT FROM date_end_cursor
            INTO @date_fin
    END
    CLOSE date_end_cursor
    DEALLOCATE date_end_cursor
GO