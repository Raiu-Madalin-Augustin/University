

    % 12. Generate all sub-strings of a length 2*n+1,
% formed from values of 0,1 or -1, so a1 = ..., a2n+1 = 0
% and |a(i+1)-ai| = 1 or 2, for every 1 <= i <= 2n.

%
%								{True, n= 0
%consec_different(a1,a2..an)=	{True, n= 1
%								{consec_different(a2..an),n>=2 and a1!=a2;
%								{False, otherwise
% flow: (i)
consec_different([]).
consec_different([_]):- !.
consec_different([E1, E2 | Tail]):-
    !, E1 =\= E2,
    consec_different([E2|Tail]).

%							{True and consec_different(a1..an), Len = 2*n +1
%is_solution(n,len,a1..an)= {
%						    {False, otherwise
%
is_solution(N, Len, L):-
    Len is 2*N+1,
    consec_different(L).

%									{ b1b2..bm, if n = 0
%insert_all(a1,a2,..an,b1b2..bm)=	{	
%									{a1 U b1b2...bm and insert_all(a2a3..an, b1b2..bm),Otherwise
%
% flow: (i,i,i) (i,i,o)
insert_all([], L, L).
insert_all([H|_], L, [H|L]).
insert_all([_|T], L, R):-
    T = [_|_],
    insert_all(T, L, R).

%							{a1,a2..an, if is_solution(n,len,a1a2...an) is True
%generate(n,len,a1a2..an)= 	{
%							{{generate(n,len+1,insert_all([1,-1,0],a1a2..an)),if len <=2*n
% flow: (i,i,i,i) (i,i,i,o)
generate(N, Len, L, L):-
    is_solution(N, Len, L), !.
generate(N, Len, L, Res):-
    Len =< 2*N,
    insert_all([1,-1, 0], L, R),
    Len2 is Len+1,
    generate(N, Len2, R, Res).


list_to_string(L, R):-
    maplist(atom_chars, L, Lists),
    append(Lists, List),
    atom_chars(R, List).


generate_all(N, Res):- findall(R, generate(N, 1, [0], R), Res).

generate_set(N, Res):- setof(R, generate(N, 1, [0], R), Res).








