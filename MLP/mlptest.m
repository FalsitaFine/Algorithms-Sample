%function [Z] = test(test_data,w,v)
function [Z] = mlptest(test_data,w,v)
test = load(test_data);
[n,d] = size(test);
x = test(:,1:d-1);
x(:,d) = 1;
y = test(:,d);
[m,~] = size(w);
[k,~] = size(v);

z = ones(n,m);
zf = ones(n,m+1);
o = ones(n,k);
result = zeros(n,k);

out = zeros(n,1);

for i = 1:n
xi = x(i,:);
z(i,:) = xi*w';
zf(i,1:m) = z(i,:).*(z(i,:) > 0);
o(i,:) = zf(i,:)*v';
        for k=1:size(o,2)
            result(i,k) = exp(o(i,k))/sum(exp(o(i,:)));
        end
end

for i=1:n
    [~,index] = max(result(i,:));
    out(i)= index-1;
end
corr = 0;
for i = 1:n
  if(y(i) == out(i))
      corr = corr + 1;
  end
end    
Err = 1 - (corr/n);
Err
csvwrite('buff.csv',Err);
Z = zf(:,1:m);
end
