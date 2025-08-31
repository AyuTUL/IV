regions = ['WA', 'NT', 'SA', 'Q', 'NSW', 'V']
colors = ['Red', 'Green', 'Blue']

neighbors = {
    'WA':  ['NT', 'SA'],
    'NT':  ['WA', 'SA', 'Q'],
    'SA':  ['WA', 'NT', 'Q', 'NSW', 'V'],
    'Q':   ['NT', 'SA', 'NSW'],
    'NSW': ['Q', 'SA', 'V'],
    'V':   ['SA', 'NSW']
}

print("------ Map Coloring Problem ------\n")
print("Regions:", ", ".join(regions))
print("Colors:", ", ".join(colors))
print("\nConstraints (adjacent regions must have different colors):")
for r in neighbors:
    for neigh in neighbors[r]:
        if regions.index(r) < regions.index(neigh):
            print(f"  {r} - {neigh}")
print()

def is_valid(assignment, region, color):
    return all(
        assignment.get(neigh) != color
        for neigh in neighbors[region]
    )

def select_unassigned_region(assignment):
    unassigned = [r for r in regions if r not in assignment]
    return min(unassigned, key=lambda r: sum(
        is_valid(assignment, r, c) for c in colors
    ))

def backtrack(assignment):
    if len(assignment) == len(regions):
        return assignment

    region = select_unassigned_region(assignment)

    for color in colors:
        if is_valid(assignment, region, color):
            assignment[region] = color
            result = backtrack(assignment)
            if result:
                return result
            del assignment[region]

    return None

solution = backtrack({})

if solution:
    print("Coloring solution found:\n")
    for r in regions:
        print(f"{r}: {solution[r]}")
else:
    print("No solution found.")