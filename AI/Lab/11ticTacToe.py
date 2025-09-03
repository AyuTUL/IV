import os

EMPTY = " "
PLAYER_MARKS = ["X", "O"]
board = [EMPTY] * 9


def draw_banner():
    print("------ Tic-Tac-Toe ------")
    print("Player 1 [X] --- Player 2 [O]\n")


def draw_board():
    print(f" {board[0]} | {board[1]} | {board[2]} ")
    print("---|---|---")
    print(f" {board[3]} | {board[4]} | {board[5]} ")
    print("---|---|---")
    print(f" {board[6]} | {board[7]} | {board[8]} ")


def is_valid_move(pos):
    return 0 <= pos < 9 and board[pos] == EMPTY


def check_winner():
    win_conditions = [
        [0, 1, 2],
        [3, 4, 5],
        [6, 7, 8],
        [0, 3, 6],
        [1, 4, 7],
        [2, 5, 8],
        [0, 4, 8],
        [2, 4, 6],
    ]
    for a, b, c in win_conditions:
        if board[a] == board[b] == board[c] != EMPTY:
            return board[a]
    if EMPTY not in board:
        return "Draw"
    return None


def game_loop():
    current_player = 0
    error_message = ""

    while True:
        os.system("cls" if os.name == "nt" else "clear")
        draw_banner()
        draw_board()
        if error_message:
            print(f"Error: {error_message}\n")
            error_message = ""

        try:
            pos = (
                int(
                    input(
                        f"Player {current_player+1} ({PLAYER_MARKS[current_player]}), choose [1-9]: "
                    )
                )
                - 1
            )
        except ValueError:
            error_message = "Invalid input! Please enter a number between 1 and 9."
            continue

        if not is_valid_move(pos):
            if not (0 <= pos < 9):
                error_message = (
                    "Position out of range! Choose a number between 1 and 9."
                )
            else:
                error_message = "Position already taken! Choose another."
            continue

        board[pos] = PLAYER_MARKS[current_player]
        result = check_winner()

        if result:
            os.system("cls" if os.name == "nt" else "clear")
            draw_banner()
            draw_board()
            if result == "Draw":
                print("It's a draw!")
            else:
                print(f"Player {current_player+1} ({result}) wins!")
            break

        current_player = 1 - current_player


if __name__ == "__main__":
    game_loop()
