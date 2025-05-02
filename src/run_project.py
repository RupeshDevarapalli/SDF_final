import os
import subprocess
import sys

SRC = "main/java"
OUT = "out"
MAIN = "MyInfArith"

def compile_java():
    print("Compiling your Java files...")

    os.makedirs(OUT, exist_ok=True)

    command = f"javac -d {OUT} {SRC}/arbitraryarithmetic/*.java {SRC}/{MAIN}.java"
    if subprocess.call(command, shell=True) != 0:
        print("Compilation failed. Please fix the errors and try again.")
        sys.exit(1)

    print("All files compiled successfully.\n")

def run_program(args):
    print(f"Running: {MAIN} {' '.join(args)}")
    command = ["java", "-cp", OUT, MAIN] + args
    subprocess.call(command)

def main():
    if len(sys.argv) != 5:
        print("\nUsage: python run_project.py <int|float> <add|sub|mul|div> <num1> <num2>\n")
        print("Example: python run_project.py int mul 123456789 987654321\n")
        sys.exit(1)

    compile_java()
    run_program(sys.argv[1:])

if __name__ == "__main__":
    main()
