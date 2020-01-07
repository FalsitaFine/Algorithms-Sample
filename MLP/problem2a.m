clear all;
clc;

k = 10; 
r = 0;
error = zeros(6,2);

%train
for i=3:3:18
    r = r + 1;
    fprintf('M = %d \n',i);
    [Z_train,W,V] = mlptrain('optdigits_train.txt','optdigits_valid.txt',i,k);
    error(r,:) = csvread('buff.csv');
end

%plot and get best M
M = [3,6,9,12,15,18];
figure(1)

plot(M,error(:,1),'r-');
hold on
plot(M,error(:,2),'b-');
legend('Training','Validation');
title('Error with different number of Hidden layers');
xlabel('Layers');
ylabel('Error');


[~, bestM] = min(error(:,2));
bestM = bestM*3;

%test with best M
fprintf('The best number of hidden layer(M) is %d',bestM);
fprintf('\n Retrain the data to get W and V\n');
[Z_train,W,V] = mlptrain('optdigits_train.txt','optdigits_valid.txt',bestM,k);


fprintf('\n Using the best M on the testdata, the error rate is');
[Z_test] = mlptest('optdigits_test.txt',W,V);

    