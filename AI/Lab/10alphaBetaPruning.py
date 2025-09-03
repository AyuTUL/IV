tree = [[[5, 1, 2], [8, -8, -9]], [[9, 4, 5], [-3, 4, 3]]]
pruned = 0


def alphabeta(node, depth=0, alpha=float("-inf"), beta=float("inf"), maximizing=True):
    global pruned
    if not isinstance(node, list):
        return node

    if maximizing:
        value = float("-inf")
        for child in node:
            val = alphabeta(child, depth + 1, alpha, beta, False)
            value = max(value, val)
            alpha = max(alpha, value)
            if alpha >= beta:
                pruned += 1
                break
        return value
    else:
        value = float("inf")
        for child in node:
            val = alphabeta(child, depth + 1, alpha, beta, True)
            value = min(value, val)
            beta = min(beta, value)
            if alpha >= beta:
                pruned += 1
                break
        return value


if __name__ == "__main__":
    print("------ Alpha Beta Pruning ------")
    result = alphabeta(tree)
    print("Alpha-Beta Result :", result)
    print("Sub-trees pruned :", pruned)
