sussList = {
    "a": [["b", 10], ["j", 8], ["f", 7]],
    "b": [["d", 1], ["c", 2]],
    "j": [["k", 0]],
    "f": [["e", 5], ["g", 3]],
    "d": [],
    "c": [["h", 0]],
    "e": [["i", 6]],
    "g": [],
    "h": [],
    "i": [["k", 0]],
    "k": [],
}


def sortList(new_list):
    new_list.sort(key=lambda x: x[1])
    return new_list


def hillClimbing_search(node, value):
    if node in sussList and sussList[node]:
        new_list = sortList(sussList[node])
        if new_list[0][1] < value:
            return hillClimbing_search(new_list[0][0], new_list[0][1])
        else:
            return node, value
    return node, value


initial_node = "a"
initial_value = 10

print("------ Hill Climbing ------")
print("Given graph :\n")
for key, value in sussList.items():
    print(f"{key}: {value}")
result_node, result_value = hillClimbing_search(initial_node, initial_value)
print(f"\nLocal maxima at node '{result_node}' with heuristic value {result_value}")
