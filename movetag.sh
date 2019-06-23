#!/bin/bash

if [ $# -lt 1 ]
then
    echo Bitte Tagnamen als Parameter angeben
else
    git fetch
    git pull
    git push origin :refs/tags/$1
    git tag -f $1
    echo .
    echo Bitte mit IDE noch git push ausführen, mit + alle Tags
fi
