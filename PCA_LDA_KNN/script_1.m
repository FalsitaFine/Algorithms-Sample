%Question 1
clc;
clear all;

[s11, s21] = MultiGaussian('training_data.txt','test_data.txt',1);
[s12, s22] = MultiGaussian('training_data.txt','test_data.txt',2);
[s13, s23] = MultiGaussian('training_data.txt','test_data.txt',3);
