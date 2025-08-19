# Dictionary holding all nodes, their successors, and heuristic values
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
    "k": []
}

# Function to sort successor list in ascending order by heuristic value
def sortList(new_list):
    new_list.sort(key=lambda x: x[1])
    return new_list

# Hill Climbing search function
def hillClimbing_search(node, value):
    if node in sussList.keys() and sussList[node]:
        new_list = sussList[node]
        new_list = sortList(new_list)
        if value > new_list[0][1]:
            # Move to the successor with the lowest heuristic value
            value = new_list[0][1]
            node = new_list[0][0]
            hillClimbing_search(node, value)
        else:
            print(f"ANSWER:\nFor given Data, the local maxima is at node '{node}' with heuristic value {value}\nS")
    else:
        print(f"ANSWER:\nFor given Data, the local maxima is at node '{node}' with heuristic value {value}")

# Starting node
initial_node = "a"
initial_value = 10
print("The user input is as follows:\n")
for key, value in sussList.items():
    print(f"{key}: {value}")
hillClimbing_search(initial_node, initial_value)
