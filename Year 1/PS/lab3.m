pkg load statistics

mu= input("Input mu\n");
si= input("Input si\n");


disp("Normal")
a=normcdf(0,mu,si)
a1=1-a

b=normcdf(1,mu,si)-normcdf(-1,mu,si)
b1=1-b

n=  input("Input n\n");
p=  input("Input p\n");

disp("Student")

disp("P(x<=0) ="), disp(tcdf(0,n));

a1=1-disp(tcdf(0,n))

tcdf(1,n) -tcdf(-1,n)
tcdf(-1,n) + 1- tcdf(1,n)

disp("X^2")

n=input("parameter for x^2")
res1=gamcdf(0,n/2,2)
1-res1



disp("Fischer")

m=input("m for fischer")
n=input("n for fischer")

res1=fcdf(0,m,n)
1-res1
fcdf(1,m,n)-fcdf(-1,m,n)