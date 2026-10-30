# Biblioteca — Proiect II OOP

Aplicatie Java Swing cu baza de date SQLite, pentru gestiunea cartilor si revistelor.
Proiect Java simplu, fara Maven sau pom.xml.

## Pornire
Deschide folderul proiect_2 in IntelliJ, apoi src/Main.java si apasa Run.
Configuratia Biblioteca include deja bibliotecile SQLite din lib.
Daca proiectul era deschis inainte de modificari, inchide-l si redeschide folderul.
Vezi INTELLIJ.md. Poti folosi si Porneste.bat.

## Functii
- Adaugare si editare de carti si reviste.
- Cautare dupa titlu, autor, tip si gen/numar; filtrare dupa disponibilitate.
- Imprumut si returnare, cu confirmare.
- Stergere cu confirmare; o publicatie imprumutata trebuie returnata intai.
- Statistici si salvare automata in data/biblioteca.db.
Termenul demonstrativ este 21 zile pentru carti si 7 zile pentru reviste.
Aplicatia retine starea imprumutului, fara persoane sau calendar de scadente.

## Cerinte laborator
| Cerinta | Unde este indeplinita |
|---|---|
| Java Swing | MainFrame, PublicatieDialog, StatisticiPanel, JTable |
| Minim 2 ferestre/panouri | JFrame principal, JDialog editor si mai multe JPanel |
| JOptionPane modal | Confirmari, validare, erori si Despre |
| Minim 2 interfete/clase abstracte | Publicatie abstracta si interfata Repository |
| Minim 6 clase | 11 clase de productie si o interfata |
| Mostenire, override si super in 2 clase | Carte si Revista extind Publicatie |
| Pachete proprii imbricate | ro.umfst.biblioteca.model, persistence si ui |
| Constructori supraincarcati | Publicatie, Carte, Revista, PublicatieDialog |
| get/set | Publicatie, Carte, Revista |
| toString si equals | Publicatie; toString suprascris cu super in subclase |
| Structuri de date | List, ArrayList si String[] |
| Persistenta SQL | Database si SqlitePublicatieRepository, SQLite local |

## Organizare
src/Main.java este punctul de pornire.
src/ro/umfst/biblioteca/model contine entitatile si validarea.
src/ro/umfst/biblioteca/persistence contine conexiunea JDBC si operatiile SQL.
src/ro/umfst/biblioteca/ui contine ferestrele si tabelul.
sql/schema.sql contine schema de referinta; tabela este creata automat.
tests contine verificarile, care folosesc o baza temporara.

## Explicatii pentru prezentare
Publicatie este abstracta: obiectele concrete sunt Carte sau Revista.
Ambele folosesc super pentru constructor si pentru toString, si suprascriu
getTip, getDetaliu si getDurataImprumut. Tabelul foloseste aceleasi metode pentru
ambele subtipuri: acesta este polimorfismul.
Repository defineste operatiile de acces la date; SqlitePublicatieRepository le implementeaza.
equals compara ID-ul stabil, iar hashCode foloseste acelasi ID.
PreparedStatement trimite valorile separat de comanda SQL.
try-with-resources inchide conexiunile dupa folosire.

## Verificare si dependente
Testeaza.bat verifica operatiile SQL, persistenta, validarea, OOP si ferestrele Swing.
Driver: sqlite-jdbc 3.46.1.0; slf4j-api si slf4j-nop 2.0.13.
JDK 17+ necesar. JDK-ul portabil din tools exista local si nu este incarcat pe GitHub.
Dupa clonare se poate folosi JDK-ul configurat in IntelliJ.
Repository: https://github.com/ZorkMaster001/temaoop2