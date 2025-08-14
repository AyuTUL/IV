def tower_of_hanoi(n, source, destination, auxiliary):
    if n == 1:
        print(f"Move disk 1 from {source} to {destination}")
        return
    
    # Move n-1 disks from source to auxiliary
    tower_of_hanoi(n - 1, source, auxiliary, destination)
    
    # Move nth disk
    print(f"Move disk {n} from {source} to {destination}")
    
    # Move the n-1 disks from auxiliary to destination
    tower_of_hanoi(n - 1, auxiliary, destination, source)

# Get number of disks from user
n = int(input("Enter number of disks: "))

print(f"\nMinimum number of steps required: {2**n - 1}\n")

# Solve the problem
tower_of_hanoi(n, 'A', 'C', 'B')
