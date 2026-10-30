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
## Prezentare pe alt calculator
1. Prima data cloneaza repository-ul; daca exista deja copia, foloseste Git Pull.
2. Deschide folderul proiectului in IntelliJ.
3. In File > Project Structure > Project SDK selecteaza JDK 17 sau mai nou.
   Numele SDK-ului poate fi diferit pe laptop; nu trebuie sa fie exact temurin-17.
4. Alege configuratia Biblioteca si apasa Run.

Bibliotecile SQLite sunt in lib si vin prin Git. Nu trebuie instalat un server SQL.
La prima pornire se creeaza automat data/biblioteca.db si tabela publicatii.
Baza locala nu este urmarita in Git: pe un laptop nou catalogul porneste gol.
Pentru aceleasi carti si reviste, inchide aplicatia pe ambele calculatoare si copiaza
fisierul data/biblioteca.db de pe acest calculator in folderul data al proiectului
de pe laptop, inainte de prezentare. Daca folderul data lipseste, creeaza-l.
Copierea peste o baza existenta inlocuieste datele acelei baze; pastreaza o copie
daca ai deja inregistrari pe laptop.
