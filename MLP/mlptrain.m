
function [Z,w,v] = mlptrain(train_data,val_data,m,k)

%load data
training = load(train_data);
[n,d] = size(training);
x = training(:,1:d-1);
x(:,d) = 1;
y = training(:,d);

%initialize
w = 0.02 * rand(m,d) - 0.01; %[-0.01,0.01]
v = 0.02 * rand(k,m+1) - 0.01;
z = ones(n,m);
zf = ones(n,m+1);
o = ones(n,k);
eta = 0.0001;
iter = 1000;
E = zeros(iter,1);
label = zeros(n,k);

for i = 1:n
    label(i,y(i)+1) = 1;   
end


for j = 1 : iter
    
for i = 1 : n
    xi = x(i,:);
    z(i,:) = xi * w';
    %fixed z
    zf(i,1:m) = z(i,:).*(z(i,:) > 0);

    o(i,:) = zf(i,:)*v';
    
    %softmax
    for k = 1:size(o,2)
        result(i,k) = exp(o(i,k))/sum(exp(o(i,:)));
    end
    
        ref = zeros(size(result(i,:)));
        ref(y(i) + 1) = 1;
        dif = (ref - result(i,:))*v;
        
        dv = eta*((ref - result(i,:))'*zf(i,:));
        dw = eta*(dif(1:m))'*xi;
        
        v = v + dv;
        %if (z(i,:)>=0)
        %    w(1,:) = w(1,:) + dw(1,:);
        %else
        %    w(0,:) = w(0,:) + dw(0,:);
        %end
        up_ind = (z(i,:) >= 0);
        w(up_ind,:) = w(up_ind,:) + dw(up_ind,:);
        
        
end
l = label.*log(result);
E(j) = -sum(sum(l));
if (j > 2)

    dE = (abs(E(j)) - abs(E(j-1))) / (abs(E(j)));
    %check converge
        if (dE > 0)
            break;
        end
    %modify eta
        if (abs(E(j)) < abs(E(j-1)))
            eta = eta + (1e-4); %a
        else
            eta = eta + (-1e-2) * eta; %-b*eta
        end
        
end
        
    

            
    
        
end

fprintf('Error rate on the training data with m = %d:\n',m);
[z_train] = mlptest(train_data,w,v);
train_err = csvread('buff.csv');

fprintf('Error rate on the validation data with m = %d:\n',m);
[z_valid] = mlptest(val_data,w,v);
valid_err = csvread('buff.csv');

Z = z_train;  

csvwrite('buff.csv',[train_err,valid_err]);
end