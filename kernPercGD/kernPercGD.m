function [alpha,b] = kernPercGD(train_data, train_label)
%Set different degree
degree = 6;

%initialize
[N,k] = size(train_data);
alpha = zeros(N,1);
alpha_past = zeros(N,1);
delta_alpha = 0;
delta_alpha_past = 0;

b = 0;
data_kernelized = zeros(N,N);


%Kernelize
for i = 1:N
    for j = 1:N 
        data_kernelized(i,j) = ((train_data(i,:)*train_data(j,:)')+1)^degree; %13.29
    end
end

while(1)
    alpha_past = alpha;
    delta_alpha_past = delta_alpha;
    for i = 1:N
        if ((((alpha.*train_label)'*data_kernelized(:,i)) + b)*train_label(i)) <= 0
            alpha(i) = alpha(i) + 1;
            b = b + train_label(i);
        end
    end
    %check converge
    delta_alpha = abs(abs(norm(alpha) - norm(alpha_past)));
    if abs(delta_alpha-delta_alpha_past)<=1e-4
        break;
    end
    if (norm(alpha) - norm(alpha_past) <= 1e-4)
        break;
    end
end

end
