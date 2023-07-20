pkg load statistics
clc
clear all

#Part B 
#1.
x= [7 8 4 5 9 9;
    4 12 8 1 8 7;
    3 13 2 1 17 7
    12 5 6 2 1 12;
    14 10 2 4 9 11;
    3 5 12 6 10 7]
    
n=length(x(:))   
conf_level=input("confidence level:")
alpha=1-conf_level

# a 
sigma=5;

m_x=mean(x(:))
a=sqrt(n)
z_l = norminv(1-alpha/2, 0, 1);
z_r = norminv(alpha/2, 0, 1);

mL = m_x - sigma / sqrt(n) * z_l;
mR = m_x - sigma / sqrt(n) * z_r;

disp(mL)
disp(mR)

#b
