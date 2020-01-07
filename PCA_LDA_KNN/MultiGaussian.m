
function [s1_result, s2_result] = MultiGaussian(training_data,testing_data,Model)

%% prepare for computing
train = importdata(training_data);
test = importdata(testing_data);

%Get P(Ci), separate data by class
class1_tr = train(:,9) == 1;
class2_tr = train(:,9) == 2;

train_C1 = train(class1_tr,1:8);
train_C2 = train(class2_tr,1:8);
N1 = size(train_C1,1);
N2 = size(train_C2,1);
PC1 = N1/(N1+N2);
PC2 = N2/(N1+N2);
%test_C1 = test(class3,1:8);
%test_C2 = test(class4,1:8);
%N3 = size(test_C1,1);
%N4 = size(test_C2,1);

%compute components
s1 = cov(train_C1);
s2 = cov(train_C2);
s1_inv = pinv(s1);
s2_inv = pinv(s2);
mu1 = mean(train_C1);
mu2 = mean(train_C2);
s = (s1 * N1 + s2 * N2) / (N1 + N2);
s_inv = pinv(s);

result_ref = test(:,9);
t_array = test(:,1:end-1);

%% Model 1
if (Model == 1)
    disp("Model 1");
for i  = 1:size(t_array,1)
    t = t_array(i,:); 
    GC1_X = -0.5*(log(det(s1))) - 0.5*(t - mu1)*s1_inv*(t - mu1)' + log(PC1);
    %w1=  -t*s1_inv*t' + 2*t*s1_inv*mu1' - mu1*s1_inv*mu1';
    GC2_X = -0.5*(log(det(s2))) - 0.5*(t - mu2)*s2_inv*(t - mu2)' + log(PC2);
    if (GC1_X - GC2_X >= 0)
        result(i,1) = 1;
    else
        result(i,1) = 2;
    end
end
wrong_prediction = (result_ref - result) ~= 0;
error_rate = sum(wrong_prediction,1)/100;
disp("error rate:");
error_rate

disp("S1 and S2:");
s1_result = s1 
s2_result = s2 

disp("mu1 and mu2 :");
mu1 
mu2
%disp(result-test(:,9));
end

%% Model 2
if (Model == 2)
    disp("Model 2");
for i  = 1:size(t_array,1)
    t = t_array(i,:); 
    GC1_X = -0.5*(log(det(s))) - 0.5*(t - mu1)*s_inv*(t - mu1)' + log(PC1);
    GC2_X = -0.5*(log(det(s))) - 0.5*(t - mu2)*s_inv*(t - mu2)' + log(PC2);
    if (GC1_X - GC2_X >= 0)
        result(i,1) = 1;
    else
        result(i,1) = 2;
    end
end
wrong_prediction = (result_ref - result) ~= 0;
error_rate = sum(wrong_prediction,1)/100;
disp("error rate:");
error_rate

disp("S1 and S2:");
s1_result = s
s2_result = s

disp("mu1 and mu2 :");
mu1 
mu2
end

%% Model 3
if (Model == 3)
disp("Model 3");
alpha1 = mean(diag(diag(diag(s1))));
alpha2 = mean(diag(diag(diag(s2))));
s1_alpha = alpha1 * eye(size(diag(diag(s1)),1));
s2_alpha = alpha2 * eye(size(diag(diag(s2)),1));
s1_inv = pinv(s1_alpha);
s2_inv = pinv(s2_alpha);
for i  = 1:size(t_array,1)
    t = t_array(i,:); 
    GC1_X = -0.5*(log(det(s))) - 0.5*(t - mu1)*s1_inv*(t - mu1)' + log(PC1);
    GC2_X = -0.5*(log(det(s))) - 0.5*(t - mu2)*s2_inv*(t - mu2)' + log(PC2);
    if (GC1_X - GC2_X >= 0)
        result(i,1) = 1;
    else
        result(i,1) = 2;
    end
end
wrong_prediction = (result_ref - result) ~= 0;
error_rate = sum(wrong_prediction,1)/100;
disp("error rate:");
error_rate

disp("alpha1 and alpha2:");
s1_result = alpha1
s2_result = alpha2

disp("mu1 and mu2 :");
mu1 
mu2

end
end