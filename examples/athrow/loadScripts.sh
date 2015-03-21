#!/bin/bash

STR=$(echo -n $(javap -p "$1"))
 
OLD_IFS="$IFS"
IFS="("
STR_ARRAY=( $STR )
IFS="$OLD_IFS"
 
#for x in "${STR_ARRAY[@]}"
#do
#    echo "> [$x]"
#done

for index in "${!STR_ARRAY[@]}"
do
    if [[ $((index+1)) < ${#STR_ARRAY[@]} ]]; then
    	if [[ $index != $((0)) ]]; then 
    		echo "> $index ${STR_ARRAY[index]}"
    		read -a line_ARRAY <<<${STR_ARRAY[index]}
    		echo "${line_ARRAY[${#line_ARRAY[@]}-((1))]}"
    	fi
	fi
done