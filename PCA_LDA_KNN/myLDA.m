function [projection_matrix, eigenvalues] = myLDA(data, num_principal_components)


[N_data,F] = size(data);
dislabeled_data = data(:,1:F-1);
%disp(dislabeled_data);

data_mean = mean(dislabeled_data);
Sw = zeros(F-1,F-1);
Sb = zeros(F-1,F-1);

for i = 1:10 % 0 - 9
    %get dataset for current digit
    classdata = dislabeled_data(data(:,F) == i-1,:);
    
    %get mean within current class
    m_i = mean(classdata);
    
    
    datasize = size(classdata,1);
    Si = 0;
    for j = 1:datasize
        Si = Si + (classdata(j,:) - m_i)' * (classdata(j,:) - m_i);
        %disp(Si)
    end
    Sw = Sw + Si;
    %disp(Sw);
    Sb = Sb + datasize*(m_i - data_mean)'*(m_i - data_mean);
end
%disp(obj_f);
[projection_matrix, eigenvalues] = eig(pinv(Sw)*Sb);
%disp(projection_matrix);
%disp("---");
%disp(eigenvalues);

projection_matrix= projection_matrix(:,end-num_principal_components+1:end);
eigenvalues = eigenvalues(end-num_principal_components+1:end);

end
