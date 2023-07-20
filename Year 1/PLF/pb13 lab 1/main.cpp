#include "lista.h"
#include <iostream>

int main()
{
   Lista l1;
   Lista l2;
   printf("The First set:\n");
   l1=creare();
   printf("The second set:\n");
   l2=creare();
   if(inclusion(l1,l2 )==1)
       printf("The first set is included in the second set");
   else if(inclusion(l1,l2)==2 )
       printf("The second set is included in the first set");
   else if(inclusion(l1,l2)==3)
       printf("the sets are equal");
   else printf("False");

   printf("\nGive the list for the elimination example \n");
   Lista l3;
   l3=creare();
   int a;
   std::cout<<"\nWhat element do you want to remove? : ";
   std::cin>>a;
   std::cout<<"The element has been eliminated: "<<Eliminate(l3,a)<<" times \n";
   tipar(l3);

}
