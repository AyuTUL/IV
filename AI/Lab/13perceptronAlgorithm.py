# Perceptron-based Logic Gates

def unit_step(v):
    return 1 if v >= 0 else 0

def perceptron(x, w, b):
    v = sum(wi * xi for wi, xi in zip(w, x)) + b
    return unit_step(v)

# Logic gates implemented as perceptrons
def NOT(x):
    return perceptron([x], [-1], 0.5)

def AND(x):
    return perceptron(x, [1, 1], -1.5)

def NAND(x):
    return NOT(AND(x))

def OR(x):
    return perceptron(x, [1, 1], -0.5)

def NOR(x):
    return NOT(OR(x))

# Test harness
def test_gate(name, func, inputs):
    print(f"\n{name} Gate:")
    for a, b in inputs:
        print(f"{name}({a}, {b}) = {func([a, b])}")

if __name__ == "__main__":
    test_inputs = [(0,0), (0,1), (1,0), (1,1)]
    print("------ Perceptron Algorithm ------")
    test_gate("AND", AND, test_inputs)
    test_gate("NAND", NAND, test_inputs)
    test_gate("OR", OR, test_inputs)
    test_gate("NOR", NOR, test_inputs)

    print("\nNOT Gate:")
    for a in [0, 1]:
        print(f"NOT({a}) = {NOT(a)}")
