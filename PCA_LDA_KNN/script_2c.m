clear all;
clc;

% loading data
load("optdigits_train.txt");
load("optdigits_test.txt");

training_data = optdigits_train;
test_data = optdigits_test;

%projecting to R^2
[principal_components,eigenvalues] = myPCA(training_data,2);


[size_training, F] = size(training_data);
[size_test, ~] = size(test_data);

projected_train = training_data(:,1:F-1) * principal_components;
projected_train(:,end+1) = training_data(:,F);
projected_test = test_data(:,1:F-1)* principal_components;
projected_test(:,end+1) = test_data(:,F);


for i = 0:9
train_class(:,i+1) = (projected_train(:,3) == i);
end

for i = 0:9
test_class(:,i+1) = (projected_test(:,3) == i);
end

%set plot
labels = ["0","1","2","3","4","5","6","7","8","9"];
arg = [".r",".b",".g",".k",".m","r*","b*","g*","k*","m*"];
figure
hold on;

%draw the plot
for i = 1:10
    plot(projected_train(train_class(:,i),1),projected_train(train_class(:,i),2),arg(i));
    text(projected_train(train_class(1:100,i),1),projected_train(train_class(1:100,i),2),labels{i});
end

for i = 1:10
    plot(projected_test(test_class(:,i),1),projected_test(test_class(:,i),2),arg(i));
    text(projected_test(test_class(1:100,i),1),projected_test(test_class(1:100,i),2),labels{i});
end


