touch ..\\examples\\1.txt
touch ..\\examples\\2.txt
echo "This files should be equal." > ..\\examples\\1.txt
echo "This files should be equal." > ..\\examples\\2.txt
javac ..\\Jiff.java
java -cp .. Jiff ..\\examples\\1.txt ..\\examples\\2.txt
