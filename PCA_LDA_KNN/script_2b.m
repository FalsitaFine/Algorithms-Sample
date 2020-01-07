clear all;
clc;

% loading data
load("optdigits_train.txt");
load("optdigits_test.txt");

training_data = optdigits_train;
test_data = optdigits_test;

%Find Minimum K
num_principal_components = 64;

[principal_components,eigenvalues] = myPCA(training_data,num_principal_components);

[size_training, F] = size(training_data);
[size_test, ~] = size(test_data);

pca_train = training_data(:,1:F-1)*principal_components;
pca_train(:,end+1) = training_data(:,F);

pca_test = test_data(:,1:F-1);
pca_test(:,end+1) = test_data(:,F);


 
first = 1;
for i=1:num_principal_components
     prop_var(i) = sum(eigenvalues(1:i))/sum(eigenvalues); 
     if prop_var(i) > 0.9 
        if first == 1
         disp("Minimum K explain at least 90% of the Variance");
         disp(i);
         first = 0;
        end
     end
end
 
 plot(prop_var);
 title('Figure')
 xlabel('Number of Eigenvectors')
 ylabel('Proportion of Variance')
 
 
 
 
%Run KNN with K = 21
num_principal_components = 21;

[principal_components,eigenvalues] = myPCA(training_data,num_principal_components);

[size_training, F] = size(training_data);
[size_test, F] = size(test_data);

pca_train = training_data(:,1:F-1)*principal_components;
pca_train(:,end+1) = training_data(:,F);

pca_test = test_data(:,1:F-1)*principal_components;
pca_test(:,end+1) = test_data(:,F);

[N_test,F] = size(pca_test);
disp("Test Result For 21 eigenvalues");

for i= 1:4
[prediction_result] = myKNN(pca_train,pca_test,2*i-1);
    error_rate(i) = nnz((prediction_result - pca_test(:,F)))/N_test;
    k = 2*i-1
    disp("error rate:");
    display(error_rate(i));
end
