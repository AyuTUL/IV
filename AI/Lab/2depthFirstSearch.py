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

closedlist = {}
dfs_traversal_output = []

for node in adj_list.keys():
    closedlist[node] = "notvisited"


def dfs_util(u):
    closedlist[u] = "visited"
    dfs_traversal_output.append(u)

    for v in adj_list[u]:
        if closedlist[v] != "visited":
            dfs_util(v)


print("------Depth First Search------")
startnode = input("Enter the starting node : ")

if startnode in adj_list:
    dfs_util(startnode)
    print("DFS traversal path : ", dfs_traversal_output)
else:
    print("Node not found in the graph.")
