# Graph definition
graph = {
    "a": ["b", "c"],
    "b": ["d", "e"],
    "c": ["f", "g"],
    "d": [],
    "e": [],
    "f": [],
    "g": [],
}


def dfs(node, goal, depth_limit, visited, path):

    visited.add(node)
    path.append(node)

    if node == goal:
        return True
    if depth_limit == 0:
        return False

    for neighbor in graph.get(node, []):
        if neighbor not in visited:
            if dfs(neighbor, goal, depth_limit - 1, visited, path):
                return True

    return False


def iddfs(start, goal):

    for depth in range(len(graph) + 1):
        visited = set()
        path = []
        found = dfs(start, goal, depth, visited, path)
        print(f"Depth {depth} : Traversal path: {path}")

        if found:
            return f"Goal node '{goal}' found! Traversal path : {path}"

    return "Goal node not found."


print("------ Iterative Deepening Depth First Search ------")
start = input("Enter the start node : ").strip().lower()
goal = input("Enter the goal node : ").strip().lower()

if start not in graph or goal not in graph:
    print("Invalid start or goal node.")
else:
    print(iddfs(start, goal))
