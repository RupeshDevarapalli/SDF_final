# Arbitrary Precision Arithmetic Library (Java) - CS1023 SDF Project

## Project Summary

This project implements a library in Java to support **infinite/arbitrary-precision arithmetic** for both **integers** and **floating-point numbers** using object-oriented programming principles.

It includes:

- A custom class `AInteger` for arbitrary-size integer operations
- A custom class `AFloat` for arbitrary-precision floating-point operations
- A command-line interface (`MyInfArith`) to perform calculations via terminal
- No built-in large number libraries (e.g., `BigInteger`, `BigDecimal`) used
- Operations implemented manually using string manipulations
- `arbitraryarithmetic.AInteger`: Arbitrary precision signed integer class
- `arbitraryarithmetic.AFloat`: Arbitrary precision float using scaled `AInteger`
- `MyInfArith`: Command-line tool to evaluate expressions

---

## How to Build and Run

### Requirements

- Java 17+ (or compatible version)
- Python 3 (for automation script)

### Compile and Run (Manual)

```bash
javac -d out src/main/java/arbitraryarithmetic/*.java src/main/java/MyInfArith.java
java -cp out MyInfArith int add 123456789 987654321
```

# Examples

### Integer addition
```bash
java MyInfArith int add 12345678901234567890 98765432109876543210
```
### Output: 111111111011111111100

### Float division
```bash
java MyInfArith float div 244727.15202 75964.3891
```
### Output: 3.22160363453775211100855150561