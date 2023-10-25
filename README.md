# Editor-and-Spelling-Checker
## Program Description:
The program is able to read a file from disk with the current directory set to the directory where the program is located. Save is able to save the current text in the window back to the file from which it was read. If no file was opened, the program provides a save dialog to get the location in which to save the file, defaulting to the directory where the progam is located. The Edit menu has one menu item: Spell Check. This program will use "dictionary.txt" as a dictionary to look up properly spelled words. When the program starts, it will open the dictionary file (name it "dictionary.txt"). The program will hash the words into a hash table container chosen from the choices available in the Java libraries.

The program will have a menu item to open an input file and place it into a JavaFX TextArea in a window. When the checking proceeds, the program will extract a word from the text to be checked and find out if the word is in the dictionary. The program will continue this process until you have checked all the words in the file. Each time the program finds a word that cannot be matched in the dictionary, the program will let the user know and it will attempt to generate a list of suggested words. The program will generate the list by assembling similar words via three methods:

- One letter missing. The program assumes that one letter has been left out of the word. The program will assemble new words to check by adding letters a..z in each of the positions in the word from the start to the end of the word.
- One letter added. The program assumes the word has an extra letter. It scans through the word deleting each of the letters in turn, and looking up the word formed by the remaining letters.
- Two letters reversed. The program swaps letters in positions 0..1, 1..2, 2..3, ... , n-2..n-1, to form new words which it looks up.

Each time the program finds a legal word from any of the three methods, the program adds it to the suggestion list, which it presents to the user in a dialog box. If the program cannot identify any suggestions, it lets the user know there are none.

## How to run the program:
Please refer to the UsersManual.docx file for instructions on how to run the program and the program UI.
