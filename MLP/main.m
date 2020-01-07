clear all;clc
%% Only modify the codes in the Define Network Architecture section.


%% Load and Explore Image Data
% training data
digitDatasetPath = 'optdigits_train';
imds_train = imageDatastore(digitDatasetPath, ...
    'IncludeSubfolders',true,'LabelSource','foldernames');
figure;
perm = randperm(1873,20);
for i = 1:20
    subplot(4,5,i);
    imshow(imds_train.Files{perm(i)});
end

labelCount = countEachLabel(imds_train)

img = readimage(imds_train,1);
size(img)

% validation data
digitDatasetPath = 'optdigits_valid';
imds_valid = imageDatastore(digitDatasetPath, ...
    'IncludeSubfolders',true,'LabelSource','foldernames');
figure;
perm = randperm(1873,20);
for i = 1:20
    subplot(4,5,i);
    imshow(imds_valid.Files{perm(i)});
end

labelCount = countEachLabel(imds_valid)

img = readimage(imds_valid,1);
size(img)

% testing data
digitDatasetPath = 'optdigits_test';
imds_test = imageDatastore(digitDatasetPath, ...
    'IncludeSubfolders',true,'LabelSource','foldernames');
figure;
perm = randperm(1873,20);
for i = 1:20
    subplot(4,5,i);
    imshow(imds_test.Files{perm(i)});
end

labelCount = countEachLabel(imds_test)

img = readimage(imds_test,1);
size(img)


%%%%%%%%%%%%%%%%%%%%%%%% Modify the codes here %%%%%%%%%%%%%%%%%%%%%%
%% Define Network Architecture 
%%%%%%%%%%%%%%%%%%%%%%%% Modify the codes here %%%%%%%%%%%%%%%%%%%%%%

%comment out different layers to perform the two different questions

%{
layers = [ ...
    imageInputLayer([8 8 1])
    convolution2dLayer(4,1,'Padding','same')
    batchNormalizationLayer
    %examplePreluLayer(20)
    myLReLULayer(20)
    fullyConnectedLayer(10)
    softmaxLayer
    classificationLayer];

%}


layers = [ ...
    imageInputLayer([8 8 1])
    %convolution2dLayer(3,20)
    convolution2dLayer(3,20,'Padding','same')
    batchNormalizationLayer
    %examplePreluLayer(20)
    myLReLULayer(20)
    maxPooling2dLayer(3,'Stride',2)
    convolution2dLayer(3,32,'Padding','same')
    batchNormalizationLayer
    %examplePreluLayer(20)
    myLReLULayer(20)
    fullyConnectedLayer(10)
    softmaxLayer
    classificationLayer];


%% Specify training/validation options
options = trainingOptions('adam','MaxEpochs',10, ...
    'ValidationData',imds_valid, ...
    'ValidationFrequency',30, ...
    'Verbose',false, ...
    'Plots','training-progress');

%% Train the network
net = trainNetwork(imds_train,layers,options);

%% Predict the labels of new data and calculate the classification accuracy.
YPred = classify(net,imds_test);
Ytest = imds_test.Labels;
accuracy = sum(YPred == Ytest)/numel(Ytest)