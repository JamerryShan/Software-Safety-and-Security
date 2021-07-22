#!/bin/bash
if ! command -v radamsa &> /dev/null
then
  echo "Please install radamsa and put it into your PATH."
  exit
fi

N=0
while true; do
  N=`expr $N + 1`
  echo -en '\r' $N
  radamsa initial-fuzzing-input.json > input.json
  ./parser input.json > /dev/null 2>&1
  if [ $? -gt 127 ]; then
    cp input.json fuzz-crash-`date +%Y.%m.%d-%H.%M.%S.%N`.json
    echo
    echo "Crash found!"
    exit
  fi
done
