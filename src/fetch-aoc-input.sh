#!/bin/bash

# Ensure the script exits on error
set -e

# Configuration
COOKIE_FILE="session-cookie.txt"
SESSION_COOKIE=$(cat "$COOKIE_FILE")
DAY=$1

# Validate the day input
if [[ -z "$DAY" || "$DAY" -lt 1 || "$DAY" -gt 25 ]]; then
  echo "Usage: $0 <day (1-25)>"
  exit 1
fi

# Define the URL and output file
URL="https://adventofcode.com/2024/day/$DAY/input"
OUTPUT_FILE="input.txt"

# Fetch the input using curl
curl -s -H "Cookie: session=$SESSION_COOKIE" "$URL" -o "$OUTPUT_FILE"

# Check for HTML content (error pages are typically HTML)
if grep -q "<html>" "$OUTPUT_FILE"; then
  echo "Failed to retrieve input for day $DAY. Check if the day is unlocked and your session cookie is valid."
  rm "$OUTPUT_FILE"  # Remove the invalid file
  exit 1
fi

echo "Input for day $DAY saved to $OUTPUT_FILE"