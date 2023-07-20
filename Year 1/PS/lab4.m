pkg load statistics
P = input("p >> ")
N = input("no of simulations >> ")
rel_freq=zeros(1,N)
U = rand(N)

for (x=1:1:N)
    c=1;
    while(U(x,c)>=P&&c <=N)
       c+=1;
    endwhile
    rel_freq(c)=rel_freq(c)+1;
endfor

rel_freq=rel_freq/N;
disp(rel_freq);
x=1:N;
plot(x,rel_freq,"+");    

#{
X = (U < P)
U_X = unique(X)

n_X = hist(X,length(U_X));

rel_freq = n_X/N

x=binornd(N,P)



a=0:N
y=binopdf(x,N,P)
figure
bar(a,y,1)
#}