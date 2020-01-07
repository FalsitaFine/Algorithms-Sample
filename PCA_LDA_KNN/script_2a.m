clc;
clear all;

% loading data
load("optdigits_train.txt");
load("optdigits_test.txt");
training_data = optdigits_train;
test_data = optdigits_test;
[N_test,F] = size(test_data);

%call KNN function
for i = 1:4
    prediction_result = myKNN(training_data, test_data, (2*i-1));
    %{
    disp("---");
    disp(2*i-1);
    disp(prediction_result(i));
%}
    error_rate(i) = nnz((prediction_result - test_data(:,F)))/N_test;
    k = 2*i-1
    disp("error rate:");
    display(error_rate(i));
end

%Class = knnclassify(test_data,training_data,train_label, k, distance, rule)