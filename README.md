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

---

# Ghid pentru prezentarea la laborator

## Cum pot prezenta proiectul in aproximativ un minut?

„Am realizat o aplicatie de gestionare a unei biblioteci, in Java Swing.
Pot adauga carti si reviste, le pot edita, cauta, imprumuta si returna.
Datele sunt salvate intr-o baza SQLite si raman dupa inchiderea aplicatiei.
Am separat codul in model, persistenta si interfata grafica.
Clasa abstracta Publicatie contine datele comune, iar Carte si Revista o extind.
Am folosit incapsulare, mostenire, abstractizare si polimorfism.
Accesul la baza de date este definit prin interfata Repository.”

## Ce este o clasa? Ce este un obiect?

**Clasa** descrie ce date si ce comportamente au obiectele unui anumit tip.
**Obiectul** este o instanta concreta a clasei, creata cu new.

Exemplu din proiect:

```java
Carte carte = new Carte("Ion", "Liviu Rebreanu", 1920, "Roman");
```

Carte este clasa; obiectul creat reprezinta o anumita carte.
carte este variabila care pastreaza o referinta la acel obiect.

## 1. Incapsularea

**Ce este:** gruparea datelor si a metodelor care le folosesc intr-o clasa,
cu acces controlat la starea interna.

**Unde este:** in Publicatie, campurile titlu, autor, an si imprumutata sunt private.
Alte clase le citesc sau modifica prin metode publice.

```java
private String titlu;

public String getTitlu() {
    return titlu;
}

public void setTitlu(String titlu) {
    this.titlu = textValid(titlu, "Titlul");
}
```

**De ce ajuta:** setTitlu respinge un titlu gol. Daca acel camp ar fi public,
codul din exterior ar putea ocoli verificarea.

**Raspuns scurt:** „Folosesc campuri private si metode de acces, cu validare,
pentru a controla modificarile obiectelor.”

## 2. Mostenirea

**Ce este:** o subclasa preia structura si comportamentul accesibil al unei clase de baza
si poate adauga sau adapta functionalitati. Exprima o relatie de tip „este un”.

```java
public final class Carte extends Publicatie
public final class Revista extends Publicatie
```

O Carte este o Publicatie. O Revista este o Publicatie.
Datele comune sunt definite o singura data in Publicatie.

Campurile private ale clasei de baza nu pot fi accesate direct din subclase.
Subclasele lucreaza cu ele prin constructorul bazei si metodele accesibile.

**Raspuns scurt:** „Carte si Revista extind Publicatie, ca sa refolosesc partea comuna.”

## 3. Abstractizarea

**Ce este:** definirea caracteristicilor si operatiilor esentiale, fara a cere
codului care le foloseste sa cunoasca toate detaliile implementarii.

**Unde este:** Publicatie este abstracta. Are date si metode comune,
dar lasa anumite operatii in responsabilitatea subclaselor:

```java
public abstract String getTip();
public abstract int getDurataImprumut();
public abstract String getDetaliu();
```

Nu pot scrie new Publicatie(...). Pot crea Carte sau Revista.
O clasa abstracta poate avea campuri, constructori si metode deja implementate.

**Raspuns scurt:** „Publicatie descrie partea comuna, iar subclasele completeaza
comportamentul specific.”

## 4. Polimorfismul

**Ce este:** folosirea unei referinte de tip comun pentru obiecte de tipuri diferite.
La apelul unei metode de instanta suprascrise, se executa implementarea obiectului concret.

```java
Publicatie prima = new Carte("Ion", "Liviu Rebreanu", 1920, "Roman");
Publicatie aDoua = new Revista("Stiinta", "Redactia", 2024, 12);

System.out.println(prima.getDurataImprumut());  // 21
System.out.println(aDoua.getDurataImprumut()); // 7
```

In PublicatieTableModel, getValueAt apeleaza p.getDurataImprumut(),
desi variabila p este de tip Publicatie. Nu trebuie sa aleaga manual durata dupa tip.

**Raspuns scurt:** „Aceeasi metoda apelata prin Publicatie are rezultate diferite
pentru Carte si Revista.”

## 5. Suprascriere si supraincarcare

| Concept | Explicatie | Exemplu |
|---|---|---|
| Suprascriere / overriding | O subclasa redefineste o metoda de instanta mostenita, cu semnatura compatibila | getDurataImprumut in Carte si Revista |
| Supraincarcare / overloading | Acelasi nume, dar liste de parametri diferite | Cei doi constructori Carte |

Constructorii pot fi supraincarcati, dar nu sunt mosteniti si nu se suprascriu.
Doar schimbarea tipului returnat nu este suficienta pentru supraincarcare.

**Ce face @Override?** Cere compilatorului sa verifice ca metoda chiar suprascrie
sau implementeaza o metoda existenta. Ajuta la detectarea greselilor de semnatura.

## 6. Constructori, this si super

**Constructorul** initializeaza obiectul. Are numele clasei si nu are tip returnat.

In Carte exista:
- Un constructor pentru o carte noua: genereaza ID si seteaza starea initiala.
- Un constructor cu ID si stare: permite reconstruirea obiectului din baza de date.

**this** desemneaza obiectul curent. In this.titlu = ..., distinge campul de parametru.
**this(...)** apeleaza un alt constructor al aceleiasi clase.

**super(...)** apeleaza constructorul clasei de baza:

```java
super(id, titlu, autor, an, imprumutata);
```

**super.toString()** foloseste implementarea clasei de baza, apoi subclasa
adauga genul cartii sau numarul revistei.

## 7. Interfata si clasa abstracta

**Interfata** defineste un contract. Repository<T> declara operatiile
findAll, save si delete. SqlitePublicatieRepository le implementeaza
pentru Publicatie, folosind SQL.

**Clasa abstracta** poate pastra starea comuna a obiectelor si poate impune
metode abstracte. Publicatie are campuri, constructori si implementari comune.

In acest proiect:
- extends: Carte extinde Publicatie.
- implements: SqlitePublicatieRepository implementeaza Repository<Publicatie>.

O clasa Java poate extinde o singura clasa, dar poate implementa mai multe interfete.
Interfetele pot avea si metode default sau static; nu inseamna ca toate metodele
oricarei interfete Java sunt obligatoriu fara implementare.

**De ce Repository?** Interfata grafica foloseste contractul de acces la date.
Pot introduce o alta implementare, de exemplu pentru fisiere, fara sa rescriu
operatiile din interfata grafica, daca pastrez acelasi contract.

## 8. Modificatori de acces, static si final

| Cuvant | Sens | Exemplu din proiect |
|---|---|---|
| private | Acces direct numai in clasa care declara membrul | campul titlu |
| public | Accesibil din exterior, daca si tipul este accesibil | getTitlu |
| protected | Acces in acelasi pachet si in subclase, cu regulile Java pentru acces intre pachete | constructorii Publicatie, textValid |
| fara modificator | Acces la nivelul aceluiasi pachet | regula generala Java |
| static | Membrul apartine clasei, nu unui obiect anume | main, textValid |
| final pe camp | Campul poate fi atribuit o singura data | id |
| final pe metoda | Metoda nu poate fi suprascrisa | equals, hashCode |
| final pe clasa | Clasa nu poate fi extinsa | Carte, Revista |

Un camp final care contine o referinta nu face automat obiectul referit imuabil.
In proiect, ID-ul este final si nu are setter deoarece identitatea trebuie sa ramana stabila.

## 9. toString, equals, hashCode si ==

**toString()** intoarce o descriere text a obiectului. In subclase este completata
cu detaliile specifice. Tabelul citeste separat proprietatile, nu foloseste toString
pentru a construi toate celulele.

**equals()** stabileste egalitatea logica. In Publicatie compara ID-urile.
Doua carti cu acelasi titlu, dar cu ID-uri diferite, NU sunt egale in acest proiect.
Doua obiecte reconstruite cu acelasi ID sunt considerate aceeasi publicatie.

**==** compara referintele in cazul obiectelor: verifica daca este acelasi obiect.
Pentru tipuri primitive, compara valorile.

**hashCode()** trebuie sa respecte regula: daca a.equals(b) este true,
atunci a.hashCode() si b.hashCode() trebuie sa fie egale.
Obiecte diferite pot avea acelasi hashCode; inversul regulii nu este garantat.
Aceasta regula conteaza pentru HashSet si HashMap, chiar daca aici folosim liste.

## 10. List, ArrayList, tablouri si generice

**List** este o interfata pentru colectii ordonate.
**ArrayList** este o implementare redimensionabila a acestei interfete.

```java
List<Publicatie> result = new ArrayList<>();
```

<Publicatie> precizeaza tipul elementelor: pot pune Publicatie si subtipurile ei.
Un tablou are lungime fixa dupa creare; ArrayList isi poate modifica numarul de elemente.
String[] columns retine numele coloanelor tabelului.

**Ce este T din Repository<T>?** Un parametru de tip generic.
Prin Repository<Publicatie> folosesc acel contract pentru obiecte Publicatie.

## 11. Pachetele si rolul claselor

| Clasa / interfata | Rol |
|---|---|
| Main | Punctul de intrare; apeleaza App |
| App | Configureaza aspectul, baza de date si deschide fereastra |
| Publicatie | Date comune, validare si contractul subtipurilor |
| Carte | Gen literar si durata de 21 zile |
| Revista | Numar revista si durata de 7 zile |
| Repository<T> | Contract pentru accesul la date |
| Database | Conexiune SQLite si creare tabela |
| SqlitePublicatieRepository | Citire, salvare si stergere prin SQL |
| MainFrame | Fereastra principala si actiunile utilizatorului |
| PublicatieDialog | Formular modal de adaugare/editare |
| PublicatieTableModel | Furnizeaza randurile si coloanele pentru JTable |
| StatisticiPanel | Afiseaza totalurile |

Pachetele model, persistence si ui separa responsabilitatile si organizeaza codul.
Aceasta organizare nu creeaza automat mai multe baze de date sau procese.

## 12. Swing si tratarea evenimentelor

**JFrame:** fereastra principala.
**JPanel:** container pentru organizarea componentelor; nu este singur o fereastra independenta.
**JDialog:** fereastra de dialog; editorul din proiect este modal.
**JOptionPane:** dialoguri standard pentru mesaje, erori si confirmari.

**Modal** inseamna ca utilizatorul trebuie sa inchida/rezolve dialogul inainte
sa interactioneze din nou cu fereastra blocata de acesta.

**ActionListener:** reactioneaza la actiuni, de exemplu apasarea unui buton.
Expresia e -> save() este o expresie lambda folosita ca implementare a listenerului.

**De ce SwingUtilities.invokeLater?** Programeaza crearea interfetei pe firul Swing
numit Event Dispatch Thread (EDT). Majoritatea operatiilor cu componente Swing
trebuie facute pe acel fir.

**Ce face fireTableDataChanged?** Anunta JTable ca datele modelului s-au schimbat.

**De ce convertRowIndexToModel?** Dupa sortare/filtrare, pozitia unui rand afisat
poate fi diferita de pozitia sa din model. Conversia ajuta la editarea obiectului corect.

## 13. Baza de date si SQL

**Persistenta** inseamna ca datele supravietuiesc inchiderii programului.
Lista din memorie se pierde la iesire; fisierul SQLite ramane pe disc.

**SQLite** este o baza de date locala, stocata intr-un fisier.
**JDBC** este API-ul Java pentru accesul la baze de date.
Driverul din lib conecteaza JDBC la SQLite.

Tabela publicatii are:
- id: cheie primara, identifica unic publicatia;
- tip: Carte sau Revista;
- titlu, autor, an;
- detaliu: genul cartii sau numarul revistei;
- imprumutata: 0 sau 1.

O singura tabela pastreaza ambele tipuri. La citire, coloana tip decide daca
se construieste un obiect Carte sau Revista. Constructorul cu ID pastreaza
identitatea existenta in baza de date.

**CRUD:** Create = adaugare, Read = citire, Update = modificare, Delete = stergere.
save foloseste INSERT ... ON CONFLICT(id) DO UPDATE:
daca ID-ul este nou insereaza, iar daca exista actualizeaza inregistrarea.

**PreparedStatement:** comanda SQL contine ?, iar valorile se transmit prin setString,
setInt si setBoolean. Evita concatenarea datelor utilizatorului in SQL si permite
titluri cu apostrof, precum O'Brien.

**executeQuery:** pentru interogari care intorc randuri, de exemplu SELECT.
**executeUpdate:** pentru modificari precum INSERT, UPDATE sau DELETE.
**ResultSet:** permite parcurgerea randurilor returnate de o interogare.

**try-with-resources:** inchide automat resursele declarate in try,
inclusiv la aparitia unei exceptii. Nu trebuie sa las conexiunile deschise.

## 14. Exceptii si validare

**Exceptia** semnaleaza o situatie in care operatia nu se poate termina normal.
try contine operatia; catch trateaza un anumit tip de eroare.
throw arunca o exceptie, iar throws declara ca o metoda poate propaga exceptii.

Exemple:
- IllegalArgumentException: titlu gol, an invalid sau numar de revista nepozitiv.
- NumberFormatException: un text nu poate fi convertit intr-un intreg.
- SQLException: eroare de acces la baza de date.

NumberFormatException este subclasa IllegalArgumentException, de aceea este
prinsa prima in PublicatieDialog. Altfel, un catch mai general ar acoperi-o deja.
SQLException este o exceptie verificata de compilator; IllegalArgumentException
este o exceptie de executie (RuntimeException).

Validarea din model se aplica si atunci cand obiectele sunt create fara formular.
Mesajele JOptionPane explica utilizatorului ce trebuie corectat.

## Intrebari suplimentare posibile

**Ai folosit Serializable?**
Nu. Cerinta de persistenta este indeplinita prin SQLite. Nu serializez obiectele Java.

**De ce UUID pentru ID?**
Generez un identificator cu probabilitate foarte mica de coliziune, fara sa folosesc
titlul drept identitate. Baza impune suplimentar unicitatea prin cheia primara.

**Ce se intampla cand inchizi si redeschizi aplicatia?**
Obiectele din memorie sunt recreate din randurile existente in SQLite.

**Ce se intampla daca lipseste fisierul bazei?**
Database creeaza folderul, fisierul si tabela la prima pornire. Catalogul porneste gol.

**De ce nu poti sterge o carte imprumutata?**
MainFrame verifica starea si cere returnarea inainte de stergere.
Este o regula a interfetei aplicatiei; repository-ul nu impune separat aceasta regula.

**Imprumutul retine persoana si data scadenta?**
Nu. Retine doar starea imprumutata. Durata de 21/7 zile demonstreaza polimorfismul.
Nu exista tabela de cititori sau evidenta scadentelor.

**Este totul mostenire?**
Nu. MainFrame foloseste un Repository printr-un camp: este o relatie de asociere,
de tip „foloseste”. Carte extends Publicatie exprima mostenire, de tip „este un”.

**Ce ai putea imbunatati?**
As putea adauga cititori, imprumuturi cu data si scadenta, un istoric si operatii
de baza de date in fundal. In prezent operatiile SQL sunt mici si sincrone;
pentru volume mari as folosi SwingWorker ca sa pastrez interfata responsiva.

**Ce inseamna var si blocul cu trei ghilimele?**
var lasa compilatorul sa deduca tipul unei variabile locale; Java ramane tipizat static.
Blocul delimitat de trei ghilimele este un text block, folosit pentru SQL pe mai multe linii.
switch cu -> din modelul tabelului este o expresie switch, care produce o valoare.

## Ordinea recomandata pentru demonstratie

1. Porneste aplicatia si arata fereastra principala.
2. Adauga o Carte si o Revista; arata termenele diferite.
3. Incearca un titlu gol ca sa demonstrezi validarea.
4. Cauta, filtreaza si sorteaza.
5. Editeaza o publicatie.
6. Imprumuta o publicatie si incearca stergerea ei.
7. Returneaz-o, apoi arata confirmarea de stergere.
8. Inchide si redeschide aplicatia pentru a demonstra persistenta.
9. Deschide Publicatie, Carte si Revista pentru explicatia OOP.
10. Arata Repository si implementarea SQL.

Citeste codul acestor clase inainte de prezentare si exerseaza explicatiile cu
propriile cuvinte. Exemplele de mai sus trebuie intelese, nu doar memorate.
