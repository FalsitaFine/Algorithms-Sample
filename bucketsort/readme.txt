cuixx327@umn.edu
Tianming cui
5326541
The code has been tested with sampleinput on CSEL-KH1250-34



usage: bash run.sh inputfilename(or ./run.sh inputfilename)
put the inputfile in the same direction with the code(and .sh),
then type bash run.sh,
the program will give out an output.txt

Here I assumed the names will not be too long.
It is not because my bucketsort method itself,
just because I sort these names by converting them into (double) type values and sort these values.
The method is, for a name, get the ascii of each bit, 
divide the second bit by 200^1, the third bit by 200^2, etc.
So if the name is too long, the accuracy of (double) type will not enough and therefore the program will be not able to distingush them(for example, Aaaaaaaaaaaaaaaaaaab and Aaaaaaaaaaaaaaaaaac).
For normal names, it works well.


Thank you for reading!
Have a good day!
