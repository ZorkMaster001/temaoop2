package ro.umfst.biblioteca.model;

import java.util.UUID;

public final class Carte extends Publicatie {
    private String gen;
    public Carte(String titlu, String autor, int an, String gen) {
        this(UUID.randomUUID().toString(), titlu, autor, an, gen, false);
    }
    public Carte(String id, String titlu, String autor, int an, String gen, boolean imprumutata) {
        super(id, titlu, autor, an, imprumutata);
        setGen(gen);
    }
    public String getGen() { return gen; }
    public void setGen(String gen) { this.gen = textValid(gen, "Genul"); }
    @Override public String getTip() { return "Carte"; }
    @Override public int getDurataImprumut() { return 21; }
    @Override public String getDetaliu() { return gen; }
    @Override public String toString() { return super.toString() + " | Carte: " + gen; }
}