#include "lista.h"
#include <iostream>

using namespace std;


PNod creare_rec(){
  TElem x;
  cout<<"x=";
  cin>>x;
  if (x==0)
    return NULL;
  else{
    PNod p=new Nod();

    p->e=x;
    p->urm=creare_rec();

    return p;
  }
}

Lista creare(){
   Lista l;
   l._prim=creare_rec();
}

void tipar_rec(PNod p){
   if (p!=NULL){
     cout<<p->e<<" ";
     tipar_rec(p->urm);
   }
}

void tipar(Lista l){
   tipar_rec(l._prim);
}

void distrug_rec(PNod p){
   if (p!=NULL){
     distrug_rec(p->urm);
     delete p;
   }
}


void distrug(Lista l) {
	//se elibereaza memoria alocata nodurilor listei
    distrug_rec(l._prim);
}

bool elementExists(TElem e,PNod b) {
    if (b != nullptr)
    {
        if (e == b->e)
            return true;

        return elementExists(e, b->urm);
    }
    return false;
}

int Length(PNod a){
    if( a == nullptr)
        return 0;
    return 1+Length(a->urm);
}
bool check(PNod a, PNod b)
{
    if(a == nullptr)
        return true;//empty set
    else
        return (elementExists(a->e,b)&& check(a->urm,b));
}

int inclusion(Lista l1,Lista l2) {
    int len1, len2;
    len1 = Length(l1._prim);
    len2 = Length(l2._prim);

    if (len1 < len2)
        {
        if (check(l1._prim, l2._prim))
            return 1;
        }
    if(len1>len2) {

            if (check(l2._prim, l1._prim))
                return 2;

        }
    else
        if(check(l1._prim,l2._prim)) {

            return 3;

        }
    return 0;
}


int Eliminate(Lista l,TElem e){

  return  Eliminate_rec(l._prim,l._prim, e);
}

int Eliminate_rec( PNod prev,PNod curr,TElem e){
    if(curr!= nullptr)
    {
        if(curr->e==e){
            if(curr!=prev)
            {
                prev->urm=curr->urm;
                delete curr;
                return 1 + Eliminate_rec(prev,prev->urm,e);
            }else{
                PNod  aux=curr->urm->urm;
                curr->e=curr->urm->e;
                delete curr->urm;
                curr->urm=aux;
                return 1 + Eliminate_rec(curr,curr,e);
            }
        }else return Eliminate_rec(curr,curr->urm,e);


    }else return 0;
}
