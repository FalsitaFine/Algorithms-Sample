clear all;
clc;

% loading data
load("optdigits_train.txt");
load("optdigits_test.txt");

training_data = optdigits_train;
test_data = optdigits_test;

[principal_components,eigenvalues] = myLDA(training_data,2);

[size_training, F] = size(training_data);
[size_test, ~] = size(test_data);

projected_train = training_data(:,1:F-1) * principal_components;
projected_train(:,end+1) = training_data(:,F);
projected_test = test_data(:,1:F-1)* principal_components;
projected_test(:,end+1) = test_data(:,F);

%set plot
labels = ["0","1","2","3","4","5","6","7","8","9"];
arg = [".r",".b",".g",".k",".m","r*","b*","g*","k*","m*"];

figure
title('LDA Projected');
hold on;

for i = 0:9
    getclass = (training_data(:,F) == i);
    plot(projected_train(getclass,1),projected_train(getclass,2),arg(i+1));
    text(projected_train(getclass,1),projected_train(getclass,2),labels{i+1});
end

for i = 0:9
    getclass = (test_data(:,F) == i);
    plot(projected_test(getclass,1),projected_test(getclass,2),arg(i+1));
    text(projected_test(getclass,1),projected_test(getclass,2),labels{i+1});
end


