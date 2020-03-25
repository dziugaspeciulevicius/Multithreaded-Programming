# Multithreaded-Programming
Multi-threading college tasks with Java

### TASK1
***Task description:***
Develop multithreaded application with graphical user interface for searching files/folders in a filesystem.
Application input:
> - The name of a searched item;
> - Search initial folder.

Application output:
> - list of folders there the searched item is found.
> - search duration.

### TASK2
***Task description:***
Practical Assignment No. 2
Develop bug-free multithreaded application, having graphical user interface, that encodes/decodes user specified folder content. Choose any number of threads you think is necessary and reasonable.

> - Content coding is in AES algorithm.
> - calculating and storing of MD5 hash values for encrypted files.
> - restoring initial directory structure after directory decoding.
> - checking MD5 hash values before decoding. If there is a mismatch, the files are left encoded.

### TASK3
***Task description:***
Practical Assignment No. 3
You've been given the archive (~172MB) of arff (link about arff files) files. Each given file contains a set of features and values of each in the last line of the file (values are separated using comma). Each file is one scientific research trial (structure of all files are the same). Further research needs the data to be concatenated to one arff structured file having the same set of selected features and appropriate data.

You have to create multithreaded errors-free that concatenates separate arff files to one arff-structured file.

> - user is free to select arff files source (folder, list of files and etc.).
> - user is free to select the extractable and concatenable features.
> - application must extract values of selected features from all the given files (if there is file of bad format, file must be skipped and reported).
> - application must create arrf structured file as the results file.
> - application UI must remain unfrozen during the whole life-cycle.
