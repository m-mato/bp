#!/bin/bash -       
#title           :loadScripts.sh
#description     :This scrpt loads byteman script with rules for entered classes.
#author		     :Matej Majdis
#date            :20150321  
#notes           :Needs Byteman path in BYTEMAN_PATH variable.
#==============================================================================

echo "" > rules.btm

for c in "$@"
do
	size=${#c}
	#CLASS_PATH=$(echo ${c:0:(size-((6)))} | tr "/" ".")
	#echo "$CLASS_PATH"

	BYTEMAN_PATH="../byteman"

	STR=$(echo -n $(javap -p "$c"))
	 
	OLD_IFS="$IFS"
	IFS="("
	STR_ARRAY=( $STR )
	IFS="$OLD_IFS"

	METHODS_ARRAY=("<init>")

	read -a CP_AR <<<${STR_ARRAY[0]}
	s=${#CP_AR[2]}
	CLASS_PATH=$(echo ${CP_AR[2]:1:(s-((7)))})
	echo "----> $CLASS_PATH"
	#echo "---> ${STR_ARRAY[0]}"

	for index in "${!STR_ARRAY[@]}"
	do
	    if [[ $((index+1)) < ${#STR_ARRAY[@]} ]]; then
	    	if [[ $index != $((0)) ]]; then 
	    		#echo "> $index ${STR_ARRAY[index]}"
	    		read -a line_ARRAY <<<${STR_ARRAY[index]}
	    		method=${line_ARRAY[${#line_ARRAY[@]}-((1))]}
	    		#echo "$method"

	    		if [[ ${METHODS_ARRAY[$method]} ]]; then 
	    			METHODS_ARRAY+=($method)
	    		fi
	    	fi
		fi
	done

	for x in "${METHODS_ARRAY[@]}"
	do
	    echo "+ [$x]"


		echo "RULE detect throw, method $x, class $CLASS_PATH
	CLASS $CLASS_PATH
	METHOD $x
	AT THROW ALL
	BIND exception:Throwable= $^
	IF true
	DO System.out.println(\"Detected athrow, exception: \" + exception)
ENDRULE
">> rules.btm

	done
done

../byteman/bin/bmsubmit.sh -l rules.btm

