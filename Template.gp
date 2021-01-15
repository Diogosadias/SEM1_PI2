set terminal dumb
set output "outputs/aaaaa.txt"
set title "Número Total De Individuos"
set xlabel "Momento"
set ylabel "Dimensão da população"
set style data linespoints
plot "Data.txt" title "Número Total De Individuos"
