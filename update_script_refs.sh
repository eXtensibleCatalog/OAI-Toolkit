FROM=0.6.9alpha
TO=0.6.10alpha

FROM_JAR=OAIToolkit\-${FROM}.jar
TO_JAR=OAIToolkit\-${TO}.jar

cd sample_scripts

for f in `grep -l ${FROM_JAR} *`
do
   mv $f ${f}.old
   cat ${f}.old | sed 's/'"${FROM_JAR}"'/'"${TO_JAR}"'/g' > $f
   rm -f ${f}.old
done


