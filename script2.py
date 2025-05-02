import os
import subprocess
import sys

qwerty = ["java", "-jar", "src/main/java/arbitraryarithmetic/arithmetic.jar"] + sys.argv[1:]

subprocess.run(qwerty)