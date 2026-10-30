# Biblioteca — Proiect II, Programare orientata pe obiecte

Aplicatie desktop in **Java Swing**, cu baza de date **SQLite** accesata prin JDBC.
Nu necesita server SQL. Datele sunt pastrate local in data/biblioteca.db.

## Pornire
Dublu click pe **Porneste.bat**. Scriptul compileaza sursele si porneste aplicatia.
JDK 17 portabil in tools/jdk; bibliotecile JDBC sunt in lib. Nu este necesar Maven.
Pentru verificari: **Testeaza.bat**.

## Utilizare
1. Apasa + Adauga si alege Carte sau Revista.
2. Completeaza titlul, autorul/editorul, anul si genul literar sau numarul revistei.
3. Foloseste cautarea si filtrul de stare; coloanele pot fi sortate.
4. Selecteaza o publicatie pentru editare, imprumut/returnare sau stergere.
5. Publicatiile imprumutate trebuie returnate inainte de stergere.
6. Inchide si redeschide aplicatia: datele si starea imprumuturilor se pastreaza.

Catalogul porneste gol. Termenul de imprumut este o regula demonstrativa (21 zile
pentru carti, 7 pentru reviste). Nu sunt gestionate persoane, scadente sau penalitati.

## Cerinte laborator
| Cerinta | Implementare |
|---|---|
| GUI Java Swing | MainFrame, JTable, butoane, cautare, filtre |
| Minim 2 ferestre/JPanel | MainFrame (JFrame), PublicatieDialog (JDialog), StatisticiPanel (JPanel), panoul catalogului |
| Ferestre modale JOptionPane | Confirmare imprumut, stergere, erori, validari, Despre |
| Minim 2 interfete/clase abstracte | Publicatie (abstracta), Repository<T> (interfata) |
| Minim 6 clase | 10 clase de productie + o interfata |
| 2 clase cu mostenire, override, super | Carte si Revista extind Publicatie; super(...) si super.toString(); getTip/getDurataImprumut/getDetaliu suprascrise |
| Minim 3 pachete imbricate | ro.umfst.biblioteca.model, .persistence, .ui; App in ro.umfst.biblioteca |
| Constructori supraincarcati | Publicatie, Carte, Revista, PublicatieDialog |
| get/set | Modelele Publicatie, Carte si Revista |
| toString, equals | Publicatie; toString extins in ambele subclase; hashCode consistent cu equals |
| Structuri de date | List<Publicatie>, ArrayList, String[] pentru coloane |
| Persistenta SQL | Database si SqlitePublicatieRepository; SQLite in data/biblioteca.db |

## Structura
- model: entitati, validare si polimorfism.
- persistence: contract Repository, conexiune JDBC, operatii SQL parametrizate.
- ui: fereastra principala, editor modal, statistici si modelul tabelului.
- sql/schema.sql: schema SQL de referinta; creata automat de Database.
- src/test: test de integrare cu baza temporara, separat de datele aplicatiei.

## Explicatii pentru prezentare
Publicatie este abstracta fiindca un obiect concret trebuie sa fie Carte sau Revista.
Repository separa accesul la date de interfata grafica. Aceeasi lista de Publicatie
contine ambele subtipuri; tabelul apeleaza getDurataImprumut fara sa stie subtipul
(polimorfism). equals compara ID-ul stabil, iar hashCode respecta acelasi criteriu.
Constructorii subclaselor folosesc super pentru a initializa partea comuna.
PreparedStatement transmite valorile separat de SQL, inclusiv titluri cu apostrof.
try-with-resources inchide conexiunile si rezultatele SQL.

## Verificare
Testeaza.bat verifica inserare, editare, stergere, persistenta dupa reconectare,
imprumut/returnare, validare, equals/hashCode, polimorfism, modelul tabelului si
constructia ferestrelor Swing. Testul nu modifica baza aplicatiei.

## Dependente
- Eclipse Temurin JDK 17 (GPLv2 + Classpath Exception), distributie portabila.
- org.xerial:sqlite-jdbc:3.46.1.0 (Apache-2.0 / BSD pentru componentele SQLite).
- org.slf4j:slf4j-api:2.0.13 si slf4j-nop:2.0.13 (MIT).
Pastreaza fisierele de licenta din distributia JDK la redistribuire.
## IntelliJ IDEA
Deschide pom.xml ca proiect Maven. Vezi INTELLIJ.md pentru configurare si rulare.

## GitHub
Repository: https://github.com/ZorkMaster001/temaoop2
JDK-ul portabil din tools este disponibil doar in copia locala, fiind exclus din Git.
Dupa clonare, foloseste IntelliJ cu JDK 17+ si import Maven, sau configureaza JAVA_HOME
pentru scripturile .bat. Bibliotecile din lib permit rularea scripturilor fara Maven.
