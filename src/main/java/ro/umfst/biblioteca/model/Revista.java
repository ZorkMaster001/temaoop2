package ro.umfst.biblioteca.model;

import java.util.UUID;

public final class Revista extends Publicatie {
    private int numar;
    public Revista(String titlu, String editor, int an, int numar) {
        this(UUID.randomUUID().toString(), titlu, editor, an, numar, false);
    }
    public Revista(String id, String titlu, String editor, int an, int numar, boolean imprumutata) {
        super(id, titlu, editor, an, imprumutata);
        setNumar(numar);
    }
    public int getNumar() { return numar; }
    public void setNumar(int numar) {
        if (numar < 1) throw new IllegalArgumentException("Numarul revistei trebuie sa fie pozitiv.");
        this.numar = numar;
    }
    @Override public String getTip() { return "Revista"; }
    @Override public int getDurataImprumut() { return 7; }
    @Override public String getDetaliu() { return String.valueOf(numar); }
    @Override public String toString() { return super.toString() + " | Revista nr. " + numar; }
}