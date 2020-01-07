%% clear

clear all;
clc;


%% 2(a)(b)

for k = 4 : 4 : 12
    try
        [h m Q] = EMG(0,'stadium.bmp',k);
    catch ER
        disp(ER);
    end
end



%% 2(c)
%Run EM
try
    [h m Q] = EMG(0,'goldy.bmp',7);
catch ER
    disp(ER);
end

%Run K-mean with K = 7
[img,cmap] = imread('goldy.bmp');
img_rgb = ind2rgb(img,cmap);
img_double = im2double(img_rgb);
[width, height, dimension] = size(img_double);
X = reshape(img_double, width*height, dimension);
[N,d] = size(X);
[clusters_initial, m] = kmeans(X, 7, 'Maxiter', 100, 'EmptyAction','singleton');
figure
hold on;
new = m(clusters_initial, :);
compressed = reshape(new, [width, height,d]);
compressed = rgb2ind(compressed, m);
imshow(compressed,m);
title(['Compressed Result of K-means algorithm with K = ',num2str(7)]);
hold off;

%% 2(d)
%shown in the written report

%% 2(e)
    [h m Q] = EMG(1,'goldy.bmp',7);

