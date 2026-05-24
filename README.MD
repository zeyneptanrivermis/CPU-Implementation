# CPU Emulator Project

## Overview

This project is a Java-based CPU emulator designed for educational purposes. It simulates a simplified CPU architecture, complete with a direct-mapped cache system, memory management, and support for executing 16-bit binary instructions.

The emulator helps users understand core CPU operations, cache behavior, and basic memory interactions.

## Components and Structure

### Main.java

- Entry point of the application.
- Responsible for loading the program and configuration files.
- Initiates emulator execution.
- Outputs the final state, including:
  - Accumulator (AC) value
  - Cache hit ratio

### CPUEmulator.java

- Implements the main execution logic.
- Manages CPU registers:
  - Program Counter (PC)
  - Accumulator (AC)
  - Flags
- Decodes and executes instructions fetched from memory via the cache.
- Supports instructions such as:
  - LOAD
  - STORE
  - ADD
  - SUB
  - MUL
  - CMP
  - JMP
  - Conditional jumps

### Cache.java

- Implements a direct-mapped cache.
- Contains 8 blocks.
- Each block stores 2 bytes.
- Uses a write-through policy.
- Immediately updates main memory on each write.
- Tracks cache performance metrics, especially the hit ratio.

### Memory.java

- Represents a simple 64 KB byte-addressable memory system.
- Serves as the backing store accessed through the cache.

## Configuration and Program Files

### config.txt

Defines the base address and initial Program Counter values for the emulator.

Example contents:

```text
0x2000
0x2000
