pkg load statistics
%at the 5% significance level, on average, do these nickle particles seem to be 
%smaller than 3?
% alpha = significance level
alpha = 0.05

% data
x = [3.26, 1.89, 2.42, 2.03, 3.07, 2.95, 1.39, 3.06, 2.46, 3.35, 1.56, 1.79, ...
     1.76, 3.82, 2.42, 2.96]
     
% data size 
n = length(x)

% average means miu

% H0 - null hypothesis
% H0: The nickle particles are equal to 3
% H0: miu = 3
% H1 - the research hypothesis
% H1: The nickle particles are smaller than 3
% H1: miu < 3
% => we perform a left-tailed test 
fprintf("We perform a left-tailed test for the population mean\n");

% we want to perform a test for the population mean, the second case, 
% with a large sample but sigma unknown => ttest aas TT in T(n-1)
miu = 3

% ttest -> test for the mean of a population(theta=miu), the case where sigma is unknown
% INPUT : x = the data sample 
%         miu = the population mean
%         alpha = the significance level
%         tail = left
% OUTPUT: H = indicator which tells us if we reject or do not reject H_0
%         P = critical value of the test (p-value)
%         CI = confidence interval
%         statistics = TS_0 TS when theta = theta0. If TS_0 is in RR => reject H0.
[H,P,CI,statistics] = ttest(x, miu, 'alpha', alpha, 'tail', 'left');

% result of the test, h = 0, if H0 is NOT rejected,
% h = 1, if H0 IS rejected
fprintf('\n H is %d', H)
if H == 1
  fprintf('\n So the null hypothesis is rejected, \n')
  fprintf('i.e the data suggests that the nickle particles are smaller than 3\n')
else
  fprintf('\nSo the null hypothesis is not rejected,\n')
  fprintf('i.e. the data suggests that the nickle particles are not smaller than 3\n')
end  


pkg load statistics

% alpha = significance level
alpha = 0.05

% data
x = [3.26, 1.89, 2.42, 2.03, 3.07, 2.95, 1.39, 3.06, 2.46, 3.35, 1.56, 1.79, ...
     1.76, 3.82, 2.42, 2.96]
     
% data size 
n = length(x)

% average means miu

% H0 - null hypothesis
% H0: The nickle particles are equal to 3
% H0: miu = 3
% H1 - the research hypothesis
% H1: The nickle particles are smaller than 3
% H1: miu < 3
% => we perform a left-tailed test 
fprintf("We perform a left-tailed test for the population mean\n");

% we want to perform a test for the population mean, the second case, 
% with a large sample but sigma unknown => ttest aas TT in T(n-1)
miu = 3

% ttest -> test for the mean of a population(theta=miu), the case where sigma is unknown
% INPUT : x = the data sample 
%         miu = the population mean
%         alpha = the significance level
%         tail = left
% OUTPUT: H = indicator which tells us if we reject or do not reject H_0
%         P = critical value of the test (p-value)
%         CI = confidence interval
%         statistics = TS_0 TS when theta = theta0. If TS_0 is in RR => reject H0.
[H,P,CI,statistics] = ttest(x, miu, 'alpha', alpha, 'tail', 'left');

% result of the test, h = 0, if H0 is NOT rejected,
% h = 1, if H0 IS rejected
fprintf('\n H is %d', H)
if H == 1
  fprintf('\n So the null hypothesis is rejected, \n')
  fprintf('i.e the data suggests that the nickle particles are smaller than 3\n')
else
  fprintf('\nSo the null hypothesis is not rejected,\n')
  fprintf('i.e. the data suggests that the nickle particles are not smaller than 3\n')
end  


