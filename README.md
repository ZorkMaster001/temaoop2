# Biblioteca — Proiect II OOP

## Pornire
In IntelliJ: Open > folderul proiect_2. Asteapta indexarea, apoi alege Biblioteca
din bara de sus si apasa Run. Sau deschide src/Main.java si ruleaza metoda main.
Necesita JDK 17 sau mai nou, selectat in File > Project Structure > Project SDK.


## Foldere
- src: codul Java al proiectului.
- lib: cele 3 biblioteci JAR necesare pentru SQLite; nu sunt arhive de test.
- data: baza de date SQLite, salvata automat.
- .idea si Biblioteca.iml: configuratia IntelliJ.

## Functii
Carti si reviste; adaugare, editare, cautare, filtre, imprumut/returnare,
stergere cu confirmare si statistici. Datele raman dupa inchidere.
Termene demonstrative: 21 zile pentru carti si 7 zile pentru reviste.
Nu se gestioneaza persoane sau scadente calendaristice.

## Cerinte OOP
| Cerinta | Implementare |
|---|---|
| Swing si mai multe ferestre/panouri | MainFrame, PublicatieDialog, StatisticiPanel |
| JOptionPane modal | Confirmari, erori, validari, Despre |
| 2 interfete/clase abstracte | Publicatie abstracta si Repository interfata |
| Minim 6 clase | 11 clase si o interfata |
| Mostenire, suprascriere si super | Carte si Revista extind Publicatie |
| Pachete imbricate | ro.umfst.biblioteca.model, persistence si ui |
| Constructori supraincarcati | Publicatie, Carte, Revista, PublicatieDialog |
| get/set, toString, equals | Clasele din model; hashCode consistent cu equals |
| Structuri de date | List, ArrayList, String[] |
| SQL persistent | SQLite prin Database si SqlitePublicatieRepository |

Polimorfism: tabelul apeleaza getDurataImprumut pentru ambele subtipuri.
Carte si Revista folosesc super in constructor si in toString.
equals compara ID-ul stabil. PreparedStatement separa valorile de comanda SQL.
Conexiunile sunt inchise prin try-with-resources.

GitHub: https://github.com/ZorkMaster001/temaoop2