graph = {
    "s": {"a": 3, "b": 2},
    "a": {"c": 4, "d": 1},
    "b": {"e": 3, "f": 1},
    "c": {},
    "d": {},
    "e": {"h": 5},
    "f": {"i": 2, "g": 3},
    "g": {},
    "h": {},
    "i": {},
}

heuristic = {
    "s": 13,
    "a": 12,
    "b": 4,
    "c": 7,
    "d": 3,
    "e": 8,
    "f": 2,
    "g": 0,
    "h": 4,
    "i": 9,
}


def gbfs(graph, heuristic, start, goal):
    visited = set()
    queue = [(heuristic[start], [start], 0)]
    while queue:
        (h, path, cost) = queue.pop(0)
        current_node = path[-1]
        if current_node == goal:
            return path, cost
        visited.add(current_node)
        for neighbor, distance in graph[current_node].items():
            if neighbor not in visited:
                new_path = path + [neighbor]
                new_cost = cost + distance
                queue.append((heuristic[neighbor], new_path, new_cost))
        queue.sort()
    return None


start_node = input("Enter the starting node : ")
goal_node = input("Enter the goal node : ")

traversed_path, path_cost = gbfs(graph, heuristic, start_node, goal_node)

if traversed_path:
    print(
        f"Greedy Best-First Search traversal path: {traversed_path} with cost {path_cost}"
    )
else:
    print("Path not found.")
