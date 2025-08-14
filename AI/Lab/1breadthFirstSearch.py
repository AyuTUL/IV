adj_list = {
    "a": ["b", "c", "d"],
    "b": ["e", "f"],
    "c": ["g"],
    "d": ["h"],
    "e": [],
    "f": ["i"],
    "g": ["j"],
    "h": ["k"],
    "i": [],
    "j": [],
    "k": [],
}


def bfs(graph, start_node):
    visited = []
    queue = []
    output = []

    visited.append(start_node)
    queue.append(start_node)

    while queue:
        node = queue.pop(0)
        output.append(node)

        for neighbour in graph[node]:
            if neighbour not in visited:
                visited.append(neighbour)
                queue.append(neighbour)

    print("BFS traversal path : ", output)


print("------Breadth First Search------")
startnode = input("Enter the starting node : ")
if startnode in adj_list:
    bfs(adj_list, startnode)
else:
    print("Node not found in graph.")
