# README.md

## CPU Emulator Project

### Overview

This project is a Java-based CPU emulator designed for educational purposes. It simulates a simplified CPU architecture, complete with a direct-mapped cache system, memory management, and support for executing 16-bit binary instructions. The emulator facilitates understanding of core CPU operations, cache behaviors, and basic memory interactions.

### Components and Structure

* **Main.java**

  * Entry point of the application.
  * Responsible for loading the program and configuration files.
  * Initiates emulator execution and outputs the final state, including the Accumulator (AC) value and cache hit ratio.

* **CPUEmulator.java**

  * Implements the main execution logic.
  * Manages CPU registers: Program Counter (PC), Accumulator (AC), and Flags.
  * Decodes and executes instructions fetched from memory via the cache.
  * Supports instruction set including operations like LOAD, STORE, ADD, SUB, MUL, CMP, JMP, and conditional jumps.

* **Cache.java**

  * Implements a direct-mapped cache with 8 blocks (2 bytes per block).
  * Uses a write-through policy, immediately updating main memory on each write.
  * Tracks and reports cache performance metrics, specifically hit ratio.

* **Memory.java**

  * Represents a simple 64KB byte-addressable memory system.
  * Serves as the backing store accessed through the cache.

### Configuration and Program Files

* **config.txt**

  * Defines base address and initial Program Counter values for the emulator.
  * Example contents:

    ```
    0x2000
    0x2000
    ```

* **program.txt**

  * Contains the program instructions in binary form (16 bits per instruction).

### Execution Instructions

To compile and execute the emulator, follow these steps:

1. Compile the Java source files:

   ```shell
   javac Main.java CPUEmulator.java Cache.java Memory.java
   ```

2. Run the emulator with program and configuration files:

   ```shell
   java Main program.txt config.txt
   ```

### Performance Metrics

Upon execution, the emulator provides:

* **Accumulator (AC)**: Final computation result.
* **Cache Hit Ratio**: Efficiency metric indicating cache performance.

### Testing and Results

The provided test program calculates the sum of integers from 0 to 20. The emulator accurately executes this program, producing the correct result (AC = 210) and demonstrating cache operations with an observed hit ratio.

### Future Improvements

* Implementing set-associative caches to enhance cache efficiency.
* Exploring write-back policies for improved performance.
* Expanding test cases for more comprehensive performance evaluations.
* Detailed performance analyses, including timing metrics.

### Development Environment

* Java Version: Java 21

---

This README provides a structured overview, facilitating clear understanding and effective usage of the CPU emulator project.
