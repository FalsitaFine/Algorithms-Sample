%Load Figures
clear all;

% loading data
load("face_train_data_960.txt");
load("face_test_data_960.txt");
data_train = face_train_data_960;
data_test = face_test_data_960;

data_combined = [data_train; data_test];


%Find Minimum K
num_principal_components = 960;

[principal_components,eigenvalues] = myPCA(data_combined,num_principal_components);

[size_combined, F] = size(data_combined);

pca_train = data_combined(:,1:F-1)*principal_components;
pca_train(:,end+1) = data_combined(:,F);




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
 
figure(3);
 plot(prop_var);
 title('Figure')
 xlabel('Number of Eigenvectors')
 ylabel('Proportion of Variance')
 
 
 
 %use 41 find before
[eigenfaces,~] = myPCA(data_combined,41);

for i = 1:5
    figure(1);
    subplot(1,5,i);
    imagesc(reshape(data_combined(i,1:end-1),32,30)');
end

%eigenfaces = eigenfaces';

for i = 1:5
    figure(2);
    subplot(1,5,i);
    imagesc(reshape(eigenfaces(:,i),32,30)');
end



[N_test,F] = size(data_test);
disp("KNN error rate");
%call KNN function
for i = 1:4
    prediction_result = myKNN(data_train, data_test, (2*i-1));
    %{
    disp("---");
    disp(2*i-1);
    disp(prediction_result(i));
%}
    k = 2*i-1
    error_rate = nnz((prediction_result - data_test(:,F)))/N_test
end
