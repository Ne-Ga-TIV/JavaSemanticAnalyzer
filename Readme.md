# Java Code Semantic Analyzer

This project implements a semantic analyzer for Java code using JavaParser. The analyzer parses Java source code, identifies variable declarations, assignments, and returns types, and provides insights into the semantic structure of the code.


## Prerequisites

Ensure you have the following prerequisites installed on your system:

- [Java SDK](https://www.oracle.com/java/technologies/javase-downloads.html) (version 8 or later)
- [Maven](https://maven.apache.org/download.cgi) (for building the project)

## Build

Build the project using Maven:

```bash
mvn clean install
```
## Features

- **Variable Analysis:** Identify variable declarations and assignments, including types.
- **Type Checking:** Perform basic type checking to catch potential mismatches.
- **Function Return Analysis:** Check if declared return types match actual return values.
- **Testable:** Includes testing functionality to ensure the accuracy of the analyzer.


## Usage
Here's an example Java code snippet demonstrating how to use the semantic analyzer:
```java
try {
    new JavaCodeAnalyzer("YourFile.java");
}catch (Exception ex) {
    ex.printStackTrice();
}
```
## Testing

All available tests can be found in `/src/test/java`. The `/src/test/resources/` directory contains files with the source code of the Java code for the tests.
```bash
mvn clean compile test
```


