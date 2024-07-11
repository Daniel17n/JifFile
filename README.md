# JifFile
JifFile is a java project that allows you to see if two files are identical or have some difference between them. If there are differences, the program will show you the first line at which they differ. 

> Currently the program only accepts two files. I personaly don't see the use case to compare multiple files between them. So it will be like that for the moment. See the [FEATURE ROAD MAP](#feature-road-map) to check if it has become a feature yet.

## Quickstart
To quickstart the program, you first have to clone the repository into your machine. 
Then follow this steps:
1. Compile the .java:
``` bash
$> javac Jiff.java
```
2. Run the program with the proper arguments: 
> Jiff <Path\to\first.file> <Path\to\second.file>
``` bash
$> java -cp . Jiff "Path\\to\\first.file" "Path\\to\\second.file"
```

I've included a functionality where, if two files have different sizes, the program will ask you for input:
- You want to know where they first differ, so you enter "y".
- You dont care, so you enter "n". But at this point I may ask: Why wouldn't you just compared the size of those two files?

## FEATURE ROAD MAP
This is the list of things I would like to implement, and a rough order of implementation. This features will have no ETA for the moment.
- Create a GUI where to input a path to the file.
- Compile to executable 
- Show all different lines between the files.
- Support creating a file explorer window to search and select the files.
- Support drag&drop.
- TBD