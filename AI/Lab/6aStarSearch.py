graph = {
    "s": [("a", 1), ("g", 10)],
    "a": [("b", 2), ("c", 1)],
    "b": [("d", 5)],
    "c": [("d", 3), ("g", 4)],
    "d": [("g", 2)],
    "g": [],
}

heuristic = {"s": 5, "a": 3, "b": 4, "c": 2, "d": 6, "g": 0}


def a_star(graph: dict, heuristic: dict, start: str, goal: str):

    open_list = {start}
    closed_list = set()
    g_cost = {start: 0}
    parents = {start: start}

    while open_list:
        current = min(open_list, key=lambda node: g_cost[node] + heuristic[node])

        if current == goal:
            path = []
            while parents[current] != current:
                path.append(current)
                current = parents[current]
            path.append(start)
            path.reverse()
            return path, g_cost[goal]

        for neighbor, weight in graph.get(current, []):
            new_cost = g_cost[current] + weight
            if neighbor not in open_list and neighbor not in closed_list:
                open_list.add(neighbor)
                parents[neighbor] = current
                g_cost[neighbor] = new_cost
            else:
                if g_cost.get(neighbor, float("inf")) > new_cost:
                    g_cost[neighbor] = new_cost
                    parents[neighbor] = current
                    if neighbor in closed_list:
                        closed_list.remove(neighbor)
                        open_list.add(neighbor)

        open_list.remove(current)
        closed_list.add(current)

    return None, float("inf")


print("------ A* Search ------")
start_node = input("Enter the starting node: ")
goal_node = input("Enter the goal node: ")

if start_node in graph and goal_node in graph:
    path, total_cost = a_star(graph, heuristic, start_node, goal_node)
    if path:
        print(f"Traversal path : {path} with cost {total_cost}")
    else:
        print("Path does not exist.")
else:
    print("Invalid start or goal node.")
