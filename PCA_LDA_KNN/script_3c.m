


clear all;
clc;

% loading data
load("face_train_data_960.txt");
load("face_test_data_960.txt");
data_train = face_train_data_960;
data_test = face_test_data_960;
[N_train,F] = size(data_train);


K = [10,50,100];
index = 0;
for i= 1:3
    %projection 
    [projected_faces, ~] = myPCA(data_train,K(i));
    
    %reconstruct(back projection)
    centered_data =projected_faces'*(data_train(:,1:F-1)'-mean(data_train(:,1:F-1))');
    back_projected_faces = projected_faces*centered_data+mean(data_train(:,1:F-1))';
    
    for j=1:5
        index = index+1;

        subplot(4,5,index);
        imagesc(reshape(back_projected_faces(:,j)',32,30)');
        title(['K=',num2str(K(i))]);
    end
end





