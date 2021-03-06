-- Teil 1 --

SELECT COUNT(*) AS "Anzahl", a.Ort
FROM Ferienwohnung f
INNER JOIN Adresse a ON a.AdressID = f.AdressID
GROUP BY a.Ort;

-- Teil 2 --

SELECT f.name
FROM Bewertung bw
INNER JOIN Buchung b ON b.BuchungsNR = bw.BuchungsNR
INNER JOIN Ferienwohnung f ON b.WohnungsID = f.WohnungsID
INNER JOIN Adresse a ON f.AdressID = a.AdressID
WHERE a.Landname = 'Spanien' 
GROUP BY f.name
HAVING AVG(bw.Sterne) > 4;

-- Teil 3 --

SELECT COUNT(*) AS "Anzahl"
FROM Ferienwohnung f
WHERE f.WohnungsID NOT IN ( SELECT b.WohnungsID FROM Buchung b);

-- Teil 4 --

CREATE VIEW ANZAUS ( WohnungsID, Ausstattungsanzahl ) AS
SELECT fw.WohnungsID, COUNT(*)
FROM FW_HAT_AUS fw
GROUP BY fw.WohnungsID;

SELECT a.WohnungsID
FROM ANZAUS a
WHERE a.Ausstattungsanzahl = (SELECT MAX(Ausstattungsanzahl) from ANZAUS);

-- Teil 5 --

SELECT l.Name, NVL(COUNT(b.BuchungsNR),0) AS "Anzahl Buchungen pro Land"
FROM Land l
LEFT JOIN Adresse a  ON a.Landname = l.Name
LEFT JOIN Ferienwohnung f ON f.AdressID = a.AdressID
LEFT JOIN Buchung b ON f.WohnungsID = b.WohnungsID
GROUP BY l.Name
ORDER BY NVL(COUNT(b.BuchungsNR),0) DESC;

-- Teil 6 --

SELECT f.Name, AVG(bw.Sterne) AS "durchschnittliche Bewertung"
FROM dbsys13.Bewertung bw
INNER JOIN dbsys13.Buchung b ON b.BuchungsNR = bw.BuchungsNR
RIGHT JOIN dbsys13.Ferienwohnung f ON b.WohnungsID = f.WohnungsID
INNER JOIN dbsys13.Adresse a ON f.AdressID = a.AdressID
WHERE a.Landname = 'Spanien' AND f.WohnungsID NOT IN 
    (SELECT b.WohnungsID
     FROM dbsys13.Buchung b 
     WHERE b.Anfangsdatum BETWEEN '04/06/2020' AND  '07/06/2020'
     OR  b.Enddatum BETWEEN '04/06/2020' AND '07/06/2020'
     OR b.Anfangsdatum < '04/06/2020' AND b.Enddatum > '07/06/2020'
    )
--AND f.WohnungsID IN (SELECT fw.WohnungsID FROM FW_HAT_AUS fw WHERE fw.Ausstattungsname = 'Sauna')
GROUP BY f.Name
ORDER BY AVG(bw.Sterne) DESC;


