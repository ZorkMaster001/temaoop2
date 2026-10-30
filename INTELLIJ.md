# Ruleaza din IntelliJ

1. Inchide proiectul deschis: File > Close Project.
2. Open > selecteaza folderul D:/Facultate/OOP - anul 2/proiect_2.
3. Deschide src/Main.java si apasa triunghiul verde de langa main.
   Poti alege si configuratia Biblioteca din partea de sus si apasa Run.

Proiectul este Java simplu. Nu foloseste Maven, pom.xml sau descarcari de dependente.
SQLite este deja inclus si legat la modulul Biblioteca, prin fisierele din lib.

JDK: este configurat temurin-17, cel selectat deja pe acest calculator.
Daca IntelliJ cere alt SDK: File > Project Structure > Project > SDK > Add JDK,
apoi selecteaza tools/jdk din folderul proiectului.

Alternativa: dublu click pe Porneste.bat.
Datele se salveaza automat in data/biblioteca.db si raman dupa inchidere.
Nu trebuie sa pornesti un server SQL sau sa creezi manual tabele.

Teste: dublu click pe Testeaza.bat sau ruleaza metoda main din
tests/ro/umfst/biblioteca/IntegrationTest.java.