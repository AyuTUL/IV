graph = {
    "s": [("a", 1), ("g", 10)],
    "a": [("b", 2), ("c", 1)],
    "b": [("d", 5)],
    "c": [("d", 3), ("g", 4)],
    "d": [("g", 2)],
    "g": [],
}

heuristic = {"s": 5, "a": 3, "b": 4, "c": 2, "d": 6, "g": 0}


def a_star(graph, heuristic, start, goal):
    open_list = set([start])
    closed_list = set()
    g = {start: 0}
    parents = {start: start}

    def get_neighbors(node):
        return graph[node]

    def h(node):
        return heuristic[node]

    while open_list:
        n = min(open_list, key=lambda v: g[v] + h(v))

        if n == goal:
            path = []
            while parents[n] != n:
                path.append(n)
                n = parents[n]
            path.append(start)
            path.reverse()

            total_cost = g[goal]
            print(f"Path found: {path} with cost {total_cost}")
            return path, total_cost

        for m, weight in get_neighbors(n):
            if m not in open_list and m not in closed_list:
                open_list.add(m)
                parents[m] = n
                g[m] = g[n] + weight
            else:
                if g.get(m, float("inf")) > g[n] + weight:
                    g[m] = g[n] + weight
                    parents[m] = n
                    if m in closed_list:
                        closed_list.remove(m)
                        open_list.add(m)

        open_list.remove(n)
        closed_list.add(n)

    print("Path does not exist.")
    return None, float("inf")


start = input("Enter the starting node : ")
goal = input("Enter the goal node : ")
a_star(graph, heuristic, start, goal)
