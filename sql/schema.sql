-- Schema de referinta; Database.java creeaza automat tabela la prima pornire.
CREATE TABLE IF NOT EXISTS publicatii (
    id TEXT PRIMARY KEY,
    tip TEXT NOT NULL CHECK(tip IN ('Carte','Revista')),
    titlu TEXT NOT NULL CHECK(length(trim(titlu)) > 0),
    autor TEXT NOT NULL CHECK(length(trim(autor)) > 0),
    an INTEGER NOT NULL CHECK(an >= 1450),
    detaliu TEXT NOT NULL CHECK(length(trim(detaliu)) > 0),
    imprumutata INTEGER NOT NULL DEFAULT 0 CHECK(imprumutata IN (0,1))
);