/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Mirado
 * Created: 20 nov. 2024
 */

CREATE OR REPLACE VIEW v_total_machine AS
SELECT id_machine,SUM(prix_revient_theorique) as totaltheorique, SUM(prix_revient_pratique) as totalpratique, SUM(longueur*largeur*epaisseur) as volume
FROM bloc GROUP BY id_machine;

CREATE OR REPLACE VIEW v_perte_machine AS
SELECT id_machine,totaltheorique,totalpratique,totaltheorique-totalpratique as perte,volume
FROM v_total_machine ORDER BY perte;

/*Fonctions par date*/

CREATE OR REPLACE FUNCTION f_perte_machine_by_year(annee INTEGER)
RETURNS TABLE (
    id_machine VARCHAR(10),
    totaltheorique DOUBLE PRECISION,
    totalpratique DOUBLE PRECISION,
    perte DOUBLE PRECISION,
    volume DOUBLE PRECISION
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        subquery.id_machine,
        subquery.totaltheorique,
        subquery.totalpratique,
        subquery.totaltheorique - subquery.totalpratique AS perte,
        subquery.volume
    FROM (
        SELECT 
            bloc.id_machine,
            SUM(prix_revient_theorique) AS totaltheorique,
            SUM(prix_revient_pratique) AS totalpratique,
            SUM(longueur * largeur * epaisseur) AS volume
        FROM 
            bloc
        WHERE 
            EXTRACT(YEAR FROM daty_creation) = annee
        GROUP BY 
            bloc.id_machine
    ) AS subquery
    ORDER BY 
        perte;
END;
$$ LANGUAGE plpgsql;