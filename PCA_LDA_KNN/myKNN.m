% KNN function Kernel
function [prediction] = myKNN(training_data, test_data, k)

% Get size of dataset & number of features
[N_training,F] = size(training_data);
[N_test,~] = size(test_data);

%Correction number of features
F = F - 1;

for i = 1 : N_test
    for j = 1: N_training
        distance(j,i) = norm(training_data(j, 1:F) - test_data(i, 1:F));
        %{
        disp([i,j]);
        disp(distance(i,j));
        disp("-");
        %}
    end
end

%Sort the distance
[~, sorted_index] = sort(distance);


for i = 1 : N_test
    for j = 1: k
        prediction(j,i) = training_data(sorted_index(j,i), F+1);
    end
end


prediction = prediction';
prediction = mode(prediction,2);



%prediction = "default";
end
