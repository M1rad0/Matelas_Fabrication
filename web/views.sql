/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Mirado
 * Created: 20 nov. 2024
 */

CREATE OR REPLACE VIEW v_total_machine AS
SELECT id_machine,SUM(prix_revient_theorique) as totaltheorique, SUM(prix_revient_pratique) as totalpratique
FROM bloc GROUP BY id_machine;

CREATE OR REPLACE VIEW v_perte_machine AS
SELECT id_machine,totaltheorique,totalpratique,totaltheorique-totalpratique as perte
FROM v_total_machine ORDER BY perte;