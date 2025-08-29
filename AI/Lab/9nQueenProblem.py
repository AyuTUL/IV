def is_safe(board, row, col, n):
    for c in range(col, -1, -1):
        if board[row][c] == 1:
            return False

    i, j = row, col
    while i >= 0 and j >= 0:
        if board[i][j] == 1:
            return False
        i -= 1
        j -= 1

    i, j = row, col
    while i < n and j >= 0:
        if board[i][j] == 1:
            return False
        i += 1
        j -= 1
    return True


def nqueen(board, col, n):
    if col >= n:
        return True
    for i in range(n):
        if is_safe(board, i, col, n):
            board[i][col] = 1
            if nqueen(board, col + 1, n):
                return True
            board[i][col] = 0
    return False


print("------ n Queen Problem ------")
n = int(input("Enter number of queens : "))
board = [[0 for _ in range(n)] for _ in range(n)]

if nqueen(board, 0, n):
    for row in board:
        print(" ".join("Q" if x == 1 else "." for x in row))
else:
    print("Not possible")
