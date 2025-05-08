#!/bin/bash

# Define the source directory
SRC_DIR="src/main/java"

# Define the output directory for compiled classes
BIN_DIR="bin"

# Define the main class to run
MAIN_CLASS="com.db.terraform.TerraformDbSimulator"

# Define the path to the SQLite JDBC driver JAR
SQLITE_JAR="$HOME/.m2/repository/org/xerial/sqlite-jdbc/3.36.0.3/sqlite-jdbc-3.36.0.3.jar"

# Create the bin directory if it doesn't exist
mkdir -p $BIN_DIR

# Compile all necessary Java files, including those in subpackages
# We need to include the sqlite connector and data entity classes
echo "Compiling Java files..."
javac -d $BIN_DIR -cp "$BIN_DIR:$SQLITE_JAR" \
  $SRC_DIR/com/db/sqlite/SQLiteConnector.java \
  $SRC_DIR/com/db/terraform/data/*.java \
  $SRC_DIR/com/db/terraform/TerraformDbSimulator.java
  $SRC_DIR/com/db/terraform/TerraformDbShow.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
  echo "Compilation failed."
  exit 1
fi

echo "Compilation successful. Running $MAIN_CLASS..."

# Run the main class, setting the classpath to the bin directory
java -cp "$BIN_DIR:$SQLITE_JAR" $MAIN_CLASS

# Check if execution was successful
if [ $? -ne 0 ]; then
  echo "Execution failed."
  exit 1
fi

echo "TerraformDbSimulator execution finished. Running TerraformDbShow..."

# Run the TerraformDbShow class
SHOW_CLASS="com.db.terraform.TerraformDbShow"
java -cp "$BIN_DIR:$SQLITE_JAR" $SHOW_CLASS

# Check if execution of TerraformDbShow was successful
if [ $? -ne 0 ]; then
  echo "TerraformDbShow execution failed."
  exit 1
fi

echo "TerraformDbShow execution finished. Script finished."