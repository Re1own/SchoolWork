date +%F
read -p "Input value of n: " n
sum=0
i=1
while (($i<=$n))
do
sum=$(($sum +$i))
i=$(($i+2))
done
echo "SUM is $sum."
