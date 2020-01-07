clc;
clear all;

% loading data
load("face_train_data_960.txt");
load("face_test_data_960.txt");
data_train = face_train_data_960;
data_test = face_test_data_960;

data_combined = [data_train; data_test];

[eigenfaces,~] = myPCA(data_combined,41);


[N_test,F] = size(data_test);

%call KNN function
for i = 1:4
    prediction_result = myKNN(data_train, data_test, (2*i-1));
    %{
    disp("---");
    disp(2*i-1);
    disp(prediction_result(i));
%}
    error_rate(i) = nnz((prediction_result - data_test(:,F)))/N_test;
    k = 2*i-1
    disp("error rate:");
    disp(error_rate(i));
end