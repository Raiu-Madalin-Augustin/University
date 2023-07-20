


%------------------------a
% Substitute all occurrences of an element of a list with all the
% elements of another list. Eg. subst([1,2,1,3,1,4 ],1,[10,11],X)
% produces X=[10,11,2,10,11,3,10,11,4].
%


%flow(i,i,o)
concatenate([],L,L).
concatenate([H|L1],L2,[H|L3]):-concatenate(L1,L2,L3).
%
%
%                                   |l1,..ln ,if n=0
%concatenate(l1,l2...ln,p1,..pm)=   |
%                                   |l1 U
%                                   concatenate(l1,..ln,p1,..pm),otherwise
%


%flow (i,i,i,o)
%                        |[] , n = 0
%subst(E,l1..ln,p1..pm)= |subst(E,l1..ln,E U p2..pm), if p1=E
%                        |subst(E,l1..ln, p2..pm) if  p1!=E
subst(_,_,[],[]).
subst(E,L,[E|T],R):-subst(E,L,T,R1),!,
                    concatenate(L,R1,R).
subst(E,L,[H|T],[H|R]):-subst(E,L,T,R).

% ex subst(1,[10,11],[1,2,1,3,1,4],X) produces
% X=[10,11,2,10,11,3,10,11,4].




%--------------------b
%b. For  a  heterogeneous  list,  formed  from  integer  numbers  and  list  of  numbers,  replace  in  every  sublist  all occurrencesof the first element from sublist it a new given list.Eg.: [1, [4, 1, 4], 3, 6, [7, 10, 1, 3, 9], 5, [1, 1,1], 7] si [11, 11] =>[1, [11, 11, 1, 11, 11], 3, 6, [11, 11, 10, 1, 3, 9], 5, [11 11 11 11 11 11], 7]
%

%               |l1,if n!=0
%first(l1..ln)= |Ø, otherwise
%flow(i,o)

first([H|_],H).


% flow(i, i, o) (i, i, i)
% append_elem(l1l2...ln, e)=| [e], if n = 0
%                           |l1 U append_elem(l2l3...ln, e), otherwise if n != 0
append_elem([], E, [E]).
append_elem([H|T], E, [H|R]):-
    append_elem(T, E, R).


%
%
%Note:point b will use the function subst from point a
%
%                             |C,if n=0
%replace_first(l1l2...ln,S,C)=|replace_first(l2l3...ln,S,C U
%                              subst(first(l1),S,l1),if type(l1) = list
%
%                             | replace_first(l2l3...ln, S, C U l1),
%                                         otherwise if type(l1) != list
%
%flow(i,i,i,o)(i,i,i,i)
%replace_first(L-list,S-list,C-list,R-list)
%
replace_first([],_,R,R).

replace_first([H|T],S,C,R):-
    is_list(H),
    first(H,F),
    subst(F,S,H,NR),
    append_elem(C,NR,NC),
    replace_first(T,S,NC,R).

replace_first([H|T],S,C,R):-
    \+is_list(H),
    append_elem(C,H,NC),
    replace_first(T,S,NC,R).



% ex
% replace_first([1,[4,1,4],3,6,[7,10,1,3,9],5,[1,1,1],7],[11,11],[],R).
%replace_fist([],[11,11],[],R)
