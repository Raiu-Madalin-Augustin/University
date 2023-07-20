pkg load statistics

n=input("Give value to n=");
p=input("Give value to p=");
x=linspace(0,n,n+1);

thing=binopdf(x,n,p);
figure;
bar(x,thing,1);
xlabel('Observation');
ylabel('Probability');

x1=0:0.01:n;
a=binocdf(x1,n,p);
figure;
stairs(x1,a);
xlabel('Observation');
ylabel('Cumulative Probability');



disp("P(X=0)= "), disp(binopdf(0, 3, 0.5))
disp("P(X!=1)= "), disp(sum(binopdf([0,2,3], 3, 0.5)))

disp("P(X<=2)= "), disp(binocdf(2,3,0.5))
disp("P(x<2)= " ), disp(binocdf(1,2,0.5))

disp("P(X>=1)= "), disp(binocdf(3,3,0.5)-binocdf(0,3,0.5))

