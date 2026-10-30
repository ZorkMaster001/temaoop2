# Deschidere in IntelliJ IDEA

1. File > Open > selecteaza folderul proiect_2 sau pom.xml.
2. Daca IntelliJ intreaba, alege importul ca proiect Maven.
3. File > Project Structure > Project > SDK: alege un JDK 17 sau mai nou.
   Daca nu ai unul, Add SDK > JDK si selecteaza tools/jdk din acest proiect
   (dupa descarcarea JDK-ului portabil), sau Download JDK > versiunea 17.
4. Lasa IntelliJ sa sincronizeze Maven. Dependenta sqlite-jdbc se descarca automat.
5. Deschide src/main/java/ro/umfst/biblioteca/App.java.
6. Apasa triunghiul verde de langa main > Run App.main().
7. In Run > Edit Configurations verifica Working directory: folderul proiect_2.
   Astfel, baza SQLite se salveaza in proiect_2/data/biblioteca.db.

Nu trebuie sa creezi manual baza de date, tabele sau un server SQL.
Daca apar importuri rosii, foloseste Reload All Maven Projects si verifica Project SDK.

## Test de integrare in IntelliJ
Deschide src/test/java/ro/umfst/biblioteca/IntegrationTest.java si ruleaza metoda main.
Se foloseste o baza temporara, care nu modifica datele aplicatiei.