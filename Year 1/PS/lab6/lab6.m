pkg load statistics
alfa = input('sign. level(in(0,1))=');
%a)
m0=9;

X=[7 7 4  5  9 9 
   4 12 8 1  8 7 
   3 13 2 1 17 7 
   12 5 6 2  1 13 
   14 10 2 4 9 11 
   3 5 12 6 10 7 
   ];%sample data
n=length(X(:))
sigma=5;%case sigma 
tail=-1;
disp("We are solving the first part of the first problem");
disp("we are using a left-tailed test on the mean")
ans = ztest(X,m0,sigma,"alpha",alfa,"tail","left");
