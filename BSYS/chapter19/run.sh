if [[ "$1" == "" ]]; then
    echo "Need max page number and iterations"
    exit 1
fi

if [[ "$2" == "" ]]; then
    echo "Need number of iterations"
    exit 1
fi

rm tlb.csv

for (( i = 1; i <= $1; i *= 2))
do
    ./tlb $i $2
    wait
done

python ./plot.py