FROM=1.0.10
TO=1.0.11

FROM_JAR=OAIToolkit\-${FROM}.jar
TO_JAR=OAIToolkit\-${TO}.jar

cd sample_scripts

for f in `grep -l ${FROM_JAR} *`
do
   mv $f ${f}.old
   cat ${f}.old | sed 's/'"${FROM_JAR}"'/'"${TO_JAR}"'/g' > $f
   rm -f ${f}.old
done


