pkg load statistics
%find a 99% confidence interval for the standard deviation of the size of 
%nickles particles

%data
x = [3.26, 1.89, 2.42, 2.03, 3.07, 2.95, 1.39, 3.06, 2.46, 3.35, 1.56, 1.79, ...
     1.76, 3.82, 2.42, 2.96]
     
%size     
n=length(x) 

    
% confidence level
oneminusalpha = 0.99
% significance level
alpha = 1 - oneminusalpha;

v=var(x); 
q1=chi2inv(1-alpha/2,n-1);
q2=chi2inv(alpha/2,n-1);  %we need both quantiles , there's no more symmetry
v1 = (n - 1) * v/q1; 
v2 = (n - 1) * v/q2;
s1 = sqrt(v1); s2 = sqrt(v2);

fprintf('conf. interval for st. deviation (s1, s2) = (%4.3f, %4.3f)\n\n', s1, s2)