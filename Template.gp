set terminal qt
set title "Número por Classe (não normalizado)"
set xlabel "Momento"
set ylabel "Classe"
set style data linespoints
plot "Data.txt" using 1:2 with lines title "x1",  "Data.txt" using 1:3 with lines title "x2",  "Data.txt" using 1:4 with lines title "x3",  "Data.txt" using 1:5 with lines title "x4", 