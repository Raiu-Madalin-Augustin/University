A=[1,0,-2;2,1,3;0,1,0]
B=[2,1,1;1,0,-1;1,1,0]
C=A-B
disp("Matrix C is"),C
D=A*B
disp("matrix D is"),D
 x=-10:0.1:10;
 plot(x, x.^2/10, "-m;x*sin(x);", x, "-b;cos(x);")