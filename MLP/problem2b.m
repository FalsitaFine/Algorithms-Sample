clear;
clc;

%load combine data
combine = 'optdigits_combine.txt';
combine_data = load(combine);
[n,d] = size(combine_data);
label = combine_data(:,d);


%train
m = 18;
k = 10;

fprintf('Train the result with best M from 2a\n');
[Z_train,W,V] = mlptrain('optdigits_train.txt','optdigits_valid.txt',m,k);
fprintf('The error rate on the combined data\n');
[Z] = mlptest(combine,W,V);


%projection and plot
[Proj2D] = pca(Z,'NumComponents',2);
Z_2d = Z*Proj2D;


figure(1);
title('2-D Projection');
%set(gca, 'XScale', 'log')
%set(gca, 'YScale', 'log')

for i= 0 : 10
    index = (label == i);
    hold on;
    plot(Z_2d(index, 1), Z_2d(index, 2),".");
    text(Z_2d(index, 1), Z_2d(index, 2), num2str(i));
end




[Proj3D] = pca(Z,'NumComponents',3);
Z_3d = Z*Proj3D;


figure(2);
title('3-D Projection');
%set(gca, 'XScale', 'log')
%set(gca, 'YScale', 'log')
%set(gca, 'ZScale', 'log')

for i= 0 : 10
    index = (label == i);
    hold on;
    plot3(Z_3d(index, 1), Z_3d(index, 2), Z_3d(index,3),".");
    view(-45,-45);
    text(Z_3d(index, 1), Z_3d(index, 2), Z_3d(index,3),num2str(i));
end
