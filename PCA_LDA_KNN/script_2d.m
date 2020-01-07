clear all;
clc;

% loading data
load("optdigits_train.txt");
load("optdigits_test.txt");

training_data = optdigits_train;
test_data = optdigits_test;

L = [2, 4, 9];

for i = 1:3
current_L = L(i)
%Do LDA for different L
[principal_components,eigenvalues] = myLDA(training_data,L(i));

[size_training, F] = size(training_data);
[size_test, ~] = size(test_data);

projected_train = training_data(:,1:F-1) * principal_components;
projected_train(:,end+1) = training_data(:,F);
projected_test = test_data(:,1:F-1)* principal_components;
projected_test(:,end+1) = test_data(:,F);


    %apply KNN to find the test error
    [N_test,F] = size(projected_test);

    for i= 1:3

    [prediction_result] = myKNN(projected_train, projected_test,2*i-1);
    error_rate(i) = nnz((prediction_result - projected_test(:,F)))/N_test;
    k = 2*i-1
    disp("error rate:");
    display(error_rate(i));
    
    end

end
