#!/bin/bash

DAY=$1

# Validate the day input
if [[ -z "$DAY" || "$DAY" -lt 1 || "$DAY" -gt 25 ]]; then
  echo "Usage: $0 <day (1-25)>"
  exit 1
fi

if [ "$DAY" -lt 10 ]; then
    DAY="0$DAY"
fi

FILE_NAME="main/kotlin/Day$DAY.kt"

# Check if the file already exists
if [ -e "$FILE_NAME" ]; then
  echo "Error: $FILE_NAME already exists."
  exit 1
else
  touch "$FILE_NAME"
  cat template.kt > "$FILE_NAME"
fi