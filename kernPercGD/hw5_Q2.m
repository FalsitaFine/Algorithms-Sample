clear all;
clc;


%% Generate data from Homework pdf
rng(1); % For reproducibility
r = sqrt(rand(100,1)); % Radius
t = 2*pi*rand(100,1); % Angle
data1 = [r.*cos(t), r.*sin(t)]; % Points
r2 = sqrt(3*rand(100,1)+2); % Radius
t2 = 2*pi*rand(100,1); % Angle
data2 = [r2.*cos(t2), r2.*sin(t2)]; % points

figure;
plot(data1(:,1),data1(:,2),'r.','MarkerSize',15)
hold on
plot(data2(:,1),data2(:,2),'b.','MarkerSize',15)
ezpolar(@(x)1);ezpolar(@(x)2);

axis equal
hold off

data3 = [data1;data2];
theclass = ones(200,1);
theclass(1:100) = -1;

%% Q2a Kernel perceptron
degree = 6;

[alpha,b] = kernPercGD(data3,theclass);
[N,k] = size(data3);

% Kenelize
for i = 1:N
    for j = 1:N 
        data_kernelized(i,j) = ((data3(i,:)*data3(j,:)')+1)^degree; %13.29 in textbook
    end
end

%Testing and get error value
test_result = zeros(size(theclass,1),1);
for i = 1:N 
    test_result(i) = sign((alpha.*theclass)'*data_kernelized(:,i) + b);
end
correct = (test_result == theclass);
disp("Training error for Q2a, on the generated data:");
train_err = 1 - (sum(correct) / size(theclass,1))


% Predict scores over the grid (from https://www.mathworks.com/help/stats/support-vector-machines-for-binary-classification.html
d = 0.02;
[x1Grid,x2Grid] = meshgrid(min(data3(:,1)):d:max(data3(:,1)),...
    min(data3(:,2)):d:max(data3(:,2)));
xGrid = [x1Grid(:),x2Grid(:)];
%[~,scores] = predict(cl,xGrid);

% Kenelize xGrid
for i = 1:size(data3,1)
    for j = 1:size(xGrid,1) 
                %data_kernelized_x(i,j) = ((xGrid(i,:)*xGrid(j,:)')+1)^degree; 
        data_kernelized_x(i,j) = ((data3(i,:)*xGrid(j,:)')+1)^degree; %13.29
    end
end

% Test and get prediction result
for i = 1:size(xGrid)
    result(i) = ((alpha.*theclass)'*data_kernelized_x(:,i) + b);
end

%plot boundary (from https://www.mathworks.com/help/stats/support-vector-machines-for-binary-classification.html
figure;
h(1:2) = gscatter(data3(:,1),data3(:,2),theclass,'rb','.');
hold on;
% ezpolar(@(x)1);
contour(x1Grid,x2Grid,reshape(result(1,:),size(x1Grid)),[0 0],'k');
legend('-1','+1','kernPercGD');
axis equal


%% Q2b fitcSVM

boxvalue = 100;
boxvalue2 = 1;
boxvalue3 = 0.01;

cl = fitcsvm(data3,theclass,'KernelFunction','polynomial','PolynomialOrder',degree,...
    'BoxConstraint',boxvalue,'ClassNames',[-1,1]);
c2 = fitcsvm(data3,theclass,'KernelFunction','polynomial','PolynomialOrder',degree,...
    'BoxConstraint',boxvalue2,'ClassNames',[-1,1]);
c3 = fitcsvm(data3,theclass,'KernelFunction','polynomial','PolynomialOrder',degree,...
    'BoxConstraint',boxvalue3,'ClassNames',[-1,1]);

% Predict scores over the grid
d = 0.02;
[x1Grid,x2Grid] = meshgrid(min(data3(:,1)):d:max(data3(:,1)),...
    min(data3(:,2)):d:max(data3(:,2)));
xGrid = [x1Grid(:),x2Grid(:)];
[~,scores] = predict(cl,xGrid);
[~,scores2] = predict(c2,xGrid);
[~,scores3] = predict(c3,xGrid);
hold on;  
%ezpolar(@(x)1);
h(3) = plot(data3(cl.IsSupportVector,1),data3(cl.IsSupportVector,2),'ko');
contour(x1Grid,x2Grid,reshape(scores(:,2),size(x1Grid)),[0 0],'m-');

h(4) = plot(data3(c2.IsSupportVector,1),data3(c2.IsSupportVector,2),'bo');
contour(x1Grid,x2Grid,reshape(scores2(:,2),size(x1Grid)),[0 0],'b-');

h(5) = plot(data3(c3.IsSupportVector,1),data3(c3.IsSupportVector,2),'ro');
contour(x1Grid,x2Grid,reshape(scores3(:,2),size(x1Grid)),[0 0],'r-');

legend('-1','+1','kernPercGD','Support vectors, Box=100','fitcSVM, Box=100','Support vectors, Box=1','fitcSVM, Box=1','Support vectors, Box=0.01','fitcSVM, Box=0.01');
title('Figure of Question 2b');
axis equal;
hold off;

%% Q2c
degree = 6;
train_49 = load('optdigits49_train.txt');
test_49 = load('optdigits49_test.txt');
train_79 = load('optdigits79_train.txt');
test_79 = load('optdigits79_test.txt');

train_labels_49 = train_49(:,end);
train_data_49 = train_49(:,1:end-1);
test_labels_49 = test_49(:,end);
test_data_49 = test_49(:,1:end-1);
train_labels_79 = train_79(:,end);
train_data_79 = train_79(:,1:end-1);
test_labels_79 = test_79(:,end);
test_data_79 = test_79(:,1:end-1);

% data 4-9
% Kernel Perceptron 
[alpha_49,b_49] = kernPercGD(train_data_49,train_labels_49);

test_result_49 = zeros(size(train_labels_49,1),1);
% Kernelize 
for i = 1:size(train_data_49,1)
    for j = 1:size(train_data_49,1) 
        data49_kernelized_train(i,j) = ((train_data_49(i,:)*train_data_49(j,:)')+1)^degree; %13.29
    end
end

for i = 1:size(train_data_49,1) 
    test_result_49(i) = sign((alpha_49.*train_labels_49)'*data49_kernelized_train(:,i) + b_49);
end

correct = (test_result_49 == train_labels_49);
disp("Training error for Q2c, 4-9");
train_err = 1 - (sum(correct) / size(train_data_49,1))

% Kernelize test with train data, because we need to use alpha_49 generated
% by the training data
for i = 1:size(train_data_49,1)
    for j = 1:size(test_data_49,1) 
        data49_kernelized_test(i,j) = ((train_data_49(i,:)*test_data_49(j,:)')+1)^degree; %13.29
    end
end

% Testing and report error
for i = 1:size(test_data_49,1) 
    result_49(i) = sign((alpha_49.*train_labels_49)'*data49_kernelized_test(:,i) + b_49);
end

correct_49 = (result_49' == test_labels_49);
disp("testing error for Q2c 4-9");
test_err_49 = 1 - (sum(correct_49)/size(test_data_49,1) )

% data 7-9
% Kernel Perceptron 
[alpha_79,b_79] = kernPercGD(train_data_79,train_labels_79);

test_result_79 = zeros(size(train_labels_79,1),1);
% Kernelize 
for i = 1:size(train_data_79,1)
    for j = 1:size(train_data_79,1) 
        data79_kernelized_train(i,j) = ((train_data_79(i,:)*train_data_79(j,:)')+1)^degree; %13.29
    end
end

for i = 1:size(train_data_79,1)
    test_result_79(i) = sign((alpha_79.*train_labels_79)'*data79_kernelized_train(:,i) + b_79);
end
correct = (test_result_79 == train_labels_79);
disp("Training error for Q2c, 7-9");
train_err = 1 - (sum(correct) / size(train_data_79,1))

% Kernelize 
for i = 1:size(train_data_79,1)
    for j = 1:size(test_data_79,1) 
        data79_kernelized_test(i,j) = ((train_data_79(i,:)*test_data_79(j,:)')+1)^degree; %13.29
    end
end

% Testing and report error
for i = 1:size(test_data_79,1)
    result_79(i) = sign((alpha_79.*train_labels_79)'*data79_kernelized_test(:,i) + b_79);
end

correct_79 = (result_79' == test_labels_79);
disp("Testing error for Q2c 7-9");
test_err_79 = 1 - (sum(correct_79)/size(test_data_79,1))
