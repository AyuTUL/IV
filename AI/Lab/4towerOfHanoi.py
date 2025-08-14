def tower_of_hanoi(n, source, destination, intermediate):
    if n == 1:
        print(f"Move disk 1 from {source} to {destination}")
        return

    tower_of_hanoi(n - 1, source, intermediate, destination)

    print(f"Move disk {n} from {source} to {destination}")

    tower_of_hanoi(n - 1, intermediate, destination, source)


print("------Tower Of Hanoi------")
n = int(input("Enter number of disks : "))
if n <= 0:
    print("Invalid input. Enter a positive integer.")
else:
    print(f"\nMinimum number of steps required : {2**n - 1}\n")
    tower_of_hanoi(n, "A", "C", "B")
