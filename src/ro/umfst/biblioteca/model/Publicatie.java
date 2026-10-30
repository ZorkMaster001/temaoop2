package ro.umfst.biblioteca.model;

import java.util.Objects;

/** Baza comuna: identitatea nu se modifica dupa creare. */
public abstract class Publicatie {
    private final String id;
    private String titlu;
    private String autor;
    private int an;
    private boolean imprumutata;

    protected Publicatie(String id, String titlu, String autor, int an) {
        this(id, titlu, autor, an, false);
    }

    protected Publicatie(String id, String titlu, String autor, int an, boolean imprumutata) {
        this.id = textValid(id, "ID");
        setTitlu(titlu);
        setAutor(autor);
        setAn(an);
        this.imprumutata = imprumutata;
    }

    protected static String textValid(String text, String camp) {
        if (text == null || text.trim().isEmpty())
            throw new IllegalArgumentException(camp + " este obligatoriu.");
        return text.trim();
    }

    public String getId() { return id; }
    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = textValid(titlu, "Titlul"); }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = textValid(autor, "Autorul / editorul"); }
    public int getAn() { return an; }
    public void setAn(int an) {
        if (an < 1450 || an > java.time.Year.now().getValue() + 1)
            throw new IllegalArgumentException("Anul trebuie sa fie intre 1450 si anul urmator.");
        this.an = an;
    }
    public boolean isImprumutata() { return imprumutata; }
    public void setImprumutata(boolean valoare) { imprumutata = valoare; }
    public abstract String getTip();
    public abstract int getDurataImprumut();
    public abstract String getDetaliu();

    @Override public String toString() { return titlu + " - " + autor + " (" + an + ")"; }
    @Override public final boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Publicatie)) return false;
        return id.equals(((Publicatie) other).id);
    }
    @Override public final int hashCode() { return Objects.hash(id); }
}