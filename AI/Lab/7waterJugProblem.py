j1 = 0
j2 = 0

print("------ Water Jug Problem ------")
x = int(input("Enter the capacity of jug 1: "))
y = int(input("Enter the capacity of jug 2: "))
goal_j1 = int(input("Enter the goal state for jug 1: "))
goal_j2 = int(input("Enter the goal state for jug 2: "))

print("\nInitial state = (0, 0)")
print(f"Capacities = ({x}, {y})")
print(f"Goal state = ({goal_j1}, {goal_j2})\n")
print("Available actions:")
print("1: Fill jug 1")
print("2: Fill jug 2")
print("3: Empty jug 1")
print("4: Empty jug 2")
print("5: Pour from jug 1 to jug 2")
print("6: Pour from jug 2 to jug 1")
print("7: Pour all from jug 1 to jug 2")
print("8: Pour all from jug 2 to jug 1")

while j1 != goal_j1 or j2 != goal_j2:
    print("\nCurrent state:", (j1, j2))

    try:
        r = int(input("Enter the rule number (1-8): "))

        match r:
            case 1:
                j1 = x
            case 2:
                j2 = y
            case 3:
                j1 = 0
            case 4:
                j2 = 0
            case 5:
                t = min(j1, y - j2)
                j1 -= t
                j2 += t
            case 6:
                t = min(j2, x - j1)
                j1 += t
                j2 -= t
            case 7:
                j2 = min(j1 + j2, y)
                j1 = max(j1 + j2 - y, 0)
            case 8:
                j1 = min(j1 + j2, x)
                j2 = max(j1 + j2 - x, 0)
            case _:
                print("Invalid rule number. Please enter a number between 1 and 8.")
                continue

    except ValueError:
        print("Please enter a valid number.")
        continue

print(f"\nGoal state reached: ({goal_j1}, {goal_j2})")
