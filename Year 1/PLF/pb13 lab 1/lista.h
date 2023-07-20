#ifndef LISTA_H
#define LISTA_H


//tip de data generic (pentru moment este intreg)
typedef int TElem;

//referire a structurii Nod;
struct Nod;

//se defineste tipul PNod ca fiind adresa unui Nod
typedef Nod *PNod;

typedef struct Nod{
    TElem e;
	PNod urm;

};

typedef struct{
//prim este adresa primului Nod din lista
	PNod _prim;
} Lista;

//operatii pe lista - INTERFATA

//crearea unei liste din valori citite pana la 0
    Lista creare();
//tiparirea elementelor unei liste
    void tipar(Lista l);
// destructorul listei
	void distruge(Lista l);

    bool elementExists(TElem e,PNod b);
	int Length(PNod a);
    bool check(PNod a, PNod b);
    int inclusion(Lista l1,Lista l2);

    int Eliminate(Lista l,TElem e);
    int Eliminate_rec(PNod p,PNod a,TElem e);



#endif
