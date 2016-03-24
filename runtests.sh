#!/bin/bash
EDITION="default"

for f in etc/*.java
do
    filename=${f##*/}
    class=${filename%.*}
    echo "Testing class ${class}..."
    java -jar dist/${EDITION}-samples.jar ${class} 2>&1 >/dev/null | diff etc/expected/out${class}.txt -
done
