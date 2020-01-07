function [h, m, Q] = EMG(flag, image, k)

%loading image file
[img,cmap] = imread(image);
img_rgb = ind2rgb(img,cmap);
img_double = im2double(img_rgb);
[width, height, dimension] = size(img_double);
X = reshape(img_double, width * height, dimension);
[N,d] = size(X);

%initialize
[clusters_initial, m] = kmeans(X, k, 'Maxiter', 3, 'EmptyAction','singleton');

for i = 1 : k
    sigma(:,:,i) = cov(X((clusters_initial==i),:));
    p(:,i) = mvnpdf(X,m(i,:),sigma(:,:,i));
end

pi = ones(1,k)/k; 



%First Iteration
    for i = 1:k
        h(:,i) = pi(i)*p(:,i)./(pi*p')';
    end
    
    for i = 1:k
        Ni = sum(h(:,i),1);
        m(i,:)= (1/Ni)*h(:,i)'*X;
        pi(i) = Ni/N;
        X_minus = X - m(i,:);
        sigma(:,:,i) = 0;
        for j = 1:N
            %Mode selection
            if flag == 0
                sigma(:,:,i) = sigma(:,:,i) +( h(j,i)*X_minus(j,:)'*X_minus(j,:))/Ni;
            else
                sigma(:,:,i) = sigma(:,:,i) +( h(j,i)*X_minus(j,:)'*X_minus(j,:) + h(j,i)*(1e-10)*eye(d))/Ni;
            end
            
        end
        %Renew Pgauss
        p(:,i) = mvnpdf(X,m(i,:),sigma(:,:,i));
    end
    
    
%Loop    
iteration = 1;
Q_E(1) = 0;
Q_M(1) = 0;
while(true)

    %At most 100 Iterations
    if(iteration>=100)
        break;
    end
    
    iteration = iteration + 1
    Q_E(iteration) = 0;
    Q_M(iteration) = 0;
    
    %E-step
    for i = 1:k
        h(:,i) = pi(i)*p(:,i)./(pi*p')';
    end
    
    %Renew likelihood value
    for i = 1: k
        for j = 1:N
            if p(j,i) == 0
                p(j,i) = 1e-20;
            end
            Q_E(iteration) = Q_E(iteration) +h(j,i)*(log(pi(i))+log(p(j,i)));
        end
    end

    %M-step
    for i = 1:k
        Ni = sum(h(:,i),1);
        m(i,:)= (1/Ni)*h(:,i)'*X;
        pi(i) = Ni/N;
        X_minus = X - m(i,:);
        sigma(:,:,i) = 0;
        for j = 1:N
            %Mode selection
            if flag == 0
                sigma(:,:,i) = sigma(:,:,i) +( h(j,i)*X_minus(j,:)'*X_minus(j,:))/Ni;
            else
                sigma(:,:,i) = sigma(:,:,i) +( h(j,i)*X_minus(j,:)'*X_minus(j,:) + h(j,i)*(1e-10)*eye(d))/Ni;
            end
        end
        %Renew Pgauss
        p(:,i) = mvnpdf(X,m(i,:),sigma(:,:,i));
    end
    
    %Renew likelihood value
        for i = 1: k
        for j = 1:N
            if p(j,i) == 0
                p(j,i) = 1e-20;
            end
            Q_M(iteration) = Q_M(iteration) + h(j,i)*(log(pi(i))+log(p(j,i)));
        end
        end
    
    %Check convergence    
    if(abs((Q_M(iteration)-Q_M(iteration-1))/Q_M(iteration-1)))<=1e-5
        break;
    end
    if(abs((Q_E(iteration)-Q_E(iteration-1))/Q_E(iteration-1)))<=1e-5
        break;
    end

    %disp(abs((Q_M(iteration)-Q_M(iteration-1))/Q_M(iteration-1)));
    %disp(abs((Q_E(iteration)-Q_E(iteration-1))/Q_E(iteration-1)));
    
end
figure()
%Plot of the complete likelihood 
plot(abs(Q_E(2:1:iteration-1)),'-r')
hold on;
plot(abs(Q_M(3:1:iteration-1)),'-g')
title(['Complete Likelihood Figure with K = ',num2str(k)]);
legend('E-step','M-step');
hold off;

Q = Q_M;


%Generate Output compressed Figures
figure()
if flag == 0
title(['Compressed Result of EM algorithm with K = ',num2str(k)]);
else
title(['Compressed Result of Modified EM algorithm with K = ',num2str(k)]);
end
hold on;
compressed = reshape(m(clusters_initial, :), [width, height,dimension]);
compressed = rgb2ind(compressed, m);
imshow(compressed,m);

hold off;
end
