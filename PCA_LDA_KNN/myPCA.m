%PCA script

function [principal_components, eigenvalues] = myPCA(data, num_principal_components)

[N_data,F] = size(data);
dislabeled_data = data(:,1:F-1);
%disp(dislabeled_data);

zero_mean_data = dislabeled_data - mean(dislabeled_data,1);
%disp(zero_mean_data);

[principal_components, eigenvalues] = eig(cov(zero_mean_data));
%disp(eigenvector);
%disp(eigenvalues);

eigenvalues =diag(eigenvalues);

principal_components= flip(principal_components(:,end-num_principal_components+1:end),2);
eigenvalues = flip(eigenvalues(end-num_principal_components+1:end));

end

