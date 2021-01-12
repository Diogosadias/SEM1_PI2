set terminal png
set output "outputs/Imagem.png"
set title "Matriz"
set xlabel "Individuos"
set ylabel "Classes"
set style data linespoints
plot "Data.txt" "População"
